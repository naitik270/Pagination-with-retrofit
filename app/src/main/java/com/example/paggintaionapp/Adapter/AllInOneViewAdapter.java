package com.example.paggintaionapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paggintaionapp.Classes.Airline;
import com.example.paggintaionapp.Classes.Example;
import com.example.paggintaionapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AllInOneViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Example.Datum> dataClassesArrayList;
    private Context context;
    private boolean isLoadingAdded = false;
    private AllInOneClickListener allInOneClickListener;


    public AllInOneViewAdapter(Context context) {
        this.context = context;
        dataClassesArrayList = new ArrayList<>();
    }

    public void SetOnItemClickListener(AllInOneClickListener allInOneClickListener) {
        this.allInOneClickListener = allInOneClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.row_all_in_one, parent, false);
        viewHolder = new AdsListVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Example.Datum dataClasses = dataClassesArrayList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                AdsListVH adsListVH = (AdsListVH) holder;
                adsListVH.setData(dataClasses, position);
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataClassesArrayList == null ? 0 : dataClassesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataClassesArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Example.Datum mc) {
        dataClassesArrayList.add(mc);
        notifyItemInserted(dataClassesArrayList.size() - 1);
    }

    public void addAll(List<Example.Datum> mcList) {

        Log.d("--addAll--", "mcList: " + new Gson().toJson(mcList));
        for (Example.Datum mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Example.Datum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataClassesArrayList.size() - 1;
        Example.Datum item = getItem(position);

        if (item != null) {
            dataClassesArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Example.Datum getItem(int position) {
        return dataClassesArrayList.get(position);
    }

    protected class AdsListVH extends RecyclerView.ViewHolder {
        private TextView idTVFirstName, idTVLastName, idTVEmail;
        private ImageView iv_display;
        private CardView cc_header;

        public AdsListVH(@NonNull View itemView) {
            super(itemView);
            initViewsAndListeners(itemView);
        }

        private void initViewsAndListeners(View itemView) {
            idTVLastName = itemView.findViewById(R.id.idTVLastName);
            idTVFirstName = itemView.findViewById(R.id.idTVFirstName);
            cc_header = itemView.findViewById(R.id.cc_header);
            iv_display = itemView.findViewById(R.id.iv_display);
            idTVEmail = itemView.findViewById(R.id.idTVEmail);

        }

        void Bind(Example.Datum obj, int position) {
            cc_header.setOnClickListener(v -> {
                allInOneClickListener.onItemClick(obj, position);
            });
        }

        public void setData(Example.Datum dataClasses, int position) {
            idTVFirstName.setText(dataClasses.getName().concat(" [").concat(String.valueOf(position)).concat("]"));
            idTVLastName.setText(dataClasses.getId());
            idTVEmail.setText(String.valueOf(dataClasses.getTrips()));

            for (Airline obj : dataClasses.getAirline()) {
                Glide.with(iv_display).load(obj.getLogo()).into(iv_display);
            }

            Bind(dataClasses, position);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}

package com.example.paggintaionapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paggintaionapp.Adapter.AllInOneViewAdapter;
import com.example.paggintaionapp.ApiClasses.APICallsInterface;
import com.example.paggintaionapp.ApiClasses.ApiClient;
import com.example.paggintaionapp.Classes.Airline;
import com.example.paggintaionapp.Classes.Example;
import com.example.paggintaionapp.Classes.PaginationScrollListener;
import com.example.paggintaionapp.R;
import com.example.paggintaionapp.Adapter.AllInOneClickListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayListActivity extends AppCompatActivity {

    Button btn_next;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    RecyclerView rv_near_by_products;
    ProgressBar ProgressBar;
    List<Example.Datum> resultsDatumList;
    List<Airline> resultsAirline;
    AllInOneViewAdapter mAllInOneViewAdapter;
    LinearLayoutManager ll_manager;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_activity);
        resultsDatumList = new ArrayList<>();
        resultsAirline = new ArrayList<>();
        btn_next = findViewById(R.id.btn_next);
        ProgressBar = findViewById(R.id.ProgressBar);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> finish());
        rv_near_by_products = findViewById(R.id.rv_near_by_products);

        ll_manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rv_near_by_products.setLayoutManager(ll_manager);
        rv_near_by_products.setHasFixedSize(true);

        mAllInOneViewAdapter = new AllInOneViewAdapter(DisplayListActivity.this);
        rv_near_by_products.setAdapter(mAllInOneViewAdapter);

        rv_near_by_products.addOnScrollListener(new PaginationScrollListener(ll_manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        apiCallsInterface = ApiClient.getRetrofitInstanceGET().create(APICallsInterface.class);
        loadFirstPage();
        mAllInOneViewAdapter.SetOnItemClickListener(new AllInOneClickListener() {
            @Override
            public void onItemClick(Example.Datum dataClasses, int position) {
                startActivity(new Intent(getApplicationContext(), DescriptionActivity.class)
                        .putExtra("dataClasses", (Serializable) dataClasses)
                        .putExtra("user_id", dataClasses.getId()));
            }
        });
    }

    private void loadFirstPage() {
        Log.d("--repo--", "loadFirstPage");
        callTopRatedMoviesApiDatum().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.e("--repo--", "response.body(): " + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    ProgressBar.setVisibility(View.GONE);
                    TOTAL_PAGES = response.body().getTotalPages();
                    Log.e("--repo--", "currentPage: " + currentPage);
                    List<Example.Datum> results = fetchResults(response);

                    mAllInOneViewAdapter.addAll(results);
                   /* for (Example.Datum obj : results) {
                        mAllInOneViewAdapter.addAll(obj.getAirline());
                    }*/

                    if (currentPage <= TOTAL_PAGES)
                        mAllInOneViewAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    Log.e("--repo--", "else: " + call.request().url());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                t.printStackTrace();
                Log.e("--repo--", "else: " + t.getMessage());
            }
        });
    }

    private APICallsInterface apiCallsInterface;

    private Call<Example> callTopRatedMoviesApiDatum() {
        Call<Example> call = apiCallsInterface.getPassenger(currentPage, 10);
        Log.e("--repo--", "URL: " + call.request().url());
        return call;
    }

    private void loadNextPage() {
        Log.d("--Load--", "loadNextPage");

        callTopRatedMoviesApiDatum().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.e("--repo--", "response.body(): " + new Gson().toJson(response.body()));
                mAllInOneViewAdapter.removeLoadingFooter();
                isLoading = false;
                if (response.body() != null) {
                    List<Example.Datum> results = fetchResults(response);


                    mAllInOneViewAdapter.addAll(results);


//                    for (Example.Datum obj : results) {
//                        mAllInOneViewAdapter.addAll(obj.getAirline());
//                    }
                    if (currentPage != TOTAL_PAGES)
                        mAllInOneViewAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    Log.e("--repo--", "else: " + call.request().url());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                t.printStackTrace();
                Log.e("--repo--", "else: " + t.getMessage());
            }
        });
    }

    private List<Example.Datum> fetchResults(Response<Example> response) {
        return response.body().getData();
    }

    /*private List<Airline> fetchResults(Response<Example> response) {
        for (Example.Datum obj : response.body().getData()) {
            return obj.getAirline();
        }
        return new ArrayList<>();
    }*/
}
package com.example.paggintaionapp.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.paggintaionapp.Classes.Airline;
import com.example.paggintaionapp.Classes.Example;
import com.example.paggintaionapp.R;


public class DescriptionActivity extends AppCompatActivity {

    ImageView iv_display;
    TextView tvItemPrice, tvItemTitle, tvItemTown;
    TextView tvDescription,tvDescriptionList;
    Example.Datum objDatum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_activity);

        iv_display = findViewById(R.id.iv_display);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvItemTitle = findViewById(R.id.tvItemTitle);
        tvItemTown = findViewById(R.id.tvItemTown);
        tvDescription = findViewById(R.id.tvDescription);
        tvDescriptionList = findViewById(R.id.tvDescriptionList);
        objDatum = (Example.Datum) getIntent().getSerializableExtra("dataClasses");
        tvItemPrice.setText(objDatum.getId());
        tvItemTitle.setText(objDatum.getName());

        for (Airline objAirline : objDatum.getAirline()) {
            Glide.with(iv_display).load(objAirline.getLogo()).into(iv_display);
            tvItemTown.setText(objAirline.getHeadQuaters());
            tvDescription.setText(objAirline.getSlogan());
            tvDescriptionList.setText(objAirline.getWebsite());
        }

    }
}

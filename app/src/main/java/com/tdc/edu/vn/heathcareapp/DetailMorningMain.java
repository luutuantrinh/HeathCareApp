package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.DetailMorningAdapter;
import com.tdc.edu.vn.heathcareapp.Model.DetailMorning;

import java.util.ArrayList;


public class DetailMorningMain extends AppCompatActivity {
    private RecyclerView rv_detailmorning;
    DetailMorningAdapter detailMorningAdapter;
    ImageView imgHinh;
    Intent intent;
    ArrayList<DetailMorning> listdetailmorning = new ArrayList<>();
    ImageView nav_back_detailmorning;
    Button btnStartDetail;
    private StorageReference mStorageRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailmorning);
        rv_detailmorning = findViewById(R.id.rv_detailmorning);
        nav_back_detailmorning = findViewById(R.id.icon_back_toolbar_detailmorning);
        btnStartDetail = findViewById(R.id.btnStartDetail);

        intent = getIntent();
        //Guided Workouts
        listdetailmorning.add(new DetailMorning(1,"Title 1"));
        listdetailmorning.add(new DetailMorning(1,"Title 2"));
        listdetailmorning.add(new DetailMorning(1,"Title 3"));
        listdetailmorning.add(new DetailMorning(1,"Title 4"));
        listdetailmorning.add(new DetailMorning(1,"Title 5"));
        listdetailmorning.add(new DetailMorning(1,"Title 6"));
        listdetailmorning.add(new DetailMorning(1,"Title 7"));
        listdetailmorning.add(new DetailMorning(1,"Title 8"));
        detailMorningAdapter = new DetailMorningAdapter(this,listdetailmorning);
        rv_detailmorning.setAdapter(detailMorningAdapter);
        rv_detailmorning.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


        // tải dữ liệu xuống từ firebase






        nav_back_detailmorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnStartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }



}

package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.adapter.AllExercisesAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.TrendingItemAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;

import java.util.ArrayList;


public class ItemTrendingItem extends AppCompatActivity {
    private RecyclerView rvitemtrendingitem;
    TrendingItemAdapter trendingItemAdapter;
    ImageView imgHinh;
    Intent intent;
    ArrayList<AllExercises> listexercises = new ArrayList<>();
    ImageButton nav_back_trendingitem;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_item);
        rvitemtrendingitem = findViewById(R.id.rv_trendingitem);
        nav_back_trendingitem = findViewById(R.id.icon_back_toolbar_trendingitem);
        listexercises.add(new AllExercises(1,"Title 1"));
        listexercises.add(new AllExercises(1,"Title 2"));
        listexercises.add(new AllExercises(1,"Title 4"));
        trendingItemAdapter = new TrendingItemAdapter(this,listexercises);
        rvitemtrendingitem.setAdapter(trendingItemAdapter);
        rvitemtrendingitem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        nav_back_trendingitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }


}

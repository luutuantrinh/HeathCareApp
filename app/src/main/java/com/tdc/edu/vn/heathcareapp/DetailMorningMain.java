package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.adapter.AllExercisesAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;

import java.util.ArrayList;


public class DetailMorningMain extends AppCompatActivity {
    private RecyclerView rv_detailmorning;
    AllExercisesAdapter allExercisesAdapter;
    ImageView imgHinh;
    Intent intent;
    ArrayList<AllExercises> listexercises = new ArrayList<>();
    ImageView nav_back_detailmorning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailmorning);
        rv_detailmorning = findViewById(R.id.rv_detailmorning);
        nav_back_detailmorning = findViewById(R.id.icon_back_toolbar_detailmorning);

        //Guided Workouts
        listexercises.add(new AllExercises(1,"Title 1"));
        listexercises.add(new AllExercises(1,"Title 2"));
        listexercises.add(new AllExercises(1,"Title 3"));
        listexercises.add(new AllExercises(1,"Title 4"));
        listexercises.add(new AllExercises(1,"Title 5"));
        listexercises.add(new AllExercises(1,"Title 6"));
        listexercises.add(new AllExercises(1,"Title 7"));
        listexercises.add(new AllExercises(1,"Title 8"));
        allExercisesAdapter = new AllExercisesAdapter(this,listexercises);
        rv_detailmorning.setAdapter(allExercisesAdapter);
        rv_detailmorning.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


        nav_back_detailmorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



}

package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.adapter.AllWorkoutAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.WorkoutGuidedAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllWorkout;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;

import java.util.ArrayList;


public class AllWorkoutMain extends AppCompatActivity {
    private RecyclerView rv_allworkout;
    AllWorkoutAdapter allWorkoutAdapter;
    ImageView imgHinh;
    Intent intent;
    ArrayList<AllWorkout> listallworkout = new ArrayList<>();
    ImageButton nav_back_allwork;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allworkout);
        rv_allworkout = findViewById(R.id.rv_allworkout);
        nav_back_allwork = findViewById(R.id.icon_back_toolbar);

        //Guided Workouts
        listallworkout.add(new AllWorkout(1,"Title 1","Description 1"));
        listallworkout.add(new AllWorkout(1,"Title 2","Description 2"));
        listallworkout.add(new AllWorkout(1,"Title 3","Description 3"));
        allWorkoutAdapter = new AllWorkoutAdapter(this,listallworkout);
        rv_allworkout.setAdapter(allWorkoutAdapter);
        rv_allworkout.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


        nav_back_allwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




}

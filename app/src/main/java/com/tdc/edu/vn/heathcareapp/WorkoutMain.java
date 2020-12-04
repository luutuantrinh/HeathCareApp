package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.adapter.AllWorkoutAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.WorkoutGuidedAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.WorkoutTrendingAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.WorkoutUnder15Adapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutTrending;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutUnder15;

import java.util.ArrayList;


public class WorkoutMain extends AppCompatActivity {
    private RecyclerView rv_guided;
    private RecyclerView rv_trending;
    private RecyclerView rv_under15minute;
    private WorkoutGuidedAdapter workoutGuidedAdapter;
    private WorkoutTrendingAdapter workoutTrendingAdapter;
    private WorkoutUnder15Adapter workoutUnder15Adapter;


    ArrayList<WorkoutGuided> listguided = new ArrayList<>();
    ArrayList<WorkoutTrending> listtrending = new ArrayList<>();
    ArrayList<WorkoutUnder15> listunder = new ArrayList<>();
    TextView txtworkout, txtexercises;
    Intent intent;
    Button btndetail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);
        rv_guided = findViewById(R.id.rv_guided);
        rv_trending = findViewById(R.id.rv_trending);
        rv_under15minute = findViewById(R.id.rv_under15minute);
        txtworkout = findViewById(R.id.txtworkout);
        txtexercises = findViewById(R.id.txtexercises);
        btndetail = findViewById(R.id.btndetail);
        intent = getIntent();

        //Guided Workouts
        listguided.add(new WorkoutGuided(1,"Title 1","Description 1"));
        listguided.add(new WorkoutGuided(1,"Title 2","Description 2"));
        listguided.add(new WorkoutGuided(1,"Title 3","Description 3"));
        workoutGuidedAdapter = new WorkoutGuidedAdapter(this,listguided);
        rv_guided.setAdapter(workoutGuidedAdapter);
        rv_guided.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));


        //Treding
        listtrending.add(new WorkoutTrending(1,"Title 1","Description 1"));
        listtrending.add(new WorkoutTrending(1,"Title 2","Description 2"));
        listtrending.add(new WorkoutTrending(1,"Title 3","Description 3"));
        workoutTrendingAdapter = new WorkoutTrendingAdapter(this,listtrending);
        rv_trending.setAdapter(workoutTrendingAdapter);
        rv_trending.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));




        //Under 15 minute
        listunder.add(new WorkoutUnder15(1,"Title 1","Description 1"));
        listunder.add(new WorkoutUnder15(1,"Title 2","Description 2"));
        listunder.add(new WorkoutUnder15(1,"Title 3","Description 3"));
        workoutUnder15Adapter = new WorkoutUnder15Adapter(this,listunder);
        rv_under15minute.setAdapter(workoutUnder15Adapter);
        rv_under15minute.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));



        //move all  workout
        txtworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WorkoutMain.this,AllWorkoutMain.class);
                startActivity(intent);
            }
        });

        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WorkoutMain.this,DetailMorningMain.class);
                startActivity(intent);
            }
        });
    }




}

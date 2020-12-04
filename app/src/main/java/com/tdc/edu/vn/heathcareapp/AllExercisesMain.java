package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.adapter.AllExercisesAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.AllWorkoutAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;

import java.util.ArrayList;


public class AllExercisesMain extends AppCompatActivity {
    private RecyclerView rv_allexercises;
    AllExercisesAdapter allExercisesAdapter;
    ImageView imgHinh;
    Intent intent;
    ArrayList<AllExercises> listexercises = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allexercises);
        rv_allexercises = findViewById(R.id.rv_allexercises);


        //Guided Workouts
        listexercises.add(new AllExercises(1,"Exercises"));
        listexercises.add(new AllExercises(1,"Exercises"));
        listexercises.add(new AllExercises(1,"Exercises"));
        allExercisesAdapter = new AllExercisesAdapter(this,listexercises);
        rv_allexercises.setAdapter(allExercisesAdapter);
        rv_allexercises.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


    }



}

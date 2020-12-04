package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.adapter.AllExercisesAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.FeedBackAAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;
import com.tdc.edu.vn.heathcareapp.data_model.FeedBackA;

import java.util.ArrayList;


public class FeedBackAMain extends AppCompatActivity {
    private RecyclerView rv_feedbacka;
    FeedBackAAdapter feedBackAAdapter;
    ArrayList<FeedBackA> listfeedback = new ArrayList<>();

    TextView txtNameCheck;
    RadioButton rdbcheck;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacka);
        rv_feedbacka = findViewById(R.id.rv_feedbackA);


        //Guided Workouts
        listfeedback.add(new FeedBackA("Too Easy",false));
        listfeedback.add(new FeedBackA("Too Hard",false));
        listfeedback.add(new FeedBackA("Not enough time",false));
        listfeedback.add(new FeedBackA("Didn't like the workout",false));

        feedBackAAdapter = new FeedBackAAdapter(this,listfeedback);
        rv_feedbacka.setAdapter(feedBackAAdapter);
        rv_feedbacka.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


    }



}

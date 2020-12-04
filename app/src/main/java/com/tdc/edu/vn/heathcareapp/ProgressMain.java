package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.adapter.ProgessChallengesAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.ProgessRecentAdapter;
import com.tdc.edu.vn.heathcareapp.adapter.WorkoutGuidedAdapter;
import com.tdc.edu.vn.heathcareapp.data_model.ProgressChallenges;
import com.tdc.edu.vn.heathcareapp.data_model.ProgressRecent;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;

import java.util.ArrayList;


public class ProgressMain extends AppCompatActivity {
    private RecyclerView rv_recent;
    private RecyclerView rv_challenges;

    private ProgessChallengesAdapter progessChallengesAdapter;
    private ProgessRecentAdapter progessRecentAdapter;
    Intent intent;
    ArrayList<ProgressRecent> listrecent = new ArrayList<>();
    ArrayList<ProgressChallenges> listchallenges = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        rv_recent = findViewById(R.id.rv_recent);
        rv_challenges = findViewById(R.id.rv_challenges);


        //Recent
        listrecent.add(new ProgressRecent(1,"Title 1","Description 1"));

        progessRecentAdapter = new ProgessRecentAdapter(this,listrecent);
        rv_recent.setAdapter(progessRecentAdapter);
        rv_recent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));


        // challenges
        listchallenges.add(new ProgressChallenges(1,"Title 1","Description 1"));
        listchallenges.add(new ProgressChallenges(1,"Title 1","Description 2"));
        listchallenges.add(new ProgressChallenges(1,"Title 1","Description 3"));
        progessChallengesAdapter = new ProgessChallengesAdapter(this,listchallenges);
        rv_challenges.setAdapter(progessChallengesAdapter);
        rv_challenges.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
    }

}

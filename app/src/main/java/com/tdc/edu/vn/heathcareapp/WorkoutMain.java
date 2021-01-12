package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.WorkoutGuidedAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.WorkoutTrendingAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.WorkoutUnder15Adapter;
import com.tdc.edu.vn.heathcareapp.Model.WorkoutGuided;
import com.tdc.edu.vn.heathcareapp.Model.WorkoutTrending;
import com.tdc.edu.vn.heathcareapp.Model.WorkoutUnder15;


import java.util.ArrayList;


public class WorkoutMain extends AppCompatActivity {
    private RecyclerView rv_guided;
    private RecyclerView rv_trending;
    private RecyclerView rv_under15minute;
    private WorkoutGuidedAdapter workoutGuidedAdapter;
    private WorkoutTrendingAdapter workoutTrendingAdapter;
    private WorkoutUnder15Adapter workoutUnder15Adapter;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Workout/Trending/ImageTrending");
    DatabaseReference myRef1 = database.getReference("Workout/Guided/Image");
    DatabaseReference myRef2 = database.getReference("Workout/Under/Image");

    ArrayList<WorkoutGuided> listguided = new ArrayList<>();
    ArrayList<WorkoutTrending> listtrending = new ArrayList<>();
    ArrayList<WorkoutUnder15> listunder = new ArrayList<>();
    TextView txtworkout, txtexercises;
    Intent intent;
    Button btndetail,btnstart1,btnplan;


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
        btnstart1 = findViewById(R.id.btnstart1);
        btnplan = findViewById(R.id.btnplan);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        intent = getIntent();

        //Guided Workouts
        /*listguided.add(new WorkoutGuided(1,"Title 1","Description 1"));
        listguided.add(new WorkoutGuided(1,"Title 2","Description 2"));
        listguided.add(new WorkoutGuided(1,"Title 3","Description 3"));
        workoutGuidedAdapter = new WorkoutGuidedAdapter(this,listguided);
        rv_guided.setAdapter(workoutGuidedAdapter);
        rv_guided.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));*/
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        WorkoutGuided workoutGuided = ds.getValue(WorkoutGuided.class);
                        listguided.add(workoutGuided);
                        //Toast.makeText(WorkoutMain.this, listguided.toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (listguided != null){
                        workoutGuidedAdapter = new WorkoutGuidedAdapter(WorkoutMain.this, listguided);
                        rv_guided.setAdapter(workoutGuidedAdapter);
                        rv_guided.setLayoutManager(new LinearLayoutManager(WorkoutMain.this));
                    }else {

                        Toast.makeText(WorkoutMain.this, "Danh Sách không có", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception exception){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Trending
        /*listtrending.add(new WorkoutTrending(1,"Title 1","Description 1"));
        listtrending.add(new WorkoutTrending(1,"Title 2","Description 2"));
        listtrending.add(new WorkoutTrending(1,"Title 3","Description 3"));
          workoutTrendingAdapter = new WorkoutTrendingAdapter(this,listtrending);
        rv_trending.setAdapter(workoutTrendingAdapter);
        rv_trending.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        WorkoutTrending workoutTrending = ds.getValue(WorkoutTrending.class);
                        listtrending.add(workoutTrending);
                        Toast.makeText(WorkoutMain.this, listtrending.toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (listtrending != null){
                        workoutTrendingAdapter = new WorkoutTrendingAdapter(WorkoutMain.this, listtrending);
                        rv_trending.setAdapter(workoutTrendingAdapter);
                        rv_trending.setLayoutManager(new LinearLayoutManager(WorkoutMain.this,RecyclerView.HORIZONTAL,false));
                    }else {

                        Toast.makeText(WorkoutMain.this, "Danh Sách không có", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception exception){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //Under 15 minute
        /*listunder.add(new WorkoutUnder15(1,"Title 1","Description 1"));
        listunder.add(new WorkoutUnder15(1,"Title 2","Description 2"));
        listunder.add(new WorkoutUnder15(1,"Title 3","Description 3"));
        workoutUnder15Adapter = new WorkoutUnder15Adapter(this,listunder);
        rv_under15minute.setAdapter(workoutUnder15Adapter);
        rv_under15minute.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));*/
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        WorkoutUnder15 workoutUnder15 = ds.getValue(WorkoutUnder15.class);
                        listunder.add(workoutUnder15);
                        //Toast.makeText(WorkoutMain.this, listtrending.toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (listunder != null){
                        workoutUnder15Adapter = new WorkoutUnder15Adapter(WorkoutMain.this, listunder);
                        rv_under15minute.setAdapter(workoutUnder15Adapter);
                        rv_under15minute.setLayoutManager(new LinearLayoutManager(WorkoutMain.this,RecyclerView.HORIZONTAL,false));
                    }else {

                        Toast.makeText(WorkoutMain.this, "Danh Sách không có", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception exception){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //move all  workout
        txtworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WorkoutMain.this, AllWorkoutMain.class);
                startActivity(intent);
            }
        });
        txtexercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WorkoutMain.this,AllExercisesMain.class);
                startActivity(intent);
            }
        });

        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WorkoutMain.this, DetailMorningMain.class);
                startActivity(intent);
            }
        });
        btnstart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WorkoutMain.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
        btnplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WorkoutMain.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

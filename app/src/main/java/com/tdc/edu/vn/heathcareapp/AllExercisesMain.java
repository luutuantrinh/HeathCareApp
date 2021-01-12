package com.tdc.edu.vn.heathcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
import com.tdc.edu.vn.heathcareapp.Adapter.AllExercisesAdapter;
import com.tdc.edu.vn.heathcareapp.Model.AllExercises;


import java.util.ArrayList;


public class AllExercisesMain extends AppCompatActivity {
    private RecyclerView rv_allexercises;
    AllExercisesAdapter allExercisesAdapter;

    ArrayList<AllExercises> listexercises = new ArrayList<>();
    ImageButton nav_back_allexercises;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef  = database.getReference("Workout/AllExerciese/Image");



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allexercises);
        rv_allexercises = findViewById(R.id.rv_allexercises);
        nav_back_allexercises = findViewById(R.id.icon_back_toolbar_allexercises);
        mStorageRef = FirebaseStorage.getInstance().getReference();

       /* listexercises.add(new AllExercises(1,"Exercises"));
        listexercises.add(new AllExercises(1,"Exercises"));
        listexercises.add(new AllExercises(1,"Exercises"));
        allExercisesAdapter = new AllExercisesAdapter(this,listexercises);
        rv_allexercises.setAdapter(allExercisesAdapter);
        rv_allexercises.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    AllExercises allExercises = ds.getValue(AllExercises.class);
                    listexercises.add(allExercises);
                }
                if(listexercises != null){
                    allExercisesAdapter = new AllExercisesAdapter(AllExercisesMain.this,listexercises);
                    rv_allexercises.setAdapter(allExercisesAdapter);
                    rv_allexercises.setLayoutManager(new LinearLayoutManager(AllExercisesMain.this, RecyclerView.VERTICAL,false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nav_back_allexercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



}

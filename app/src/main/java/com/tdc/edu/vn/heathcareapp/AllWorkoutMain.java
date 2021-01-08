package com.tdc.edu.vn.heathcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import com.tdc.edu.vn.heathcareapp.Adapter.AllWorkoutAdapter;
import com.tdc.edu.vn.heathcareapp.Model.AllWorkout;

import java.util.ArrayList;


public class AllWorkoutMain extends AppCompatActivity {
    private RecyclerView rv_allworkout;
    AllWorkoutAdapter allWorkoutAdapter;

    ArrayList<AllWorkout> listallworkout = new ArrayList<>();
    ImageButton nav_back_allwork;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Workout/AllWorkout/Image");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allworkout);
        rv_allworkout = findViewById(R.id.rv_allworkout);
        nav_back_allwork = findViewById(R.id.icon_back_toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        /*listallworkout.add(new AllWorkout(1,"Title 1","Description 1"));
        listallworkout.add(new AllWorkout(1,"Title 2","Description 2"));
        listallworkout.add(new AllWorkout(1,"Title 3","Description 3"));
        allWorkoutAdapter = new AllWorkoutAdapter(this,listallworkout);
        rv_allworkout.setAdapter(allWorkoutAdapter);
        rv_allworkout.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    AllWorkout allWorkout = ds.getValue(AllWorkout.class);
                    listallworkout.add(allWorkout);
                }
                if(listallworkout != null){
                    allWorkoutAdapter = new AllWorkoutAdapter(AllWorkoutMain.this,listallworkout);
                    rv_allworkout.setAdapter(allWorkoutAdapter);
                    rv_allworkout.setLayoutManager(new LinearLayoutManager(AllWorkoutMain.this, RecyclerView.VERTICAL,false));
                }else{
                    Toast.makeText(AllWorkoutMain.this,"Danh sách rỗng",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nav_back_allwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




}

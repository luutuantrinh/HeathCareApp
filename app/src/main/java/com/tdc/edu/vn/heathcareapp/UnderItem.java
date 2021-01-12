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
import com.tdc.edu.vn.heathcareapp.Adapter.UnderItemAdapter;
import com.tdc.edu.vn.heathcareapp.Model.AllExercises;


import java.util.ArrayList;


public class UnderItem extends AppCompatActivity {
    private RecyclerView rvitemunderitem;
    UnderItemAdapter underItemAdapter;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef  = database.getReference("Workout/Under/Image/1/Boxhit/Image");
    ArrayList<AllExercises> listexercises = new ArrayList<>();
    ImageButton nav_back_underitem;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.under_item);
        rvitemunderitem = findViewById(R.id.rv_underitem);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        nav_back_underitem = findViewById(R.id.icon_back_toolbar_underitem);
        /*listexercises.add(new AllExercises(1,"Title 1"));
        listexercises.add(new AllExercises(1,"Title 2"));
        listexercises.add(new AllExercises(1,"Title 4"));
        underItemAdapter = new UnderItemAdapter(this,listexercises);
        rvitemunderitem.setAdapter(underItemAdapter);
        rvitemunderitem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));*/
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    AllExercises allExercises = ds.getValue(AllExercises.class);
                    listexercises.add(allExercises);
                }
                if(listexercises != null){
                    underItemAdapter = new UnderItemAdapter(UnderItem.this,listexercises);
                    rvitemunderitem.setAdapter(underItemAdapter);
                    rvitemunderitem.setLayoutManager(new LinearLayoutManager(UnderItem.this, RecyclerView.VERTICAL,false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nav_back_underitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }


}

package com.tdc.edu.vn.heathcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.tdc.edu.vn.heathcareapp.Adapter.TrendingItemAdapter;
import com.tdc.edu.vn.heathcareapp.Model.AllExercises;

import java.util.ArrayList;


public class TrendingItem extends AppCompatActivity {
    private RecyclerView rvitemtrendingitem;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef  = database.getReference("Workout/Trending/ImageTrending/1/BoxHit/Image");
    TrendingItemAdapter trendingItemAdapter;
    ArrayList<AllExercises> listexercises = new ArrayList<>();
    ImageButton nav_back_trendingitem;
    Button btnstart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_item);
        rvitemtrendingitem = findViewById(R.id.rv_trendingitem);
        btnstart = findViewById(R.id.btnStarttrending);
        nav_back_trendingitem = findViewById(R.id.icon_back_toolbar_trendingitem);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        /*listexercises.add(new AllExercises(1,"Title 1"));
        listexercises.add(new AllExercises(1,"Title 2"));
        listexercises.add(new AllExercises(1,"Title 4"));*/

        /*trendingItemAdapter = new TrendingItemAdapter(this,listexercises);
        rvitemtrendingitem.setAdapter(trendingItemAdapter);
        rvitemtrendingitem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));*/


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    AllExercises allExercises = ds.getValue(AllExercises.class);
                    listexercises.add(allExercises);
                }
                if(listexercises != null){
                    trendingItemAdapter = new TrendingItemAdapter(TrendingItem.this,listexercises);
                    rvitemtrendingitem.setAdapter(trendingItemAdapter);
                    rvitemtrendingitem.setLayoutManager(new LinearLayoutManager(TrendingItem.this, RecyclerView.VERTICAL,false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nav_back_trendingitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendingItem.this,VideoMain.class);
                startActivity(intent);
            }
        });

    }


}

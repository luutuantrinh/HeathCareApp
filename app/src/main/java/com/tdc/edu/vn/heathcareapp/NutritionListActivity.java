package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAndNutritionAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.NutritionTowerAdapter;
import com.tdc.edu.vn.heathcareapp.Model.NewAndNutrition;

import java.util.ArrayList;

public class NutritionListActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    RecyclerView recyclerViewListNutrition;
    BottomNavigationView bottomNavigationView;
    //
    NewsAndNutritionAdapter newsAndNutritionAdapter;
    ArrayList<NewAndNutrition> dataNutrition = new ArrayList<>();
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postsNewAndNutritionRef = db.collection("PostsNewAndNutrition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_list);
        setControl();
        setEvent();
    }

    private void setEvent() {
        showDataNutrition();
        try {
//            newsAndNutritionAdapter = new NewsAndNutritionAdapter(dataNutrition, NutritionListActivity.this);
//            recyclerViewListNutrition.setAdapter(newsAndNutritionAdapter);
//            recyclerViewListNutrition.setLayoutManager(new LinearLayoutManager(NutritionListActivity.this));
        } catch (Exception exception) {

        }
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.Nutrition);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        Intent intent = new Intent(getApplicationContext(), NutritionActivity.class);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Nutrition:
                        return true;
                    case R.id.Profile:
                        Intent intu = new Intent(getApplicationContext(), ActivityProfile.class);
                        startActivity(intu);
                        intu.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Workouts:
                        mAuth.signOut();
                        Intent intent1 = new Intent(getApplicationContext(), SigninActivity.class);
                        startActivity(intent1);
                        return true;

                }
                return true;
            }
        });
    }

    private void showDataNutrition() {
        postsNewAndNutritionRef.whereEqualTo("category", "nutrition").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dataNutrition.clear();
                for (DocumentSnapshot ds : task.getResult()) {
                    NewAndNutrition newAndNutrition = ds.toObject(NewAndNutrition.class);
                    dataNutrition.add(newAndNutrition);
                }
                newsAndNutritionAdapter = new NewsAndNutritionAdapter(dataNutrition, NutritionListActivity.this);
                recyclerViewListNutrition.setAdapter(newsAndNutritionAdapter);
                recyclerViewListNutrition.setLayoutManager(new LinearLayoutManager(NutritionListActivity.this, RecyclerView.VERTICAL, false));
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_nutrition_list);
        recyclerViewListNutrition = findViewById(R.id.rcy_list_nutrition_list);
    }
}
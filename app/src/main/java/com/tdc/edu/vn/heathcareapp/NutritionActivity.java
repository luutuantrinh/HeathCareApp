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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.NutritionTowerAdapter;
import com.tdc.edu.vn.heathcareapp.Model.NewAndNutrition;
import com.tdc.edu.vn.heathcareapp.Model.NutritionTower;

import java.util.ArrayList;

public class NutritionActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tv_see_more_news, tv_see_more_nutrition_tower;
    RecyclerView recyclerViewNutrition, recyclerViewNews;
    NutritionTowerAdapter nutritionTowerAdapter;
    NewsAdapter newsAdapter;
    ArrayList<NewAndNutrition> dataNews = new ArrayList<>();
    ArrayList<NewAndNutrition> dataNutrition = new ArrayList<>();
    ImageButton imageButtonChat;
    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postsNewAndNutritionRef = db.collection("PostsNewAndNutrition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setEvent() {
        showDataNews();
        showDataNutrition();
        try {
            nutritionTowerAdapter = new NutritionTowerAdapter(NutritionActivity.this, dataNutrition);
            recyclerViewNutrition.setAdapter(nutritionTowerAdapter);
            recyclerViewNutrition.setLayoutManager(new LinearLayoutManager(NutritionActivity.this, RecyclerView.HORIZONTAL, false));

            newsAdapter = new NewsAdapter(dataNews, NutritionActivity.this);
            recyclerViewNews.setAdapter(newsAdapter);
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(NutritionActivity.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error: " + ex, Toast.LENGTH_SHORT).show();
        }

        tv_see_more_nutrition_tower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NutritionListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        tv_see_more_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NutritionActivity.this, ConversationListActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.Nutrition);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        Intent intent = new Intent(getApplicationContext(), NewFeedActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        return true;
                    case R.id.Nutrition:
                        return true;
                    case R.id.Profile:
                        Intent inten = new Intent(getApplicationContext(), ActivityProfile.class);
                        startActivity(inten);
                        overridePendingTransition(0, 0);
                        inten.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        return true;

                }
                return true;
            }
        });
    }

    private void showDataNutrition() {
        postsNewAndNutritionRef.orderBy("timestamp", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dataNutrition.clear();
                for (DocumentSnapshot ds : task.getResult()) {
                    NewAndNutrition newAndNutrition = ds.toObject(NewAndNutrition.class);
                    if (newAndNutrition.getCategory().equals("nutrition")) {
                        dataNutrition.add(newAndNutrition);
                    }

                }
                nutritionTowerAdapter = new NutritionTowerAdapter(NutritionActivity.this, dataNutrition);
                recyclerViewNutrition.setAdapter(nutritionTowerAdapter);
                recyclerViewNutrition.setLayoutManager(new LinearLayoutManager(NutritionActivity.this, RecyclerView.HORIZONTAL, false));
            }
        });
    }

    private void showDataNews() {
        postsNewAndNutritionRef.orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dataNews.clear();
                for (DocumentSnapshot ds : task.getResult()) {
                    NewAndNutrition newAndNutrition = ds.toObject(NewAndNutrition.class);
                    if (newAndNutrition.getCategory().equals("news")) {
                        dataNews.add(newAndNutrition);
                    }


                }
                newsAdapter = new NewsAdapter(dataNews, NutritionActivity.this);
                recyclerViewNews.setAdapter(newsAdapter);
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
        recyclerViewNutrition = findViewById(R.id.ryc_nutrition_tower);
        recyclerViewNews = findViewById(R.id.ryc_news);
        tv_see_more_news = findViewById(R.id.tv_see_more_news);
        tv_see_more_nutrition_tower = findViewById(R.id.tv_see_more_nutrition);
        imageButtonChat = findViewById(R.id.icon_chat_toolbar);
    }

}
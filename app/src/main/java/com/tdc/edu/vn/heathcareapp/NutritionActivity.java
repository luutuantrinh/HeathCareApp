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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.NutritionTowerAdapter;
import com.tdc.edu.vn.heathcareapp.Model.New;
import com.tdc.edu.vn.heathcareapp.Model.NutritionTower;

import java.util.ArrayList;

public class NutritionActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tv_see_more_news, tv_see_more_nutrition_tower;
    RecyclerView recyclerViewNutrition, recyclerViewNews;
    NutritionTowerAdapter nutritionTowerAdapter;
    NewsAdapter newsAdapter;
    ArrayList<NutritionTower> arrayListData = new ArrayList<>();
    ArrayList<New> dataNews = new ArrayList<>();
    ImageButton imageButtonChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        setControl();
        setEvent();
    }

    private void setEvent() {
        NutritionTower nutritionTower1 = new NutritionTower(1, "Van Phong", "13333");
        arrayListData.add(nutritionTower1);
        NutritionTower nutritionTower2 = new NutritionTower(2, "Hoc Sinh", "13333");
        arrayListData.add(nutritionTower2);
        NutritionTower nutritionTower3 = new NutritionTower(3, "Sinh Vien", "13333");
        arrayListData.add(nutritionTower3);

        New news1 = new New("10 Loai thuc pham tot cho gan", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 1);
        dataNews.add(news1);
        New news2 = new New("Rau cu ngan lao hoa", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 2);
        dataNews.add(news2);
        New news3 = new New("Rau cho mat", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 3);
        dataNews.add(news3);
        New news4 = new New("7 Loai rau bo sung", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 4);
        dataNews.add(news4);
        New news5 = new New("Rau muon danh cho sinh vien", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 5);
        dataNews.add(news5);
        try {
            nutritionTowerAdapter = new NutritionTowerAdapter(NutritionActivity.this, arrayListData);
            recyclerViewNutrition.setAdapter(nutritionTowerAdapter);
            recyclerViewNutrition.setLayoutManager(new LinearLayoutManager(NutritionActivity.this, RecyclerView.HORIZONTAL, false));


            newsAdapter  =new NewsAdapter(dataNews,NutritionActivity.this);
            recyclerViewNews.setAdapter(newsAdapter);
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(NutritionActivity.this));
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(NutritionActivity.this, ChatListActivity.class);
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

                }
                return true;
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
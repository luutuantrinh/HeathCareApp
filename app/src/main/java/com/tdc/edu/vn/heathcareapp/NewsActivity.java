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
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Model.New;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    NewsAdapter newsAdapter;
    ArrayList<New> dataNews = new ArrayList<>();
    RecyclerView recyclerViewNews;
    ImageButton imageButtonBackSpace;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setControl();
        setEvent();
    }

    private void setEvent() {

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
        New news6 = new New("Rau muon danh cho sinh vien", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 5);
        dataNews.add(news6);
        New news7 = new New("Rau muon danh cho sinh vien", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 5);
        dataNews.add(news7);
        New news8 = new New("Rau muon danh cho sinh vien", "Rau bina, rau xanh. Để cải thiện thị lực, phải thường xuyên ăn rau bina cùng với các loại rau xanh khác, như cải xoăn, các loại rau xanh.", "", 5);
        dataNews.add(news8);

        newsAdapter = new NewsAdapter(dataNews, NewsActivity.this);
        recyclerViewNews.setAdapter(newsAdapter);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(NewsActivity.this));


        bottomNavigationView.setSelectedItemId(R.id.Nutrition);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        Intent intent = new Intent(getApplicationContext(), NewFeedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Nutrition:
                        return true;

                }
                return true;
            }
        });

        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setControl() {
        recyclerViewNews = findViewById(R.id.rcy_news);
        bottomNavigationView = findViewById(R.id.BottomNavViewNews);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_news);
    }
}
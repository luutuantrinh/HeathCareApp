package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Model.New;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    NewsAdapter newsAdapter;
    ArrayList<New> dataNews = new ArrayList<>();
    RecyclerView recyclerViewNews;
    ImageButton imageButtonBackSpace;
    BottomNavigationView bottomNavigationView;
    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference cateRef = db.document("News/NewsTest");
    private CollectionReference categoryRef = db.collection("News");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                dataNews.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    New news = documentSnapshot.toObject(New.class);
                    news.setId_new(documentSnapshot.getId());
                    String title = news.getTitle_new();
                    String id = news.getId_new();
                    String des = news.getContent_new();
                    String url = news.getUrl_new();
                    dataNews.add(new New(id, title, des, url, ""));
                }

                newsAdapter = new NewsAdapter(dataNews, NewsActivity.this);
                recyclerViewNews.setAdapter(newsAdapter);
            }
        });
    }

    private void setEvent() {
        try {
            newsAdapter = new NewsAdapter(dataNews, NewsActivity.this);
            recyclerViewNews.setAdapter(newsAdapter);
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
        }catch (Exception ex){

        }


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
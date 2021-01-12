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

import com.facebook.internal.CollectionMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Model.New;
import com.tdc.edu.vn.heathcareapp.Model.NewAndNutrition;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    NewsAdapter newsAdapter;
    RecyclerView recyclerViewNews;
    ImageButton imageButtonBackSpace;
    BottomNavigationView bottomNavigationView;
    ArrayList<NewAndNutrition> dataNews = new ArrayList<>();
    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postsNewAndNutritionRef = db.collection("PostsNewAndNutrition");

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

    }

    private void setEvent() {
        showDataNews();
        try {
            //newsAdapter = new NewsAdapter(dataNews, NewsActivity.this);
            recyclerViewNews.setAdapter(newsAdapter);
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
        } catch (Exception ex) {

        }


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

        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showDataNews() {
//        Query query = postsNewAndNutritionRef.whereEqualTo("category", "news").orderBy("timestamp");
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

                newsAdapter = new NewsAdapter(dataNews, NewsActivity.this);
                recyclerViewNews.setAdapter(newsAdapter);
            }
        });
    }

    private void setControl() {
        recyclerViewNews = findViewById(R.id.rcy_news);
        bottomNavigationView = findViewById(R.id.BottomNavView);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_news);
    }
}
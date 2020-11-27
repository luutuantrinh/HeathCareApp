package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewFeedActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        setControl();
        setEvent();
    }

    private void setEvent() {
        bottomNavigationView.setSelectedItemId(R.id.NewFeed);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        return true;
                    case R.id.Nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return true;
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
    }

}
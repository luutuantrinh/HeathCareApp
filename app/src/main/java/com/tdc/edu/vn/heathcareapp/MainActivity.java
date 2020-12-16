package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.




    















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new WorkoutsFragment()).commit();
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.NewFeed:
                    fragment = new NewFeedFragment();
                    break;
                case R.id.Nutrition:
                    fragment = new NutritionFragment();
                    break;
                case R.id.Progress:
                    fragment = new ProgressFragment();
                    break;
                case R.id.Workouts:
                    fragment = new WorkoutsFragment();
                    break;
                case R.id.More:
                    fragment = new MoreFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };
}
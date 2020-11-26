package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

    }

    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        setTitle("New Feed");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewFeedFragment()).commit();
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);

    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.NewFeed:
                    fragment = new NewFeedFragment();
                    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    getSupportActionBar().setCustomView(R.layout.toolbar_newfeed);
                    setTitle("New Feed");
                    break;
                case R.id.Nutrition:
                    fragment = new NutritionFragment();
                    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    getSupportActionBar().setCustomView(R.layout.toolbar_nutrition);
                    setTitle("Nutrition");

                    break;
                case R.id.Progress:
                    fragment = new ProgressFragment();
                    setTitle("Progress");
                    break;
                case R.id.Workouts:
                    fragment = new WorkoutsFragment();
                    setTitle("Workouts");
                    break;
                case R.id.More:
                    fragment = new MoreFragment();
                    setTitle("More");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };
}
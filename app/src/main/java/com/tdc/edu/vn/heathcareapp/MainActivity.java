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


    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);

    }

//    BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment = null;
//            switch (item.getItemId()) {
//                case R.id.NewFeed:
//
//
//                    setTitle("New Feed");
//                    break;
//                case R.id.Nutrition:
//
//
//                    setTitle("Nutrition");
//
//                    break;
//                case R.id.Progress:
//
//                    setTitle("Progress");
//                    break;
//                case R.id.Workouts:
//
//                    setTitle("Workouts");
//                    break;
//                case R.id.More:
//
//                    setTitle("More");
//                    break;
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//            return true;
//        }
//    };

}
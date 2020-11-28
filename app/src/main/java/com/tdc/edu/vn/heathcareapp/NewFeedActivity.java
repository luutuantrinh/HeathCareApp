package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewFeedActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView titleToolBar;
    ImageButton imageButtonCreateContent, imageButtonAddFriends, imageButtonNotification;
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

        imageButtonCreateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Create Content", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewFeedActivity.this, CreateContentActivity.class);
                startActivity(intent);
            }
        });

        imageButtonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add Friends", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewFeedActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });

        imageButtonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewFeedActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
        imageButtonAddFriends = findViewById(R.id.icon_addFriend_newFeed);
        imageButtonCreateContent = findViewById(R.id.icon_createContent_newFeed);
        imageButtonNotification = findViewById(R.id.icon_notification_newFeed);
        titleToolBar = findViewById(R.id.titleNewFeed);
    }

}
package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class FollowActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    ViewPager viewPager;
    TabLayout tabLayoutFollow;
    TabItem tab_follower, tab_following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        setControl();
        setEvent();
    }

    private void setEvent() {
        tab_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayoutFollow.getTabAt(1).select();
            }
        });

        tab_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayoutFollow.getTabAt(0).select();
            }
        });


    }

    private void setControl() {
        tabLayoutFollow = findViewById(R.id.tabLayout_follow);
        tab_follower = findViewById(R.id.tab_item_follower);
        tab_following = findViewById(R.id.tab_item_following);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_follow);
        viewPager = findViewById(R.id.viewPage_follow);
    }
}
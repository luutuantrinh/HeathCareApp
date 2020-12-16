package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddFriendActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    TextView titleToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setControl();
        setEvent();
    }

    private void setEvent() {
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_add_friends);
        titleToolbar = findViewById(R.id.title_toolbar_add_friends);
    }
}
package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateContentActivity extends AppCompatActivity {

    Button buttonCancel, buttonPublish;
    TextView titleToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        setControl();
        setEvent();
    }

    private void setEvent() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void setControl() {
        buttonCancel = findViewById(R.id.btn_cancel_createContent);
        buttonPublish = findViewById(R.id.btn_publish_createContent);
        titleToolbar = findViewById(R.id.title_toolbar_createContent);
    }
}
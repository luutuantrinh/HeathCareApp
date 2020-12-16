package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdc.edu.vn.heathcareapp.Model.Post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateContentActivity extends AppCompatActivity {

    TextView titleToolbar, buttonCancel, buttonPublish;;
    EditText txt_content;
    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference PostRef = db.collection("Posts");
    // Date
    public static final String DATE_FORMAT = "yyyy-MM-dd-HH:mm:ss";
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
        buttonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = txt_content.getText().toString(); // id_post, user_id, image_id, content_post, day_create;
                Post post = new Post(System.currentTimeMillis()+"", "","", content, getCurrentDate(), 0);
                PostRef.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        onBackPressed();
                    }
                });
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void setControl() {
        buttonCancel = findViewById(R.id.btn_cancel_createContent);
        buttonPublish = findViewById(R.id.btn_publish_createContent);
        titleToolbar = findViewById(R.id.title_toolbar_createContent);
        txt_content = findViewById(R.id.txt_content_create);
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
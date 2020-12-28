package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.NotificationAdapter;
import com.tdc.edu.vn.heathcareapp.Functions.TimeFunc;
import com.tdc.edu.vn.heathcareapp.Functions.UpdateData;
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.Notification;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationsActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonSettingsNotifications;
    TextView total_request_follow, tv_mark_all_as_read;
    LinearLayout linearLayoutRequestFollow;
    RecyclerView recyclerViewNotification;
    NotificationAdapter notificationAdapter;
    ArrayList<Notification> dataNotification = new ArrayList<>();
    UpdateData updateData = new UpdateData();
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsRef = database.getReference("Posts");
    DatabaseReference followRef = database.getReference("Follows");
    DatabaseReference notificationRef = database.getReference("Notifications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void setEvent() {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String user_id = currentUser.getUid();
        tv_mark_all_as_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.updateSeenNotification(user_id);
            }
        });
        showListNotification(user_id);
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count_AddFriend = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getReceiver().equals(user_id) && follow.getRequest_status() == false) {
                        count_AddFriend += 1;
                    }
                }
                if (count_AddFriend > 0) {
                    String total = String.valueOf(count_AddFriend);
                    total_request_follow.setText(total);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageButtonSettingsNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, SettingsNotificationActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutRequestFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, RequestFriendActivity.class));
            }
        });
    }

    private void showListNotification(String user_id) {
        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataNotification.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Notification notification = ds.getValue(Notification.class);
                    if (notification.getUser_id().equals(user_id)) {
                        dataNotification.add(notification);
                    }
                }
                if (dataNotification != null) {
                    try {
                        Collections.reverse(dataNotification);
                        notificationAdapter = new NotificationAdapter(NotificationsActivity.this, dataNotification);
                        recyclerViewNotification.setAdapter(notificationAdapter);
                        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));
                    } catch (Exception exception) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_notifications);
        imageButtonSettingsNotifications = findViewById(R.id.icon_settings_notifications);
        total_request_follow = findViewById(R.id.tv_total_request_follow);
        linearLayoutRequestFollow = findViewById(R.id.lnl_request_friend_notification);
        recyclerViewNotification = findViewById(R.id.rcy_notification);
        tv_mark_all_as_read = findViewById(R.id.tv_mark_all_as_read);
    }
}
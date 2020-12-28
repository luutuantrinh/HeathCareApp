package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.RequestFriendAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Follow;

import java.util.ArrayList;

public class RequestFriendActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    ArrayList<Follow> dataFollow = new ArrayList<>();
    RequestFriendAdapter requestFriendAdapter;
    RecyclerView recyclerViewRequestFollow;
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference followRef = database.getReference("Follows");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_friend);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setEvent() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user_id = currentUser.getUid();
        deleteNotification(user_id);
        showListRequestFollow(user_id);
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void deleteNotification(String user_id) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getSeen() == false && follow.getReceiver().equals(user_id)){
                        //String id_follow, String sender, String receiver, String timestamp, Boolean request_status, Boolean isSeen
                        String ID_FOLLOW = follow.getId_follow();
                        String SENDER = follow.getSender();
                        String RECEIVER = follow.getReceiver();
                        String TIMESTAMP = follow.getTimestamp();
                        Boolean REQUEST_STATUS = follow.getRequest_status();
                        followRef.child(ID_FOLLOW).setValue(new Follow(ID_FOLLOW, SENDER, RECEIVER, TIMESTAMP, REQUEST_STATUS, true));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showListRequestFollow(String user_id) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataFollow.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getReceiver().equals(user_id) && follow.getRequest_status() == false) {
                        dataFollow.add(follow);
                    }
                }

                if (dataFollow != null) {
                    try {
                        requestFriendAdapter = new RequestFriendAdapter(RequestFriendActivity.this, dataFollow);
                        recyclerViewRequestFollow.setAdapter(requestFriendAdapter);
                        recyclerViewRequestFollow.setLayoutManager(new LinearLayoutManager(RequestFriendActivity.this));
                    } catch (Exception ex) {

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_request_friend);
        recyclerViewRequestFollow = findViewById(R.id.rcy_request_friend_list);
    }
}
package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.SearchFriendAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class FollowActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    EditText txt_search_follow;
    TabLayout tabLayoutFollow;
    RecyclerView recyclerViewListFollow;
    ArrayList<Follow> dataFollow = new ArrayList<>();
    ArrayList<User> dataUserFollower = new ArrayList<>();
    ArrayList<User> dataUserFollowing = new ArrayList<>();
    TabItem tab_follower, tab_following;
    SearchFriendAdapter searchFriendAdapter;

    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference followRef = database.getReference("Follows");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void setEvent() {
        String user_id = getIntent().getExtras().getString("user_id");
        getUserFollower(user_id);
        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollower);
        recyclerViewListFollow.setAdapter(searchFriendAdapter);
        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
        searchFriendAdapter.notifyDataSetChanged();
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tabLayoutFollow.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        getUserFollower(user_id);
                        Toast.makeText(getApplicationContext(), tab.getText(), Toast.LENGTH_SHORT).show();
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollower);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                        break;
                    case 1:
                        getUserFollowing(user_id);
                        Toast.makeText(getApplicationContext(), tab.getText(), Toast.LENGTH_SHORT).show();
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollowing);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getUserFollower(user_id);
                        Toast.makeText(getApplicationContext(), tab.getText(), Toast.LENGTH_SHORT).show();
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollower);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                        break;
                    case 1:
                        getUserFollowing(user_id);
                        Toast.makeText(getApplicationContext(), tab.getText(), Toast.LENGTH_SHORT).show();
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollowing);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                        break;
                }
            }
        });
    }

    private void getUserFollowing(String user_id) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUserFollower.clear();
                dataUserFollowing.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getSender().equals(user_id)) {
                        dataFollow.add(follow);
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User user = ds.getValue(User.class);
                                    if (user.getUser_id().equals(follow.getReceiver())) {
                                        dataUserFollowing.add(user);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
                if (dataUserFollowing != null) {
                    try {
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollowing);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                    } catch (Exception exception) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserFollower(String user_id) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUserFollower.clear();
                dataUserFollowing.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getReceiver().equals(user_id)) {
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User user = ds.getValue(User.class);
                                    if (user.getUser_id().equals(follow.getSender())) {
                                        dataUserFollower.add(user);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                if (dataUserFollower != null) {
                    try {
                        searchFriendAdapter = new SearchFriendAdapter(FollowActivity.this, dataUserFollower);
                        recyclerViewListFollow.setAdapter(searchFriendAdapter);
                        recyclerViewListFollow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
                    } catch (Exception exception) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserFollow(String user_id) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUserFollower.clear();
                dataUserFollowing.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getReceiver().equals(user_id)) {
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User user = ds.getValue(User.class);
                                    if (user.getUser_id().equals(follow.getReceiver())) {
                                        dataUserFollower.add(user);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (follow.getSender().equals(user_id)) {
                        dataFollow.add(follow);
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User user = ds.getValue(User.class);
                                    if (user.getUser_id().equals(follow.getSender())) {
                                        dataUserFollowing.add(user);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        tabLayoutFollow = findViewById(R.id.tabLayout_follow);
        tab_follower = findViewById(R.id.tab_item_follower);
        tab_following = findViewById(R.id.tab_item_following);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_follow);
        recyclerViewListFollow = findViewById(R.id.rcy_followList);
        txt_search_follow = findViewById(R.id.txt_search_follow);

    }
}
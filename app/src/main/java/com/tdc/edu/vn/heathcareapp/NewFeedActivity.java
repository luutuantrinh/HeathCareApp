package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.PostAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NewFeedActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView titleToolBar, tv_badge_chat, tv_badge_addFriend, tv_badge_notification;
    ImageButton imageButtonCreateContent, imageButtonAddFriends, imageButtonNotification, imageButtonChat;
    ArrayList<Post> dataPosts = new ArrayList<>();
    ArrayList<String> dataFollower = new ArrayList<>();
    SwipeRefreshLayout swipeRefresh_new_feed;
    PostAdapter postAdapter;
    RecyclerView recyclerViewPost;
    CardView cv_badge_chat, cv_addFriend_chat, cv_badge_notification;

    int count_notification = 0;
    int count_message_new = 0;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsRef = database.getReference("Posts");
    DatabaseReference followRef = database.getReference("Follows");
    DatabaseReference messageRef = database.getReference("Messages");
    DatabaseReference notificationRef = database.getReference("Notifications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_badge_notification.setText("0");
        cv_badge_notification.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        count_notification = 0;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user_id = currentUser.getUid();

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_message_new = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    if (message.getReceiver().equals(user_id) && message.getSeen() == false) {
                        count_message_new += 1;
                    }
                }
                if (count_message_new > 0) {
                    String total = String.valueOf(count_message_new);
                    tv_badge_chat.setText(total);
                    cv_badge_chat.setVisibility(View.VISIBLE);
                } else {
                    tv_badge_chat.setText("0");
                    cv_badge_chat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_notification = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getReceiver().equals(user_id) && follow.getSeen() == false) {
                        count_notification += 1;
                    }
                }
                if (count_notification > 0) {
                    String total = String.valueOf(count_notification);
                    tv_badge_notification.setText(total);
                    cv_badge_notification.setVisibility(View.VISIBLE);
                } else {
                    tv_badge_notification.setText("0");
                    cv_badge_notification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Notification notification = ds.getValue(Notification.class);
                    if (notification.getUser_id().equals(user_id) && notification.getSeen() == false && !notification.getUrl().equals("follow")) {
                        count_notification += 1;
                    }
                }
                if (count_notification > 0) {
                    String total = String.valueOf(count_notification);
                    tv_badge_notification.setText(total);
                    cv_badge_notification.setVisibility(View.VISIBLE);
                } else {
                    tv_badge_notification.setText("0");
                    cv_badge_notification.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myId = currentUser.getUid();
        try {
            loadDataPost(myId);
            postAdapter = new PostAdapter(NewFeedActivity.this, dataPosts);
            recyclerViewPost.setAdapter(postAdapter);
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(NewFeedActivity.this));
        } catch (Exception ex) {

        }

        swipeRefresh_new_feed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataPost(myId);
                postAdapter.notifyDataSetChanged();
                swipeRefresh_new_feed.setRefreshing(false);
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.NewFeed);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        return true;
                    case R.id.Nutrition:
                        Intent intent = new Intent(getApplicationContext(), NutritionActivity.class);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.Profile:
                        Intent intu = new Intent(getApplicationContext(), ActivityProfile.class);
                        startActivity(intu);
                        intu.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Workouts:
                        mAuth.signOut();
                        Intent intent1 = new Intent(getApplicationContext(), SigninActivity.class);
                        startActivity(intent1);
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

        imageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Chat", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewFeedActivity.this, ChatUserToUserActivity.class);
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

    private ArrayList<String> getListFollower(String user_id) {
        ArrayList<String> data = new ArrayList<>();
        data.add(user_id);
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getSender().equals(user_id) && follow.getRequest_status() == true) {
                        data.add(follow.getReceiver());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return data;
    }

    private void loadDataPost(String myId) {
        dataFollower = getListFollower(myId);
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPosts.clear();
                if (snapshot.getChildren() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Post post = ds.getValue(Post.class);
                        String ID_USER_POST = post.getUser_id();
                        if (dataFollower.contains(ID_USER_POST)) {
                            dataPosts.add(post);
                        }
                    }
                    Collections.reverse(dataPosts);
                    postAdapter = new PostAdapter(NewFeedActivity.this, dataPosts);
                    recyclerViewPost.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void setControl() {
        bottomNavigationView = findViewById(R.id.BottomNavView);
        imageButtonAddFriends = findViewById(R.id.icon_addFriend_newFeed);
        imageButtonCreateContent = findViewById(R.id.icon_createContent_newFeed);
        imageButtonNotification = findViewById(R.id.icon_notification_newFeed);
        titleToolBar = findViewById(R.id.titleNewFeed);
        recyclerViewPost = findViewById(R.id.rcy_post_newFeed);
        cv_addFriend_chat = findViewById(R.id.cv_add_new_feed);
        cv_badge_chat = findViewById(R.id.cv_chat_new_feed);
        cv_badge_notification = findViewById(R.id.cv_notification_new_feed);
        tv_badge_addFriend = findViewById(R.id.badge_add_new_feed);
        tv_badge_chat = findViewById(R.id.badge_chat_new_feed);
        tv_badge_notification = findViewById(R.id.badge_notification_new_feed);
        imageButtonChat = findViewById(R.id.icon_chat_newFeed);
        swipeRefresh_new_feed = findViewById(R.id.swipeRefresh_new_feed);
    }

}
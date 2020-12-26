package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdc.edu.vn.heathcareapp.Adapter.NewsAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.PostAdapter;
import com.tdc.edu.vn.heathcareapp.Model.New;
import com.tdc.edu.vn.heathcareapp.Model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NewFeedActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView titleToolBar;
    ImageButton imageButtonCreateContent, imageButtonAddFriends, imageButtonNotification;
    ArrayList<Post> dataPosts = new ArrayList<>();
    PostAdapter postAdapter;
    RecyclerView recyclerViewPost;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsRef = database.getReference("Posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void setEvent() {

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new WorkoutsFragment()).commit();
        try {
            postAdapter = new PostAdapter(NewFeedActivity.this, dataPosts);
            recyclerViewPost.setAdapter(postAdapter);
            recyclerViewPost.setLayoutManager(new LinearLayoutManager(NewFeedActivity.this));
        }catch (Exception ex){

        }
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildren() != null){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Post post = ds.getValue(Post.class);
                        dataPosts.add(post);
                    }
                    Collections.reverse(dataPosts);
                    postAdapter = new PostAdapter( NewFeedActivity.this, dataPosts);
                    recyclerViewPost.setAdapter(postAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bottomNavigationView.setSelectedItemId(R.id.NewFeed);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                switch (item.getItemId()) {
//                    case R.id.NewFeed:
//                        return true;
//                    case R.id.Nutrition:
//                        Intent intent = new Intent(getApplicationContext(), NutritionActivity.class);
//                        startActivity(intent);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        overridePendingTransition(0, 0);
//
//                        return true;
//                    case R.id.Profile:
//                        fragment = new ProfileFragment();
//                        return true;
//                }
//                return true;
//            }
//        });
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
        recyclerViewPost = findViewById(R.id.rcy_post_newFeed);
    }

}
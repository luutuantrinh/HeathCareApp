package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.tdc.edu.vn.heathcareapp.Adapter.PostAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Post;

import java.util.ArrayList;

public class NewFeedActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView titleToolBar;
    ImageButton imageButtonCreateContent, imageButtonAddFriends, imageButtonNotification;
    ArrayList<Post> dataPosts = new ArrayList<>();
    PostAdapter postAdapter;
    RecyclerView recyclerViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Post post1 = new Post("4", "Hoa no khong mau", "07:30 - 30/11/2020", 1, 1, 4);
        dataPosts.add(post1);
        Post post2 = new Post("1", "Hãy cứ khát khao, hãy cứ dại khờ.", "07:30 - 30/11/2020", 1, 1, 100);
        dataPosts.add(post2);
        Post post3 = new Post("4", "Vấn đề không phải là tiền bạc. Vấn đề nằm ở bài học làm người, cách bạn làm nhà lãnh đạo và những gì mà bạn thu nhận được.", "07:30 - 30/11/2020", 1, 1, 78);
        dataPosts.add(post3);
        Post post4 = new Post("7", "Chẳng có gì trở nên dễ dàng hơn. Chỉ là bạn trở nên mạnh mẽ hơn mà thôi.", "07:30 - 30/11/2020", 1, 1, 40);
        dataPosts.add(post4);
        Post post5 = new Post("4", "Đôi lúc bạn phạm sai lầm khi đang đổi mới. Tốt nhất là hãy nhanh chóng chấp nhận nó và tiếp tục cải thiện các đổi mới khác của mình.", "07:30 - 30/11/2020", 1, 1, 0);
        dataPosts.add(post5);

        postAdapter = new PostAdapter(NewFeedActivity.this, dataPosts);
        recyclerViewPost.setAdapter(postAdapter);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(NewFeedActivity.this));

        bottomNavigationView.setSelectedItemId(R.id.NewFeed);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        return true;
                    case R.id.Nutrition:
                        Intent intent = new Intent(getApplicationContext(), NutritionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
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
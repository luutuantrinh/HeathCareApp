package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tdc.edu.vn.heathcareapp.Adapter.CommentAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Comment;

import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore;
    ArrayList<Comment> dataComments = new ArrayList<>();
    CommentAdapter commentAdapter;
    RecyclerView recyclerViewCmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Comment comment1 = new Comment(1, 1, "Abc", "05:59 - 20/11/2020");
        dataComments.add(comment1);
        Comment comment2 = new Comment(2, 1, "Co cong mai code co ngay ra truong do nha, android studio la cai gi vay chu no?", "06:59 - 20/11/2020");
        dataComments.add(comment2);
        Comment comment3 = new Comment(2, 1, "hay do ban yeu!", "06:59 - 20/11/2020");
        dataComments.add(comment3);
        Comment comment4 = new Comment(2, 1, "hay do ban yeu!", "06:59 - 20/11/2020");
        dataComments.add(comment4);

        commentAdapter = new CommentAdapter(DetailPostActivity.this, dataComments);
        recyclerViewCmt.setAdapter(commentAdapter);
        recyclerViewCmt.setLayoutManager(new LinearLayoutManager(DetailPostActivity.this));

        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_detail_post);
        imageButtonMore = findViewById(R.id.icon_more_detail_post);
        recyclerViewCmt = findViewById(R.id.rcy_cmt_detail_post);
    }
}
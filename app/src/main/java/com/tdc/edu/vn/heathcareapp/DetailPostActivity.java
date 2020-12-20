package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.CommentAdapter;
import com.tdc.edu.vn.heathcareapp.Functions.TimeFunc;
import com.tdc.edu.vn.heathcareapp.Model.Comment;
import com.tdc.edu.vn.heathcareapp.Model.Post;

import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore, imageButtonSend;
    TextView tv_content_post, tv_nameUser, tv_total_like, tv_timestamp;
    ImageView imageViewUser, imageViewPost;
    ArrayList<Comment> dataComments = new ArrayList<>();
    CommentAdapter commentAdapter;
    RecyclerView recyclerViewCmt;
    EditText editTextContentComment;
    ScrollView scv_detail_post;
    String id_post = "";
    ArrayList<Post> mDataPost = new ArrayList<>();
    TimeFunc timeFunc;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference PostsRef = database.getReference("Posts");
    DatabaseReference commentsRef = database.getReference("Comments");
    DatabaseReference userRef = database.getReference("Users");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
         String id_postOS = getIntent().getExtras().getString("post_id");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataComments.clear();
                if (snapshot.getChildren() != null){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Comment comment = ds.getValue(Comment.class);
                        if (comment.getId_post().equals(id_postOS)){
                            dataComments.add(comment);
                        }
                    }
                    commentAdapter = new CommentAdapter(DetailPostActivity.this, dataComments);
                    recyclerViewCmt.setAdapter(commentAdapter);
                    recyclerViewCmt.setLayoutManager(new LinearLayoutManager(DetailPostActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEvent() {
        scv_detail_post.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("ScrollView","scrollX_"+scrollX+"_scrollY_"+scrollY+"_oldScrollX_"+oldScrollX+"_oldScrollY_"+oldScrollY);
                if (scrollY > 8){

                }else {

                }
            }
        });
        id_post = getIntent().getExtras().getString("post_id");
        Toast.makeText(this, id_post + " ", Toast.LENGTH_SHORT).show();
        showDataPost(id_post);
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextContentComment.getText().length() > 0) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    // String id_comment, id_post, user_id, content_cmt, image_id, timestamp;
                    String user_id = currentUser.getUid();
                    String content = editTextContentComment.getText().toString();
                    String id_comment = System.currentTimeMillis()+"";
                    Comment comment = new Comment(id_comment, id_post, user_id, content, System.currentTimeMillis() + "");
                    commentsRef.child(id_comment).setValue(comment);
                    editTextContentComment.setText("");
                }
            }
        });
    }

    private void showDataPost(String id_post) {
        //Query query = PostsRef.orderByChild("id_post").equalTo(id_post);

        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Post post = ds.getValue(Post.class);
                        if (post.getId_post().equals(id_post)) {
                            mDataPost.add(post);
                        }
                    }
                    if (mDataPost != null) {
                        tv_content_post.setText(mDataPost.get(0).getContent_post());
                        tv_timestamp.setText(TimeFunc.getTimeAgo(Long.parseLong(mDataPost.get(0).getDay_create()), DetailPostActivity.this));
                        String strImg = mDataPost.get(0).getImage_id();
                        if (!strImg.equals("")) {
                            imageViewPost.setVisibility(View.VISIBLE);
                            try {
                                StorageReference islandRef = storageRef.child("images/posts/" + strImg);
                                final long ONE_MEGABYTE = 1024 * 1024;
                                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Data for "images/island.jpg" is returns, use this as needed
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        imageViewPost.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            } catch (Exception ex) {

                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_detail_post);
        imageButtonMore = findViewById(R.id.icon_more_detail_post);
        recyclerViewCmt = findViewById(R.id.rcy_cmt_detail_post);
        tv_content_post = findViewById(R.id.tv_content_detail_post);
        tv_nameUser = findViewById(R.id.tv_name_detail_post);
        tv_total_like = findViewById(R.id.tv_total_like_detail_post);
        tv_timestamp = findViewById(R.id.tv_timestamp_detail_post);
        imageViewUser = findViewById(R.id.img_user_detail_post);
        imageViewPost = findViewById(R.id.image_post_detail_post);
        imageButtonSend = findViewById(R.id.btn_send_comment_detail_post);
        editTextContentComment = findViewById(R.id.txt_content_comment_detail_post);
        scv_detail_post = findViewById(R.id.scv_detail_post);
    }
}
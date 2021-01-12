package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.tdc.edu.vn.heathcareapp.Functions.KeyBoardApp;
import com.tdc.edu.vn.heathcareapp.Functions.TimeFunc;
import com.tdc.edu.vn.heathcareapp.Functions.UpdateData;
import com.tdc.edu.vn.heathcareapp.Model.Comment;
import com.tdc.edu.vn.heathcareapp.Model.Like;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.Post;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore, imageButtonSend, imageButton_like;
    TextView tv_content_post, tv_nameUser, tv_total_like, tv_timestamp;
    LinearLayout lnl_post, lnl_like, lnl_editText_comment_detail_post;
    ImageView imageViewUser, imageViewPost;
    ArrayList<Comment> dataComments = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    CommentAdapter commentAdapter;
    RecyclerView recyclerViewCmt;
    EditText editTextContentComment;
    ScrollView scv_detail_post;
    String id_post = "";
    ArrayList<Post> mDataPost = new ArrayList<>();
    TimeFunc timeFunc;
    String user_post = "";
    String IMAGE_VIEW = "";
    String user_id_of_post = "";
    String statusLike = "0";
    UpdateData updateData;
    KeyBoardApp keyBoardApp = new KeyBoardApp();
    private BottomSheetDialog bottomSheetDialog;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference PostsRef = database.getReference("Posts");
    DatabaseReference commentsRef = database.getReference("Comments");
    DatabaseReference likesRef = database.getReference("Likes");
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference notificationRef = database.getReference("Notifications");
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
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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
                if (snapshot.getChildren() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Comment comment = ds.getValue(Comment.class);
                        if (comment.getId_post().equals(id_postOS)) {
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
        //showDataPost(id_post);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEvent() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user_id = currentUser.getUid();
        id_post = getIntent().getExtras().getString("post_id");
        user_id_of_post = getIntent().getExtras().getString("user_id_of_post");
        showDataPost(id_post);
        countLikesPost(id_post, user_id);
        imageButton_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID_LIKE = id_post + user_id;
                if (statusLike.equals("0")) {
                    String timestamp = System.currentTimeMillis() + "";
                    Like like = new Like(ID_LIKE, id_post, user_id, timestamp);
                    likesRef.child(ID_LIKE).setValue(like);
                    imageButton_like.setImageResource(R.drawable.ic_favorite_like);

                    if (!user_id_of_post.equals(user_id)) {
                        Notification notification = new Notification(timestamp, user_id_of_post, user_id, " Liked your post!", id_post, "like", timestamp, false);
                        notificationRef.child(timestamp).setValue(notification);
                    }
                } else {
                    imageButton_like.setImageResource(R.drawable.ic_favorite_unlike);
                    likesRef.child(ID_LIKE).removeValue();
                    statusLike = "0";
                }
            }
        });
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerViewCmt.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    lnl_editText_comment_detail_post.setVisibility(View.GONE);

                } else {
                    lnl_editText_comment_detail_post.setVisibility(View.VISIBLE);
                }
            }
        });
        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextContentComment.getText().length() > 0) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String user_id = currentUser.getUid();
                    String content = editTextContentComment.getText().toString();
                    String id_comment = System.currentTimeMillis() + "";
                    Comment comment = new Comment(id_comment, id_post, user_id, content, System.currentTimeMillis() + "");
                    commentsRef.child(id_comment).setValue(comment);
                    editTextContentComment.setText("");
                    if (!user_post.equals(user_id)) {
                        String timestamp = System.currentTimeMillis() + "";
                        Notification notification = new Notification(timestamp, user_post, user_id, " commented on your post. Check it out!", id_post, "comment", timestamp, false);
                        notificationRef.child(timestamp).setValue(notification);
                    }

                    keyBoardApp.closeKeyBoard(DetailPostActivity.this);
                }
            }
        });
        imageButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(DetailPostActivity.this, R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_sheet_detail_post, findViewById(R.id.bottom_sheet_detail_post));
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (!user_id_of_post.equals(currentUser.getUid())) {
                    sheetView.findViewById(R.id.lnl_delete_btn_sheet_detail_post).setVisibility(View.GONE);
                    sheetView.findViewById(R.id.lnl_update_btn_sheet_detail_post).setVisibility(View.GONE);

                } else {
                    sheetView.findViewById(R.id.lnl_report_btn_sheet_detail_post).setVisibility(View.GONE);
                    sheetView.findViewById(R.id.lnl_delete_btn_sheet_detail_post).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailPostActivity.this);
                            builder.setMessage("Do you want delete this post!");
                            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    PostsRef.child(id_post).removeValue();
                                    updateData.deleteAllCommentLikeOfPost(id_post);
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                                    bottomSheetDialog.dismiss();
                                    startActivity(new Intent(DetailPostActivity.this, NewFeedActivity.class));
                                }
                            });
                            builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    bottomSheetDialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    });

                    sheetView.findViewById(R.id.lnl_update_btn_sheet_detail_post).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailPostActivity.this, CreateContentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("post_id", id_post);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }


                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });
    }


    private void countLikesPost(String id_post, String user_id) {
        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count_like = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Like like = ds.getValue(Like.class);
                    if (like.getId_post().equals(id_post)) {
                        count_like += 1;
                        if (like.getId_user().equals(user_id)) {
                            imageButton_like.setImageResource(R.drawable.ic_favorite_like);
                            statusLike = "1";
                        } else {
                            imageButton_like.setImageResource(R.drawable.ic_favorite_unlike);
                            statusLike = "0";
                        }
                    }
                }
                tv_total_like.setText("" + count_like);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDataPost(String id_post) {
        //Query query = PostsRef.orderByChild("id_post").equalTo(id_post);
        this.registerForContextMenu(imageButtonMore);
        imageButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPostActivity.this);
            }
        });

        recyclerViewCmt.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 30) {
                    lnl_post.setVisibility(View.GONE);
                } else {
                    lnl_post.setVisibility(View.VISIBLE);
                }
                //lnl_like.setVisibility(View.GONE);

            }
        });
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
                        user_post = mDataPost.get(0).getUser_id();
                        tv_content_post.setText(mDataPost.get(0).getContent_post());
                        tv_timestamp.setText(TimeFunc.getTimeAgo(Long.parseLong(mDataPost.get(0).getDay_create()), DetailPostActivity.this));
                        String strImg = mDataPost.get(0).getImage_id();
                        IMAGE_VIEW = strImg;
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
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User user = ds.getValue(User.class);
                                    if (user.getUser_id().equals(mDataPost.get(0).getUser_id())) {
                                        dataUser.add(user);
                                    }
                                }
                                if (dataUser != null) {
                                    tv_nameUser.setText(dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name());
                                    String imgUser = dataUser.get(0).getImage_id();
                                    if (!imgUser.equals("")) {
                                        try {
                                            StorageReference islandRef = storageRef.child("images/user/" + imgUser);
                                            final long ONE_MEGABYTE = 1024 * 1024;
                                            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                @Override
                                                public void onSuccess(byte[] bytes) {
                                                    // Data for "images/island.jpg" is returns, use this as needed
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    imageViewUser.setImageBitmap(bitmap);
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
        lnl_like = findViewById(R.id.lnl_totalLike);
        lnl_post = findViewById(R.id.lnl_post);
        lnl_editText_comment_detail_post = findViewById(R.id.lnl_editText_comment_detail_post);
        imageButton_like = findViewById(R.id.icon_like_detail_post);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_post, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);

    }
}
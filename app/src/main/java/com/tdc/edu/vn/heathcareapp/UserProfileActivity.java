package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.PostAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.Post;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore;
    TextView tv_title_user_profile, tv_name_user, tv_follower, tv_following, tv_location, tv_since, tv_status_follow;
    CardView cv_follower, cv_editProfile;
    LinearLayout lnl_follow_user;
    ImageView imageViewAvatarUser;
    Button btn_chat_with_user;
    RecyclerView rcl_viewPost;
    String user_id = "";
    String option = "0";
    String strCheckFollow = "0";
    private ArrayList<User> mDataUser = new ArrayList<>();
    ArrayList<User> data = new ArrayList<>();
    ArrayList<Post> dataPost = new ArrayList<>();
    PostAdapter postAdapter;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference followRef = database.getReference("Follows");
    DatabaseReference postRef = database.getReference("Posts");
    DatabaseReference notificationRef = database.getReference("Notifications");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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
        user_id = getIntent().getExtras().getString("user_id");
        option = getIntent().getExtras().getString("option");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkFollow(user_id, currentUser.getUid());
        if (!user_id.equals("")) {
            showInfo(user_id);
        }
        lnl_follow_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strCheckFollow.equals("1") || user_id.equals(currentUser.getUid())) {
                    Intent intent = new Intent(UserProfileActivity.this, FollowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", user_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        if (strCheckFollow.equals("1") || currentUser.getUid().equals(user_id)){
            showDataPost(user_id);
        }
        cv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = currentUser.getUid();
                String id_follow = sender + user_id;
                if (strCheckFollow.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                    builder.setMessage("Do you want cancel request follow?");
                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            followRef.child(id_follow).removeValue();
                            tv_status_follow.setText(R.string.followers_connection_management_follow);
                        }
                    });
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    if (strCheckFollow.equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                        builder.setMessage("Do you want unfollow?");
                        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                followRef.child(id_follow).removeValue();
                                tv_status_follow.setText(R.string.followers_connection_management_follow);
                            }
                        });
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    } else {

                        Follow follow = new Follow(id_follow, sender, user_id, System.currentTimeMillis() + "", false, false);
                        followRef.child(id_follow).setValue(follow);
                        String timestamp = System.currentTimeMillis() + "";
                        Notification notification = new Notification(timestamp, user_id, sender, "Want to follow you", "", "follow", timestamp, false);
                        notificationRef.child(timestamp).setValue(notification);
                    }
                }

            }
        });

        if (option.equals("0") && !user_id.equals(currentUser.getUid())) {
            cv_follower.setVisibility(View.VISIBLE);
            btn_chat_with_user.setVisibility(View.VISIBLE);
            btn_chat_with_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UserProfileActivity.this, ConversationDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", user_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            cv_follower.setVisibility(View.GONE);
            cv_editProfile.setVisibility(View.VISIBLE);
        }
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cv_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), ActivityProfile.class);
                startActivity(inten);
                overridePendingTransition(0, 0);
                inten.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            }
        });

    }

    private void showDataPost(String user_id) {
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPost.clear();
                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Post post = ds.getValue(Post.class);
                        if (post.getUser_id().equals(user_id)) {
                            dataPost.add(post);
                        }
                    }
                    if (dataPost != null) {
                        postAdapter = new PostAdapter(UserProfileActivity.this, dataPost);
                        rcl_viewPost.setAdapter(postAdapter);
                        rcl_viewPost.setLayoutManager(new LinearLayoutManager(UserProfileActivity.this));
                    }
                }catch (Exception exception){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkFollow(String user_id, String myId) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count_following = 0;
                int count_follower = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Follow follow = ds.getValue(Follow.class);
                    if (follow.getSender().equals(myId) && follow.getReceiver().equals(user_id)) {
                        if (follow.getRequest_status()) {
                            strCheckFollow = "1";
                            tv_status_follow.setText(R.string.followers_connection_management_unfollow);
                        } else {
                            strCheckFollow = "2";
                            tv_status_follow.setText(R.string.followers_connection_management_requested);
                        }
                    }
                    if (follow.getSender().equals(user_id)) {
                        count_following += 1;
                    }
                    if (follow.getReceiver().equals(user_id)) {
                        count_follower += 1;
                    }
                }
                tv_follower.setText(count_follower + " followers");
                tv_following.setText(count_following + " followings");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showInfo(String user_id) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDataUser.clear();
                if (snapshot.hasChild(user_id)) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        data.add(user);
                        if (user.getUser_id().equals(user_id)) {
                            mDataUser.add(user);
                        }
                    }
                    if (mDataUser != null) {
                        String fullName = mDataUser.get(0).getFirst_name() + " " + mDataUser.get(0).getLast_name();
                        tv_name_user.setText(fullName);
                        tv_title_user_profile.setText(fullName);
                        tv_location.setText(mDataUser.get(0).getLocation());
                        tv_since.setText("User Since " + dataSince(mDataUser.get(0).getCreateAt()));
                        String imgUser = mDataUser.get(0).getImage_id();
                        try {
                            StorageReference islandRef = storageRef.child("images/user/" + imgUser);
                            final long ONE_MEGABYTE = 1024 * 1024;
                            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    // Data for "images/island.jpg" is returns, use this as needed
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageViewAvatarUser.setImageBitmap(bitmap);
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
                } else {
                    Toast.makeText(UserProfileActivity.this, "No FInd", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String dataSince(String timestamp) {
        String str = timestamp;
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String dateTimeMes = DateFormat.format("dd MMM yyyy", calendar).toString();
        return dateTimeMes;
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_user_profile);
        imageButtonMore = findViewById(R.id.icon_more_user_profile);
        tv_title_user_profile = findViewById(R.id.title_toolbar_user_profile);
        tv_name_user = findViewById(R.id.tv_name_user_profile);
        tv_follower = findViewById(R.id.tv_follower_user_profile);
        tv_following = findViewById(R.id.tv_following_user_profile);
        tv_location = findViewById(R.id.tv_location_user_profile);
        tv_since = findViewById(R.id.tv_since_user_profile);
        cv_follower = findViewById(R.id.cv_follow_user_profile);
        cv_editProfile = findViewById(R.id.cv_edit_user_profile);
        imageViewAvatarUser = findViewById(R.id.img_avatar_user_profile);
        btn_chat_with_user = findViewById(R.id.btn_chat_user_profile);
        tv_status_follow = findViewById(R.id.tv_status_follow_user_profile);
        lnl_follow_user = findViewById(R.id.lnl_follow_user);
        rcl_viewPost = findViewById(R.id.rcy_post_user_profile);
    }
}
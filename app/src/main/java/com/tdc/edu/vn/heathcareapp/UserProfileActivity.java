package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore;
    TextView tv_title_user_profile, tv_name_user, tv_follower, tv_following, tv_location, tv_since, tv_status_follow;
    CardView cv_follower, cv_editProfile;
    ImageView imageViewAvatarUser;
    Button btn_chat_with_user;
    String user_id = "";
    String option = "0";
    String strCheckFollow = "0";
    private ArrayList<User> mDataUser = new ArrayList<>();
    ArrayList<User> data = new ArrayList<>();
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference followRef = database.getReference("Follows");
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
        cv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = currentUser.getUid();
                String id_follow = sender + user_id;
                if (strCheckFollow.equals("2")) {
                } else {
                    if (strCheckFollow.equals("1")) {
                        followRef.child(id_follow).removeValue();
                        tv_status_follow.setText(R.string.followers_connection_management_following_title);
                    } else {

                        Follow follow = new Follow(id_follow, sender, user_id, System.currentTimeMillis() + "", false, false);
                        followRef.child(id_follow).setValue(follow);
                        // String id_notification, String user_id, String from_user_id, String content, String post_id, String url, String timestamp, Boolean seen)
                        String timestamp = System.currentTimeMillis()+"";
                        Notification notification = new Notification(timestamp, user_id, sender, "Want to follow you", "", "follow", timestamp, false);
                        notificationRef.child(timestamp).setValue(notification);
                    }
                }

            }
        });


        if (option.equals("0")) {
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
            cv_editProfile.setVisibility(View.VISIBLE);
        }
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void checkFollow(String user_id, String myId) {
        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                }
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
    }
}
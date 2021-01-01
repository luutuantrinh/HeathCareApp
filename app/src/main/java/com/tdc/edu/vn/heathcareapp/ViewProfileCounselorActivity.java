package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class ViewProfileCounselorActivity extends AppCompatActivity {
    ImageButton imgBtn_backSpace, imgBtn_More;
    ImageView imgAvatar_counselor;
    TextView tv_fullName, tv_introduce, tv_position, tv_title_fullName;
    RatingBar ratingBar;
    CardView cv_chat;
    RecyclerView rcv_review_counselor;
    LinearLayout lnl_info_counselor;
    // variable
    ArrayList<Counselor> dataCounselor = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    String ID_counselor = "";
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference counselorRef = database.getReference("Counselors");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_counselor);
        setControl();
        setEvent();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void setEvent() {
        ID_counselor = getIntent().getExtras().getString("ID_COUNSELOR");
        if (!ID_counselor.equals("")){
            counselorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataCounselor.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Counselor counselor = ds.getValue(Counselor.class);
                        if (counselor.getUser_id().equals(ID_counselor)){
                            dataCounselor.add(counselor);
                        }
                    }
                    if (dataCounselor != null){
                        try {
                            tv_introduce.setText(dataCounselor.get(0).getIntroduce());
                            tv_position.setText(dataCounselor.get(0).getPosition_counselor());
                            ratingBar.setRating(dataCounselor.get(0).getTotal_ratting());
                        }catch (Exception exception){

                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataUser.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(ID_counselor)){
                            dataUser.add(user);
                        }
                    }
                    if (dataUser != null){
                        try {
                            String fullName = dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name();
                            tv_fullName.setText(fullName);
                            tv_title_fullName.setText(fullName);
                            String imgUser = dataUser.get(0).getImage_id();
                            try {
                                StorageReference islandRef = storageRef.child("images/user/" + imgUser);
                                final long ONE_MEGABYTE = 1024 * 1024;
                                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Data for "images/island.jpg" is returns, use this as needed
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        imgAvatar_counselor.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            } catch (Exception ex) {

                            }
                        }catch (Exception exception){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        cv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileCounselorActivity.this, ConversationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", ID_counselor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imgBtn_backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setControl() {
        imgBtn_backSpace = findViewById(R.id.icon_backspace_view_profile_counselor);
        imgBtn_More = findViewById(R.id.icon_more_view_profile_counselor);
        tv_title_fullName = findViewById(R.id.tv_fullName_counselor_nav);
        lnl_info_counselor = findViewById(R.id.lnl_info_counselor_profile);
        imgAvatar_counselor = findViewById(R.id.img_avatar_counselor_profile);
        tv_fullName = findViewById(R.id.tv_name_counselor_profile);
        tv_introduce = findViewById(R.id.tv_introduce_counselor_profile);
        tv_position = findViewById(R.id.tv_position_counselor_profile);
        ratingBar = findViewById(R.id.ratting_counselor_profile);
        cv_chat = findViewById(R.id.cv_chat_counselor_profile);
        rcv_review_counselor = findViewById(R.id.rcy_review_counselor_profile);
    }
}
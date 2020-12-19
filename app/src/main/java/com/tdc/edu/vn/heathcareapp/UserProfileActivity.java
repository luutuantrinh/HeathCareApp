package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMore;
    TextView tv_title_user_profile, tv_name_user, tv_follower, tv_following, tv_location, tv_since;
    CardView cv_follower, cv_editProfile;
    ImageView imageViewAvatarUser;
    String user_id = "";
    String option = "0";
    private ArrayList<User> mDataUser = new ArrayList<>();
    ArrayList<User> data = new ArrayList<>();
    // Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setControl();
        setEvent();
    }

    private void setEvent() {
        user_id = getIntent().getExtras().getString("user_id");
        option = getIntent().getExtras().getString("option");
        if (!user_id.equals("")) {
            showInfo(user_id);


        }

        if (option.equals("0")) {
            cv_follower.setVisibility(View.VISIBLE);
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

    private void showInfo(String user_id) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDataUser.clear();
                if (snapshot.hasChild(user_id)){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        data.add(user);
                        if (user.getUser_id().equals(user_id)) {
                            mDataUser.add(user);
                        }
                    }
                    if (mDataUser != null){
                        String fullName= mDataUser.get(0).getFirst_name() + " " + mDataUser.get(0).getLast_name();
                        tv_name_user.setText(fullName);
                        tv_title_user_profile.setText(fullName);
                        tv_location.setText(mDataUser.get(0).getLocation());
                        tv_since.setText("User Since " + dataSince(mDataUser.get(0).getCreateAt()));
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
        cv_follower = findViewById(R.id.cv_following_user_profile);
        cv_editProfile = findViewById(R.id.cv_edit_user_profile);
        imageViewAvatarUser = findViewById(R.id.img_avatar_user_profile);
    }
}
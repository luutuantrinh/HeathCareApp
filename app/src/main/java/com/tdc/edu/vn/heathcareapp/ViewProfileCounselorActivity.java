package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.tdc.edu.vn.heathcareapp.Adapter.FeedbackAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.Model.Feedback;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class ViewProfileCounselorActivity extends AppCompatActivity {
    ImageButton imgBtn_backSpace, imgBtn_More;
    ImageView imgAvatar_counselor;
    TextView tv_fullName, tv_introduce, tv_position, tv_title_fullName;
    RatingBar ratingBar;
    CardView cv_chat;
    FeedbackAdapter feedbackAdapter;
    RecyclerView rcv_review_counselor;
    LinearLayout lnl_info_counselor, lnl_feedback;

    // variable
    ArrayList<Counselor> dataCounselor = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    ArrayList<Feedback> dataFeedback = new ArrayList<>();
    ArrayList<Feedback> dataFeedback2 = new ArrayList<>();
    String ID_counselor = "";

    // Form feedback
    EditText txt_comment_feedback_form;
    Button btn_cancel_form, btn_submit_form;
    RatingBar ratingBar_form;
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference counselorRef = database.getReference("Counselors");
    DatabaseReference feedbackRef = database.getReference("Feedback");
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myId = currentUser.getUid();
        averageRating(ID_counselor);
        if (!ID_counselor.equals("")) {
            counselorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataCounselor.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Counselor counselor = ds.getValue(Counselor.class);
                        if (counselor.getUser_id().equals(ID_counselor)) {
                            dataCounselor.add(counselor);
                        }
                    }
                    if (dataCounselor != null) {
                        try {
                            tv_introduce.setText(dataCounselor.get(0).getIntroduce());
                            tv_position.setText(dataCounselor.get(0).getPosition_counselor());

                        } catch (Exception exception) {

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
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(ID_counselor)) {
                            dataUser.add(user);
                        }
                    }
                    if (dataUser != null) {
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
                        } catch (Exception exception) {

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        getDataFeedBackAndCheck(myId, ID_counselor);
        lnl_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogFeedback(myId, ID_counselor);
            }
        });
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

    private void averageRating(String id_counselor) {
        feedbackRef.addValueEventListener(new ValueEventListener() {
            float totalRatting = 0;
            int numRatting = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Feedback feedback = ds.getValue(Feedback.class);
                    if (feedback.getCounselor_id().equals(id_counselor)) {
                        totalRatting += feedback.getRatting();
                        numRatting += 1;
                        dataFeedback2.add(feedback);
                    }
                }
                ratingBar.setRating(totalRatting / numRatting);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataFeedBackAndCheck(String myId, String id_counselor) {
        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Feedback feedback = ds.getValue(Feedback.class);
                    if (feedback.getUser_id().equals(myId) && feedback.getCounselor_id().equals(ID_counselor)) {
                        lnl_feedback.setVisibility(View.GONE);
                    }
                    if (feedback.getCounselor_id().equals(ID_counselor)) {
                        dataFeedback.add(feedback);
                    }
                }
                if (dataFeedback != null) {
                    feedbackAdapter = new FeedbackAdapter(ViewProfileCounselorActivity.this, dataFeedback);
                    rcv_review_counselor.setAdapter(feedbackAdapter);
                    rcv_review_counselor.setLayoutManager(new LinearLayoutManager(ViewProfileCounselorActivity.this));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialogFeedback(String myID, String id_counselor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfileCounselorActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_feedback, null);
        btn_cancel_form = view.findViewById(R.id.btn_cancel_feedback_form);
        btn_submit_form = view.findViewById(R.id.btn_submit_feedback_form);
        ratingBar_form = view.findViewById(R.id.ratting_bar_feedback_form);
        txt_comment_feedback_form = view.findViewById(R.id.txt_feedback_form);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();
        btn_cancel_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_submit_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID_FEEDBACK = myID + id_counselor;
                String COMMENT = txt_comment_feedback_form.getText().toString();
                String timestamp = System.currentTimeMillis() + "";
                Float RATTING = ratingBar_form.getRating();
                Feedback feedback = new Feedback(ID_FEEDBACK, myID, id_counselor, COMMENT, timestamp, RATTING);
                feedbackRef.child(ID_FEEDBACK).setValue(feedback);
                Toast.makeText(getApplicationContext(), "Feedback success!!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
        lnl_feedback = findViewById(R.id.lnl_feedback);
    }
}
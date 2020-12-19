package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.MessageAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversationDetailActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonSendMessage, imageButtonImage;
    EditText txt_message;
    RecyclerView rcy_ListMessage;
    TextView tv_userName;
    String user_id = "";

    // Firebase sender, receiver
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    DatabaseReference userRef = database.getReference("Users");

    String id_sender = "";
    String id_receiver = "";
    //
    ArrayList<User> mUsers = new ArrayList<>();
    ArrayList<Message> dataMessage = new ArrayList<>();
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_detail);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        SetEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            id_sender = currentUser.getUid();
        }
    }

    private void SetEvent() {
        id_receiver = getIntent().getExtras().getString("user_id");
        showInfo(id_receiver);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcy_ListMessage.setHasFixedSize(true);
        rcy_ListMessage.setLayoutManager(linearLayoutManager);
        try {
            readMessage();
            Toast.makeText(this, dataMessage.size() + "", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {

        }

        imageButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_message.getText().length() > 0) {
                    String message = txt_message.getText().toString().trim();
                    sendMessage(message);
                }
            }
        });
        txt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    imageButtonSendMessage.setBackgroundResource(R.drawable.ic_send);
                } else {
                    imageButtonSendMessage.setBackgroundResource(R.drawable.ic_send_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Error 404", Toast.LENGTH_SHORT).show();
            }
        });
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showInfo(String receiver) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                if (snapshot.hasChild(receiver)) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(receiver)) {
                            mUsers.add(user);
                        }
                    }
                    tv_userName.setText(mUsers.get(0).getFirst_name() + " " + mUsers.get(0).getLast_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessage() {
        DatabaseReference dbMessageRef = database.getReference("Messages");
        dbMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataMessage.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message mes = ds.getValue(Message.class);

                    if (mes.getReceiver().equals(id_receiver) && mes.getSender().equals(id_sender) || mes.getReceiver().equals(id_sender) && mes.getSender().equals(id_receiver)) {
                        dataMessage.add(mes);
                    }
                }
                messageAdapter = new MessageAdapter(ConversationDetailActivity.this, dataMessage, "");
                messageAdapter.notifyDataSetChanged();
                rcy_ListMessage.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference mesRef = ref.child("Messages");
        Message mes1 = new Message(id_sender, id_receiver, message, "1", timestamp, false);
        mesRef.push().setValue(mes1);
        txt_message.setText("");
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_conversation_detail);
        imageButtonImage = findViewById(R.id.btn_image_message_conversation_detail);
        imageButtonSendMessage = findViewById(R.id.btn_send_message_conversation_detail);
        txt_message = findViewById(R.id.editText_send_message_conversation_detail);
        rcy_ListMessage = findViewById(R.id.rcy_conversation_detail);
        tv_userName = findViewById(R.id.title_toolbar_conversation_detail);
    }
}
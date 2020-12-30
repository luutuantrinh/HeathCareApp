package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.ChatListAdapter;
import com.tdc.edu.vn.heathcareapp.Model.ChatList;

import java.util.ArrayList;

public class ChatUserToUserActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonCreateNewMessage;
    EditText txt_search_message;
    RecyclerView recyclerViewChatList;
    ArrayList<ChatList> dataChatList = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    // firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference chatListRef = database.getReference("ChatList");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_to_user);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void setEvent() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user_id = currentUser.getUid();
        showListChat(user_id);
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageButtonCreateNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void showListChat(String user_id) {
        DatabaseReference listU = chatListRef.child(user_id);
        listU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataChatList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatList chatList = ds.getValue(ChatList.class);
                    dataChatList.add(chatList);
                }
                if (dataChatList != null) {
                    try {
                        chatListAdapter = new ChatListAdapter(ChatUserToUserActivity.this, dataChatList);
                        recyclerViewChatList.setAdapter(chatListAdapter);
                        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(ChatUserToUserActivity.this));
                    } catch (Exception ex) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_chat_list_user_to_user);
        imageButtonCreateNewMessage = findViewById(R.id.icon_new_message_user_to_user);
        txt_search_message = findViewById(R.id.txt_search_message);
        recyclerViewChatList = findViewById(R.id.rcy_chat_list_user_to_user);
    }
}
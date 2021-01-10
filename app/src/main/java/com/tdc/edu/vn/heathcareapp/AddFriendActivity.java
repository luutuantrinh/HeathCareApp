package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.MessageAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.SearchFriendAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    RecyclerView recyclerView;
    EditText txt_key;

    //
    ArrayList<User> mDataUser = new ArrayList<>();
    SearchFriendAdapter searchFriendAdapter;
    // firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    try {
                        searchUser(charSequence.toString());
                    } catch (Exception ex) {

                    }

                } else {
                    mDataUser.clear();
                    searchFriendAdapter = new SearchFriendAdapter(AddFriendActivity.this, mDataUser);
                    searchFriendAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(searchFriendAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchUser(String key) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDataUser.clear();
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        String fullName = user.getFirst_name().toString().toLowerCase() + " " + user.getLast_name().toString().toLowerCase();
                        if (user.getFirst_name().toLowerCase().contains(key.toLowerCase())
                                || user.getLast_name().toLowerCase().contains(key.toLowerCase())
                                || user.getEmail().toLowerCase().contains(key.toLowerCase()) || fullName.contains(key.toLowerCase())) {
                            mDataUser.add(user);
                        }
                    }
                    searchFriendAdapter = new SearchFriendAdapter(AddFriendActivity.this, mDataUser);
                    searchFriendAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(searchFriendAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_add_friends);
        txt_key = findViewById(R.id.txt_search_friend);
        recyclerView = findViewById(R.id.rcy_search_friend);
    }
}
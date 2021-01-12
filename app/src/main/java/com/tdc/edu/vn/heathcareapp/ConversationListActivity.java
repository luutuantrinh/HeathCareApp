package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Adapter.CounselorAdapter;
import com.tdc.edu.vn.heathcareapp.Adapter.SearchFriendAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.ArrayList;

public class ConversationListActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMoreOption;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerViewListChat;
    Spinner spinnerCategory;
    EditText txt_search;
    ArrayList<Counselor> dataCounselor = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    CounselorAdapter counselorAdapter;
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference CounselorRef = database.getReference("Counselors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        setControl();
        setEvent();
    }

    private void setEvent() {
        showData();
        ArrayList<String> Category = new ArrayList<>();
        Category.add("All");
        Category.add("Health");
        Category.add("Fitness");
        Category.add("Nutrition");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tutorialsName = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + tutorialsName + "STT: " + i, Toast.LENGTH_LONG).show();
                String cate = String.valueOf(i - 1);
                if (i == 0) {
                    showData();
                } else {
                    getDataByCategory(cate);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        counselorAdapter = new CounselorAdapter(ConversationListActivity.this, dataCounselor);
        recyclerViewListChat.setAdapter(counselorAdapter);
        recyclerViewListChat.setLayoutManager(new LinearLayoutManager(ConversationListActivity.this));

        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    try {
                        searchUser(charSequence.toString());
                        spinnerCategory.setSelection(0);
                    } catch (Exception exception) {

                    }
                } else {
                    showData();
                    spinnerCategory.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bottomNavigationView.setSelectedItemId(R.id.Nutrition);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        Intent intent = new Intent(getApplicationContext(), NewFeedActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        return true;
                    case R.id.Nutrition:
                        return true;
                    case R.id.Profile:
                        Intent inten = new Intent(getApplicationContext(), ActivityProfile.class);
                        startActivity(inten);
                        overridePendingTransition(0, 0);
                        inten.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        return true;

                }
                return true;
            }
        });
    }

    private void searchUser(String key) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUser.clear();
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        String fullName = user.getFirst_name().toString().toLowerCase() + " " + user.getLast_name().toString().toLowerCase();
                        if (user.getFirst_name().toLowerCase().contains(key.toLowerCase())
                                || user.getLast_name().toLowerCase().contains(key.toLowerCase())
                                || user.getEmail().toLowerCase().contains(key.toLowerCase())
                                || fullName.contains(key.toLowerCase())
                                || user.getPhone().contains(key.toLowerCase())) {
                            //dataUser.add(user);
                            CounselorRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    dataCounselor.clear();
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        Counselor counselor = ds.getValue(Counselor.class);
                                        if (counselor.getUser_id().equals(user.getUser_id())) {
                                            if (counselor.getPosition_counselor().toLowerCase().contains(key.toLowerCase())
                                                    || !counselor.getPosition_counselor().contains(key.toLowerCase())) {
                                                dataCounselor.add(counselor);
                                            }

                                        }
                                    }
                                    if (dataCounselor != null) {
                                        counselorAdapter = new CounselorAdapter(ConversationListActivity.this, dataCounselor);
                                        recyclerViewListChat.setAdapter(counselorAdapter);
                                        recyclerViewListChat.setLayoutManager(new LinearLayoutManager(ConversationListActivity.this));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showData() {
        CounselorRef.orderByChild("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataCounselor.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Counselor counselor = ds.getValue(Counselor.class);
                    dataCounselor.add(counselor);
                }
                if (dataCounselor != null) {
                    counselorAdapter = new CounselorAdapter(ConversationListActivity.this, dataCounselor);
                    recyclerViewListChat.setAdapter(counselorAdapter);
                    recyclerViewListChat.setLayoutManager(new LinearLayoutManager(ConversationListActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataByCategory(String category) {
        CounselorRef.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataCounselor.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Counselor counselor = ds.getValue(Counselor.class);
                    dataCounselor.add(counselor);
                }
                if (dataCounselor != null) {
                    counselorAdapter = new CounselorAdapter(ConversationListActivity.this, dataCounselor);
                    recyclerViewListChat.setAdapter(counselorAdapter);
                    recyclerViewListChat.setLayoutManager(new LinearLayoutManager(ConversationListActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_chat_list);
        imageButtonMoreOption = findViewById(R.id.icon_more_chat_list);
        recyclerViewListChat = findViewById(R.id.rcy_chat_list);
        spinnerCategory = findViewById(R.id.sp_consultants);
        txt_search = findViewById(R.id.txt_search_counselor);
        bottomNavigationView = findViewById(R.id.BottomNavView);
    }
}
package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tdc.edu.vn.heathcareapp.Adapter.CounselorAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;

import java.util.ArrayList;

public class ConversationListActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace, imageButtonMoreOption;
    RecyclerView recyclerViewListChat;
    CounselorAdapter counselorAdapter;
    ArrayList<Counselor> dataCounselor = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dataCounselor.add(new Counselor(1, "Khoi nguyen", "Master Android ne!"));
        dataCounselor.add(new Counselor(1, "Nghia Le", "Master Github!"));
        dataCounselor.add(new Counselor(1, "Ngoc Duyen", "Master Android ne!"));
        dataCounselor.add(new Counselor(1, "Tuan Trinh", "Master Android ne!"));
        dataCounselor.add(new Counselor(1, "Doan Van Phuong", "Master React native ne!"));

        counselorAdapter = new CounselorAdapter(ConversationListActivity.this, dataCounselor);
        recyclerViewListChat.setAdapter(counselorAdapter);
        recyclerViewListChat.setLayoutManager(new LinearLayoutManager(ConversationListActivity.this));

        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setControl() {
        imageButtonBackSpace = findViewById(R.id.icon_backspace_chat_list);
        imageButtonMoreOption = findViewById(R.id.icon_more_chat_list);
        recyclerViewListChat = findViewById(R.id.rcy_chat_list);
    }
}
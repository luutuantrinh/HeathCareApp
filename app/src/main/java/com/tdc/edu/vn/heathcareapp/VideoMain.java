package com.tdc.edu.vn.heathcareapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Adapter.VideoAdapter;
import com.tdc.edu.vn.heathcareapp.Model.Video;

import java.util.ArrayList;

public class VideoMain  extends AppCompatActivity {


    RecyclerView rv_video;
    ArrayList<Video> dataVideos = new ArrayList<>();
    private StorageReference mStorageRef;
    VideoAdapter videoAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Workout/Trending/ImageTrending/1/BoxHit/Video");
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        rv_video = findViewById(R.id.rv_videotrending);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Video video = ds.getValue(Video.class);
                        dataVideos.add(video);
                        Toast.makeText(VideoMain.this, dataVideos.toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (dataVideos != null){
                        videoAdapter = new VideoAdapter(VideoMain.this, dataVideos);
                        rv_video.setAdapter(videoAdapter);
                        rv_video.setLayoutManager(new LinearLayoutManager(VideoMain.this));
                    }else {

                        Toast.makeText(VideoMain.this, "asdasdasdasdasd", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception exception){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}

package com.tdc.edu.vn.heathcareapp.Functions;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdc.edu.vn.heathcareapp.Model.Notification;

public class UpdateData {
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsRef = database.getReference("Posts");
    DatabaseReference followRef = database.getReference("Follows");
    DatabaseReference notificationRef = database.getReference("Notifications");

    public void updateSeenNotification(String user_id) {
        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Notification notification = ds.getValue(Notification.class);
                    if (notification.getUser_id().equals(user_id) && notification.getSeen() == false){
                        String ID_NOTIFICATION = notification.getId_notification();
                        String USER_ID = notification.getUser_id();
                        String FROM_USER = notification.getFrom_user_id();
                        String CONTENT = notification.getContent();
                        String ID_POST = notification.getPost_id();
                        String URL = notification.getUrl();
                        String TIMESTAMP = notification.getTimestamp();
                        Boolean SEEN = notification.getSeen();
                        //String id_notification, String user_id, String from_user_id, String content, String post_id, String url, String timestamp, Boolean seen
                        notificationRef.child(ID_NOTIFICATION).setValue(new Notification(ID_NOTIFICATION, USER_ID, FROM_USER, CONTENT, ID_POST, URL, TIMESTAMP, true));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }public void updateSeenNotification2(String user_id, String id_notification) {
        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Notification notification = ds.getValue(Notification.class);
                    if (notification.getUser_id().equals(user_id) && notification.getSeen() == false && notification.getId_notification().equals(id_notification)){
                        String ID_NOTIFICATION = notification.getId_notification();
                        String USER_ID = notification.getUser_id();
                        String FROM_USER = notification.getFrom_user_id();
                        String CONTENT = notification.getContent();
                        String ID_POST = notification.getPost_id();
                        String URL = notification.getUrl();
                        String TIMESTAMP = notification.getTimestamp();
                        Boolean SEEN = notification.getSeen();
                        //String id_notification, String user_id, String from_user_id, String content, String post_id, String url, String timestamp, Boolean seen
                        notificationRef.child(ID_NOTIFICATION).setValue(new Notification(ID_NOTIFICATION, USER_ID, FROM_USER, CONTENT, ID_POST, URL, TIMESTAMP, true));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

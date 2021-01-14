package com.tdc.edu.vn.heathcareapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.Model.Follow;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.UserProfileActivity;

import java.util.ArrayList;

public class RequestFriendAdapter extends RecyclerView.Adapter<RequestFriendAdapter.RequestFriendViewHolder> {
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<User> dataUser = new ArrayList<>();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference followRef = database.getReference("Follows");
    DatabaseReference notificationRef = database.getReference("Notifications");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Context context;
    ArrayList<Follow> dataFollow = new ArrayList<>();

    public RequestFriendAdapter(Context context, ArrayList<Follow> dataFollow) {
        this.context = context;
        this.dataFollow = dataFollow;
    }

    @NonNull
    @Override
    public RequestFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_request, parent, false);
        return new RequestFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestFriendViewHolder holder, int position) {
        Follow follow = dataFollow.get(position);
        if (follow == null) {
            return;
        }
        String id_follow = follow.getId_follow();
        String id_sender = follow.getSender();
        String user_id = firebaseUser.getUid();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUser.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.getUser_id().equals(id_sender)) {
                        dataUser.add(user);
                    }
                }

                if (dataUser != null) {
                    try {
                        String fullNameUser = dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name();
                        holder.tv_user_name.setText(fullNameUser);

                        String imgUser = dataUser.get(0).getImage_id();
                        if(!imgUser.equals("")){
                            try {
                                StorageReference islandRef = storageRef.child("images/user/" + imgUser);
                                final long ONE_MEGABYTE = 1024 * 1024;
                                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Data for "images/island.jpg" is returns, use this as needed
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        holder.imageViewUser.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            } catch (Exception ex) {

                            }
                        }
                    } catch (Exception ex) {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followRef.child(id_follow).removeValue();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", id_sender);
                bundle.putString("option", "0");
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow follow = new Follow(id_follow, id_sender, user_id, System.currentTimeMillis() + "", true, true);
                followRef.child(id_follow).setValue(follow);
                String timestamp = System.currentTimeMillis() + "";
                Notification notification = new Notification(timestamp, id_sender, user_id, " has accepted your request to follow up", "", "accept", timestamp, false);
                notificationRef.child(timestamp).setValue(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataFollow != null) {
            return dataFollow.size();
        }
        return 0;
    }

    public class RequestFriendViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUser;
        TextView tv_user_name;
        Button btn_accept, btn_delete;

        public RequestFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUser = itemView.findViewById(R.id.img_avatar_item_request_follow);
            tv_user_name = itemView.findViewById(R.id.txt_lastName_item_request_follow);
            btn_accept = itemView.findViewById(R.id.btn_accept_request_item_request_friend);
            btn_delete = itemView.findViewById(R.id.btn_delete_request_item_request_friend);
        }
    }
}

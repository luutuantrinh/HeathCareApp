package com.tdc.edu.vn.heathcareapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tdc.edu.vn.heathcareapp.DetailPostActivity;
import com.tdc.edu.vn.heathcareapp.Functions.TimeFunc;
import com.tdc.edu.vn.heathcareapp.Functions.UpdateData;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.RequestFriendActivity;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    Context context;
    ArrayList<Notification> dataNotification = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    UpdateData updateData = new UpdateData();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference UserRef = database.getReference("Users");
    public NotificationAdapter(Context context, ArrayList<Notification> dataNotification) {
        this.context = context;
        this.dataNotification = dataNotification;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = dataNotification.get(position);
        String id_user = firebaseUser.getUid();
        if (notification == null) {
            return;
        }
        if (notification.getSeen() == false){
            holder.linearLayoutBackground.setBackgroundResource(R.color.color_background_activity);
        }
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUser.clear();
                if (snapshot.hasChild(notification.getFrom_user_id())) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(notification.getFrom_user_id())) {
                            dataUser.add(user);
                        }
                    }
                    try {
                        if (dataUser != null) {
                            String content = dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name() + notification.getContent();
                            holder.tv_content_notification.setText(content);
                            String strImgUser = dataUser.get(0).getImage_id();
                            if (!strImgUser.equals("")) {
                                try {
                                    StorageReference islandRef = storageRef.child("images/user/" + strImgUser);
                                    final long ONE_MEGABYTE = 1024 * 1024;
                                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            // Data for "images/island.jpg" is returns, use this as needed
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            holder.imageViewAvatar.setImageBitmap(bitmap);
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
                        }
                    } catch (Exception Ex) {

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String url_of_activity = notification.getUrl();
        Long timestamp = Long.parseLong(notification.getTimestamp());
        holder.tv_timestamp.setText(TimeFunc.getTimeAgo(timestamp, context));
        if (url_of_activity.equals("follow")) {
            holder.imageViewNotification.setImageResource(R.drawable.ic_people);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RequestFriendActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("post_id", posts.getId_post());
//                    intent.putExtras(bundle);
                    ((Activity) context).startActivity(intent);
                    updateData.updateSeenNotification2(id_user, notification.getId_notification());
                }
            });
        } else {
            if (url_of_activity.equals("comment")) {
                holder.imageViewNotification.setImageResource(R.drawable.ic_comment);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailPostActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("post_id", notification.getPost_id());
                        intent.putExtras(bundle);
                        ((Activity) context).startActivity(intent);
                        updateData.updateSeenNotification2(id_user, notification.getId_notification());
                    }
                });



            } else {
                if (url_of_activity.equals("like")) {
                    holder.imageViewNotification.setImageResource(R.drawable.ic_baseline_favorite_24);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, DetailPostActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("post_id", notification.getPost_id());
                            intent.putExtras(bundle);
                            ((Activity) context).startActivity(intent);
                            updateData.updateSeenNotification2(id_user, notification.getId_notification());
                        }
                    });

                } else {
                    holder.imageViewNotification.setImageResource(R.drawable.ic_notifications);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dataNotification != null){
            return  dataNotification.size();
        }
        return 0;
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar, imageViewNotification;
        TextView tv_content_notification, tv_timestamp;
        LinearLayout linearLayoutBackground;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.image_item_notification);
            imageViewNotification = itemView.findViewById(R.id.image_notification_item_notification);
            tv_content_notification = itemView.findViewById(R.id.content_item_notification);
            tv_timestamp = itemView.findViewById(R.id.timestamp_item_notification);
            linearLayoutBackground = itemView.findViewById(R.id.lnl_background_item_notification);
        }
    }
}

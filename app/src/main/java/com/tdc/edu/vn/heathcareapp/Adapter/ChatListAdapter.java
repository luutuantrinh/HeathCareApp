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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.tdc.edu.vn.heathcareapp.ConversationDetailActivity;
import com.tdc.edu.vn.heathcareapp.Model.ChatList;
import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.UserProfileActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    Context context;
    ArrayList<ChatList> dataChatList = new ArrayList<>();
    ArrayList<Message> dataMessage = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference messageRef = database.getReference("Messages");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public ChatListAdapter(Context context, ArrayList<ChatList> dataChatList) {
        this.context = context;
        this.dataChatList = dataChatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatList chatList = dataChatList.get(position);
        if (chatList == null) {
            return;
        }
        String user_id = chatList.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cv_message_not_seen.setVisibility(View.GONE);
                Intent intent = new Intent(context, ConversationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", user_id);
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        String myUserId = firebaseUser.getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUser.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.getUser_id().equals(user_id)) {
                        dataUser.add(user);
                    }
                }
                if (dataUser != null) {
                    try {
                        holder.tv_userName.setText(dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name());
                        String imgUser = dataUser.get(0).getImage_id();
                        if (!imgUser.equals("")) {
                            try {
                                StorageReference islandRef = storageRef.child("images/user/" + imgUser);
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
                    } catch (Exception exception) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        messageRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataMessage.clear();
                int count_message_seen = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    if (message.getReceiver().equals(myUserId) && message.getSender().equals(user_id)
                            || message.getReceiver().equals(user_id) && message.getSender().equals(myUserId)) {
                        dataMessage.add(message);
                    }

                    if (message.getReceiver().equals(myUserId) && message.getSender().equals(user_id) && message.getSeen() == false) {
                        count_message_seen += 1;
                    }
                }
                if (dataMessage != null) {
                    try {
                        Collections.reverse(dataMessage);
                        if (dataMessage.get(0).getReceiver().equals(user_id)) {
                            holder.tv_description_message.setText("You: " + dataMessage.get(0).getMessage());
                        } else {
                            holder.tv_description_message.setText(dataMessage.get(0).getMessage());
                            if (count_message_seen < 10 && count_message_seen > 0) {
                                holder.cv_message_not_seen.setVisibility(View.VISIBLE);
                                holder.tv_total_not_seen.setText(count_message_seen + "");
                                holder.rll_item_chat.setBackgroundResource(R.color.color_background_activity);
                            } else {
                                if (count_message_seen > 0) {
                                    holder.cv_message_not_seen.setVisibility(View.VISIBLE);
                                    holder.tv_total_not_seen.setText("9+");
                                    holder.rll_item_chat.setBackgroundResource(R.color.color_background_activity);
                                } else {
                                    holder.cv_message_not_seen.setVisibility(View.GONE);
                                    holder.rll_item_chat.setBackgroundResource(R.color.white);
                                }
                            }
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

    @Override
    public int getItemCount() {
        if (dataChatList != null) {
            return dataChatList.size();
        }
        return 0;
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView tv_userName, tv_description_message, tv_total_not_seen;
        CardView cv_message_not_seen;
        RelativeLayout rll_item_chat;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_total_not_seen = itemView.findViewById(R.id.tv_total_message_not_seen_item_chat);
            cv_message_not_seen = itemView.findViewById(R.id.cv_total_message_not_seen_item_chat);
            imageViewAvatar = itemView.findViewById(R.id.img_user_item_chat);
            tv_userName = itemView.findViewById(R.id.tv_username_item_chat);
            tv_description_message = itemView.findViewById(R.id.tv_description_item_chat);
            rll_item_chat = itemView.findViewById(R.id.rll_item_chat);
        }
    }
}

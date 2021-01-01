package com.tdc.edu.vn.heathcareapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.DetailPostActivity;
import com.tdc.edu.vn.heathcareapp.Functions.TimeFunc;
import com.tdc.edu.vn.heathcareapp.Model.Comment;
import com.tdc.edu.vn.heathcareapp.Model.Like;
import com.tdc.edu.vn.heathcareapp.Model.Notification;
import com.tdc.edu.vn.heathcareapp.Model.Post;
import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.UserProfileActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context context;
    ArrayList<Post> dataPosts = new ArrayList<>();
    ArrayList<Like> dataLikes = new ArrayList<>();
    ArrayList<User> dataUsers = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseUser firebaseUser;
    int countLike = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference LikesRef = database.getReference("Likes");
    DatabaseReference UserRef = database.getReference("Users");
    DatabaseReference notificationRef = database.getReference("Notifications");
    DatabaseReference commentRef = database.getReference("Comments");
    private String strImg = "";
    private String strImgUser = "";
    String statusLike = "0";

    public PostAdapter(Context context, ArrayList<Post> dataPosts) {
        this.context = context;
        this.dataPosts = dataPosts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post posts = dataPosts.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = firebaseUser.getUid();
        String user_id_of_post = posts.getUser_id();
        String post_id = posts.getId_post();
        if (posts.getContent_post().equals("")) {
            holder.tv_content_post.setVisibility(View.GONE);
        }
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count_comment = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Comment comment = ds.getValue(Comment.class);
                    if (comment.getId_post().equals(post_id)) {
                        count_comment += 1;
                    }
                }
                if (count_comment > 0) {
                    holder.tv_total_comment.setText(count_comment + " Comments");
                    holder.lnl_total_comment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (posts == null) {
            return;
        }
        holder.tv_user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", posts.getUser_id());
                bundle.putString("option", "0");
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUsers.clear();
                if (snapshot.hasChild(posts.getUser_id())) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(posts.getUser_id())) {
                            dataUsers.add(user);
                        }
                    }
                    try {
                        if (dataUsers != null) {
                            holder.tv_user_post.setText(dataUsers.get(0).getFirst_name() + " " + dataUsers.get(0).getLast_name());
                            strImgUser = dataUsers.get(0).getImage_id();
                            if (!strImgUser.equals("")) {
                                try {
                                    Picasso.get().load(strImg).into(holder.imageViewUser);
                                } catch (Exception e) {
                                    try {
                                        StorageReference islandRef = storageRef.child("images/user/" + strImgUser);
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
        strImg = posts.getImage_id();
        try {
            holder.tv_content_post.setText(posts.getContent_post());
        } catch (Exception exception) {
        }
        String timestamp = posts.getDay_create();
        holder.tv_create_at.setText(TimeFunc.getTimeAgo(Long.parseLong(timestamp), context));
        String totalLike = String.valueOf(posts.getTotal_like());
        holder.tv_total_like.setText(totalLike);
        if (!strImg.equals("")) {
            holder.imageViewPost.setVisibility(View.VISIBLE);
            try {
                StorageReference islandRef = storageRef.child("images/posts/" + strImg);
                final long ONE_MEGABYTE = 1024 * 1024;
                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imageViewPost.setImageBitmap(bitmap);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("post_id", posts.getId_post());
                bundle.putString("user_id_of_post", user_id_of_post);
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        statusLike = "0";
        LikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countLike = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Like like = ds.getValue(Like.class);
                    if (like.getId_post().equals(posts.getId_post())) {
                        countLike += 1;
                        if (like.getId_user().equals(user_id)) {
                            holder.icon_like.setImageResource(R.drawable.ic_favorite_like);
                            statusLike = "1";
                        } else {
                            holder.icon_like.setImageResource(R.drawable.ic_favorite_unlike);
                            statusLike = "0";
                        }
                    }

                }
                holder.tv_total_like.setText(countLike + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.icon_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID_LIKE = post_id + user_id;
                if (statusLike.equals("0")) {
                    //String id_like, String id_post, String id_user, String timestamp
                    String timestamp = System.currentTimeMillis() + "";
                    Like like = new Like(ID_LIKE, post_id, user_id, timestamp);
                    LikesRef.child(ID_LIKE).setValue(like);
                    holder.icon_like.setImageResource(R.drawable.ic_favorite_like);

                    if (!user_id_of_post.equals(user_id)) {
                        Notification notification = new Notification(timestamp, user_id_of_post, user_id, " Liked your post!", post_id, "like", timestamp, false);
                        notificationRef.child(timestamp).setValue(notification);
                    }
                } else {
                    holder.icon_like.setImageResource(R.drawable.ic_favorite_unlike);
                    LikesRef.child(ID_LIKE).removeValue();
                    statusLike = "0";
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataPosts != null) {
            return dataPosts.size();
        }
        return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content_post, tv_create_at, tv_total_like, tv_user_post;
        ImageButton icon_like;
        ImageView imageViewPost, imageViewUser;
        TextView tv_total_comment;
        LinearLayout lnl_total_comment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content_post = itemView.findViewById(R.id.tv_content_item_post);
            tv_create_at = itemView.findViewById(R.id.tv_created_At_item_post);
            tv_total_like = itemView.findViewById(R.id.tv_total_like_item_post);
            icon_like = itemView.findViewById(R.id.icon_like_item_post);
            imageViewPost = itemView.findViewById(R.id.img_post_item_post);
            tv_total_comment = itemView.findViewById(R.id.total_comment_item_post);
            imageViewUser = itemView.findViewById(R.id.img_user_item_post);
            tv_user_post = itemView.findViewById(R.id.tv_user_item_post);
            lnl_total_comment = itemView.findViewById(R.id.lnl_total_comment_item_post);
        }
    }
}

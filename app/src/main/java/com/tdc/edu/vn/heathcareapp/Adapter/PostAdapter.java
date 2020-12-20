package com.tdc.edu.vn.heathcareapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.DetailPostActivity;
import com.tdc.edu.vn.heathcareapp.Model.Like;
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
    private String strImg = "";
    private String strImgUser = "";

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

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
        if (posts == null) {
            return;
        }

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
                    } catch (Exception Ex) {

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        strImg = posts.getImage_id();
        holder.tv_content_post.setText(posts.getContent_post());
        String timestamp = posts.getDay_create();
        holder.tv_create_at.setText(getTimeAgo(Long.parseLong(timestamp), context));
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
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        LikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataLikes.clear();
                countLike = 0;
                if (snapshot.getChildren() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Like like1 = ds.getValue(Like.class);
                        if (like1.getId_post().equals(posts.getId_post()) && like1.getId_user().equals(user_id)) {
                            dataLikes.add(like1);
                            countLike += 1;
                            Toast.makeText(context, countLike + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (dataLikes != null) {
                        //holder.icon_like.setImageResource(R.drawable.ic_favorite_like);
                        holder.tv_total_like.setText(countLike + "");
                        holder.icon_like.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onClick(View view) {
                                countLike -= 1;
                                holder.tv_total_like.setText(countLike + "");
                                String id_like = System.currentTimeMillis() + "";
                                //Like like = new Like(id_like, posts.getId_post(), user_id, System.currentTimeMillis()+"");
                                holder.icon_like.setImageResource(R.drawable.ic_favorite_unlike);
                                Snackbar.make(holder.itemView, "Unlike", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        holder.icon_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                countLike += 1;
                                holder.tv_total_like.setText(countLike + "");
                                String id_like = System.currentTimeMillis() + "";
                                Like like = new Like(id_like, posts.getId_post(), user_id, System.currentTimeMillis() + "");
                                holder.icon_like.setImageResource(R.drawable.ic_favorite_like);
                                Snackbar.make(holder.itemView, "Like", Snackbar.LENGTH_SHORT).show();
                            }
                        });

                    }
                    //else {
//                        holder.icon_like.setImageResource(R.drawable.ic_favorite_unlike);
//                        holder.icon_like.setOnClickListener(new View.OnClickListener() {
//                            @SuppressLint("WrongConstant")
//                            @Override
//                            public void onClick(View view) {
//                                int totalLike = Integer.parseInt(holder.tv_total_like.getText().toString());
//                                totalLike+=1;
//                                holder.tv_total_like.setText(totalLike+"");
//                                Snackbar.make(holder.itemView, "Like", Snackbar.LENGTH_SHORT).show();
//                                String id_like = System.currentTimeMillis()+ "";
//                                Like like = new Like(id_like, posts.getId_post(), user_id, System.currentTimeMillis()+"");
//                                LikesRef.child(id_like).setValue(like);
//                                holder.icon_like.setImageResource(R.drawable.ic_favorite_like);
//                            }
//                        });
//
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static String getTimeAgo(long time, Context context) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return context.getResources().getString(R.string.just_now);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return context.getResources().getString(R.string.a_minute_ago);
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " " + context.getResources().getString(R.string.minutes_ago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            return context.getResources().getString(R.string.an_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " " + context.getResources().getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return context.getResources().getString(R.string.yesterday);
        } else {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(time);
            String dateTimeMes = DateFormat.format("dd/MM/yyyy hh:mm", calendar).toString();
            return diff / DAY_MILLIS + " " + context.getResources().getString(R.string.days_ago) + " | " + dateTimeMes;
        }
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
        }
    }
}

package com.tdc.edu.vn.heathcareapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.DetailPostActivity;
import com.tdc.edu.vn.heathcareapp.Model.Post;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context context;
    ArrayList<Post> dataPosts;

    public PostAdapter(Context context, ArrayList<Post> dataPosts) {
        this.context = context;
        this.dataPosts = dataPosts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new  PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post posts = dataPosts.get(position);
        if (posts == null){
            return;
        }

        holder.tv_content_post.setText(posts.getContent_post());
        holder.tv_create_at.setText(posts.getDay_create());
        String totalLike = String.valueOf(posts.getTotal_like());
        holder.tv_total_like.setText(totalLike);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPostActivity.class);
                ((Activity) context).startActivity(intent);
            }
        });
        holder.icon_like.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                int totalLike = Integer.parseInt(holder.tv_total_like.getText().toString());
                totalLike+=1;
                holder.tv_total_like.setText(totalLike+"");
                Snackbar.make(holder.itemView, "Like", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dataPosts != null){
            return dataPosts.size();
        }
        return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content_post, tv_create_at, tv_total_like;
        ImageButton icon_like;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content_post = itemView.findViewById(R.id.tv_content_item_post);
            tv_create_at = itemView.findViewById(R.id.tv_created_At_item_post);
            tv_total_like = itemView.findViewById(R.id.tv_total_like_item_post);
            icon_like = itemView.findViewById(R.id.icon_like_item_post);
        }
    }
}

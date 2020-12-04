package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.Model.Comment;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    Context context;
    ArrayList<Comment> mDataComment;

    public CommentAdapter(Context context, ArrayList<Comment> mDataComment) {
        this.context = context;
        this.mDataComment = mDataComment;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comments = mDataComment.get(position);
        if (comments == null){
            return;
        }

        holder.tv_content.setText(comments.getContent_cmt());
    }

    @Override
    public int getItemCount() {
        if (mDataComment != null){
            return mDataComment.size();
        }
        return 0;
    }

//    @NonNull
//    @Override
//    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
//        return new CommentViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
//        Comment comment = mDataComment.get(position);
//        if (comment == null){
//            return;
//        }
//
//        holder.tv_content.setText(comment.getContent_cmt());
//        holder.tv_createAt.setText(comment.getCreateAt_cmt());
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mDataComment != null){
//            mDataComment.size();
//        }
//        return 0;
//    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView img_user;
        TextView tv_username, tv_createAt, tv_content;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_item_cmt);
            tv_username = itemView.findViewById(R.id.username_item_cmt);
            tv_content = itemView.findViewById(R.id.content_item_cmt);
            tv_createAt = itemView.findViewById(R.id.createAt_item_cmt);
        }
    }
}

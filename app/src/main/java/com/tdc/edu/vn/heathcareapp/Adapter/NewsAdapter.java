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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.Model.New;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    ArrayList<New> mNews;
    Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private String strImg = "";

    public NewsAdapter(ArrayList<New> mNews, Context context) {
        this.mNews = mNews;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        New news = mNews.get(position);
        if (news == null) {
            return;
        }
//        strImg = news.getImg_new();
//        try {
//            StorageReference islandRef = storageRef.child("images/news/" + strImg);
//            final long ONE_MEGABYTE = 1024 * 1024;
//            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    // Data for "images/island.jpg" is returns, use this as needed
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    holder.imageViewNews.setImageBitmap(bitmap);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });
//
//        } catch (Exception ex) {
//
//        }
//        holder.tv_title_news.setText(news.getTitle_new());
//        holder.tv_content_news.setText(news.getContent_new());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailNewsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url_news", news.getUrl_new());
//                intent.putExtras(bundle);
//                ((Activity) context).startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mNews != null) {
            return mNews.size();
        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNews;
        TextView tv_title_news, tv_content_news, tv_author_news;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNews = itemView.findViewById(R.id.img_item_news);
            tv_author_news = itemView.findViewById(R.id.tv_author_item_news);
            tv_content_news = itemView.findViewById(R.id.tv_content_item_news);
            tv_title_news = itemView.findViewById(R.id.tv_title_item_news);

        }
    }
}

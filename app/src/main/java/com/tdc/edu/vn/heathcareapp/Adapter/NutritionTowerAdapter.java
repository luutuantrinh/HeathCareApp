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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.Model.NewAndNutrition;
import com.tdc.edu.vn.heathcareapp.Model.NutritionTower;
import com.tdc.edu.vn.heathcareapp.NutritionActivity;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class NutritionTowerAdapter extends RecyclerView.Adapter<NutritionTowerAdapter.NutritionTowerViewHolder> {
    private ArrayList<NewAndNutrition> mNutritionTower;
    Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public NutritionTowerAdapter(Context context, ArrayList<NewAndNutrition> arrayListData) {
        this.context = context;
        this.mNutritionTower = arrayListData;
    }


    @NonNull
    @Override
    public NutritionTowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_nutrition_tower, parent, false);
        return new NutritionTowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionTowerViewHolder holder, int position) {
        NewAndNutrition nutritionTower = mNutritionTower.get(position);
        if (nutritionTower == null){
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url_news", nutritionTower.getUrl());
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        holder.tv_title_nutrition_tower.setText(nutritionTower.getTitle());
        //holder.imageViewNT.setImageResource(nutritionTower.getId_nt());
        String strImg = nutritionTower.getImage_id();
        try {
            StorageReference islandRef = storageRef.child("images/news/" + strImg);
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageViewNT.setImageBitmap(bitmap);
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

    @Override
    public int getItemCount() {
        if (mNutritionTower != null){
            return mNutritionTower.size();
        }
        return 0;
    }

    public class NutritionTowerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNT;
        TextView tv_title_nutrition_tower;

        public NutritionTowerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNT = itemView.findViewById(R.id.img_item_nutrition_tower);
            tv_title_nutrition_tower = itemView.findViewById(R.id.title_item_nutrition_tower);
        }
    }
}

//    ImageView imageViewNT;
//    TextView tv_title_nutrition_tower;
//
//    public NutritionTowerAdapter(@NonNull View itemView) {
//        super(itemView);
//        imageViewNT = itemView.findViewById(R.id.img_item_nutrition_tower);
//        tv_title_nutrition_tower = itemView.findViewById(R.id.title_item_nutrition_tower);
//
//    }
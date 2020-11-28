package com.tdc.edu.vn.heathcareapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;

public class NutritionTowerAdapter extends RecyclerView.Adapter<NutritionTowerAdapter.NutritionTowerViewHolder> {
    @NonNull
    @Override
    public NutritionTowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionTowerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NutritionTowerViewHolder extends RecyclerView.ViewHolder{
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
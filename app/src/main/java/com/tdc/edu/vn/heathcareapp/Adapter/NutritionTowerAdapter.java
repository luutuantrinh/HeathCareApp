package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.Model.NewAndNutrition;
import com.tdc.edu.vn.heathcareapp.Model.NutritionTower;
import com.tdc.edu.vn.heathcareapp.NutritionActivity;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class NutritionTowerAdapter extends RecyclerView.Adapter<NutritionTowerAdapter.NutritionTowerViewHolder> {
    private ArrayList<NewAndNutrition> mNutritionTower;
    Context context;

    public NutritionTowerAdapter(NutritionActivity nutritionActivity, ArrayList<NewAndNutrition> arrayListData) {
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
        holder.tv_title_nutrition_tower.setText(nutritionTower.getTitle());
        //holder.imageViewNT.setImageResource(nutritionTower.getId_nt());
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
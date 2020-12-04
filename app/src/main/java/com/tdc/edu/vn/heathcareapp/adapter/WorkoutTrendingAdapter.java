package com.tdc.edu.vn.heathcareapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.ItemTrendingItem;
import com.tdc.edu.vn.heathcareapp.ItemWorkout;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutTrending;

import java.util.ArrayList;

public class WorkoutTrendingAdapter extends RecyclerView.Adapter<WorkoutTrendingAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<WorkoutTrending> listtrending;
    public WorkoutTrendingAdapter(Context context, ArrayList<WorkoutTrending> listtrending) {
        this.context = context;
        this.listtrending = listtrending;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_trendingworkouts,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        WorkoutTrending workoutTrending = listtrending.get(position);
        holder.txtTitle.setText(workoutTrending.getsTitle());
        holder.Description.setText(workoutTrending.getsDes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemTrendingItem.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listtrending.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgtrending);
            txtTitle  = itemView.findViewById(R.id.txttrending);
            Description  = itemView.findViewById(R.id.txtdestrending);

        }
    }
}

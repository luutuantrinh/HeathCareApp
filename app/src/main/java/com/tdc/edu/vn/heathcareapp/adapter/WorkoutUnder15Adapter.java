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
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutTrending;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutUnder15;

import java.util.ArrayList;

public class WorkoutUnder15Adapter extends RecyclerView.Adapter<WorkoutUnder15Adapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<WorkoutUnder15> listunder;
    public WorkoutUnder15Adapter(Context context, ArrayList<WorkoutUnder15> listunder) {
        this.context = context;
        this.listunder = listunder;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_under15workouts,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        WorkoutUnder15 workoutUnder15 = listunder.get(position);
        holder.txtTitle.setText(workoutUnder15.getsTitle());
        holder.Description.setText(workoutUnder15.getsDes());
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
        return listunder.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgunder);
            txtTitle  = itemView.findViewById(R.id.txtunder);
            Description  = itemView.findViewById(R.id.txtdesunder);

        }
    }
}
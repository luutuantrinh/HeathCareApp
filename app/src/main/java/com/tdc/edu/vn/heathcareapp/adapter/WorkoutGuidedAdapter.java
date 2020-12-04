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

import com.tdc.edu.vn.heathcareapp.ItemWorkout;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;

import java.util.ArrayList;

public class WorkoutGuidedAdapter extends RecyclerView.Adapter<WorkoutGuidedAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<WorkoutGuided> listGuided;
    public WorkoutGuidedAdapter(Context context, ArrayList<WorkoutGuided> listGuided) {
        this.context = context;
        this.listGuided = listGuided;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_guidedworkouts,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        WorkoutGuided workoutGuided = listGuided.get(position);
        holder.txtTitle.setText(workoutGuided.getsTitle());
        holder.Description.setText(workoutGuided.getsDes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemWorkout.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listGuided.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgguided);
            txtTitle  = itemView.findViewById(R.id.txtguided);
            Description  = itemView.findViewById(R.id.txtdescription);

        }
    }
}

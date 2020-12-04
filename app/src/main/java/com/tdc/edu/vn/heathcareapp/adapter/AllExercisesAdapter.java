package com.tdc.edu.vn.heathcareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;

import java.util.ArrayList;

public class AllExercisesAdapter extends RecyclerView.Adapter<AllExercisesAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<AllExercises> listallExercises;
    public AllExercisesAdapter(Context context, ArrayList<AllExercises> listallExercises) {
        this.context = context;
        this.listallExercises = listallExercises;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_allexercises,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        AllExercises allExercises = listallExercises.get(position);
        holder.txtTitle.setText(allExercises.getsTitle());
    }

    @Override
    public int getItemCount() {
        return listallExercises.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgallExercises);
            txtTitle  = itemView.findViewById(R.id.txttileexercises);
        }
    }
}

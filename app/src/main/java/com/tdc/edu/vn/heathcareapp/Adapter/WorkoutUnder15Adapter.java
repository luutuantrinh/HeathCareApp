package com.tdc.edu.vn.heathcareapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tdc.edu.vn.heathcareapp.Model.WorkoutUnder15;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.UnderItem;


import java.util.ArrayList;

public class WorkoutUnder15Adapter extends RecyclerView.Adapter<WorkoutUnder15Adapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<WorkoutUnder15> listunder = new ArrayList<>();
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
    String url = workoutUnder15.getUrl().toString();
    Uri uri = Uri.parse(url);
        Picasso.get().load(uri).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UnderItem.class);
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

        ImageView img;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.imgunder);
            txtTitle  = itemView.findViewById(R.id.txtunder);
            Description  = itemView.findViewById(R.id.txtdesunder);

        }
    }
}

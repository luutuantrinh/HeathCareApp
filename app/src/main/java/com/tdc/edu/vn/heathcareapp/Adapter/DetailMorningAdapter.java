package com.tdc.edu.vn.heathcareapp.Adapter;

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

import com.tdc.edu.vn.heathcareapp.Model.DetailMorning;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.VideoMain;


import java.util.ArrayList;

public class DetailMorningAdapter extends RecyclerView.Adapter<DetailMorningAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<DetailMorning> listdetailmorning;
    public DetailMorningAdapter(Context context, ArrayList<DetailMorning> listdetailmorning) {
        this.context = context;
        this.listdetailmorning = listdetailmorning;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_detailmorning,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        DetailMorning detailMorning = listdetailmorning.get(position);
        holder.txtTitle.setText(detailMorning.getsTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoMain.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdetailmorning.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgdetailmorning);
            txtTitle  = itemView.findViewById(R.id.txtdetailmorning);
        }
    }
}

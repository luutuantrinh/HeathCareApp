package com.tdc.edu.vn.heathcareapp.adapter;

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
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.VideoMain;
import com.tdc.edu.vn.heathcareapp.data_model.AllExercises;

import java.util.ArrayList;

public class UnderItemAdapter extends RecyclerView.Adapter<UnderItemAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<AllExercises> listalunder;
    public UnderItemAdapter(Context context, ArrayList<AllExercises> listalunder) {
        this.context = context;
        this.listalunder = listalunder;
    }


    @NonNull
    @Override
    public WorkoutGuidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_under15,parent,false);
        return new WorkoutGuidedViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutGuidedViewHolder holder, int position) {
        AllExercises allExercises = listalunder.get(position);
        holder.txtTitle.setText(allExercises.getsTitle());
        String url = allExercises.getUrl().toString();
        Uri uri = Uri.parse(url);
        Picasso.get().load(uri).into(holder.img);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoMain.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listalunder.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txtTitle;

        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            img  = itemView.findViewById(R.id.imgitemunderitem);
            txtTitle  = itemView.findViewById(R.id.txtitemunderitem);
        }
    }
}

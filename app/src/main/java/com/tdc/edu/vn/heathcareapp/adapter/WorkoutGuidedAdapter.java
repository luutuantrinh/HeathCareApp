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
import com.tdc.edu.vn.heathcareapp.GuidedItem;
import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutGuided;
import com.tdc.edu.vn.heathcareapp.data_model.WorkoutTrending;

import java.util.ArrayList;

public class WorkoutGuidedAdapter extends RecyclerView.Adapter<WorkoutGuidedAdapter.WorkoutGuidedViewHolder> {
    Context context;
    ArrayList<WorkoutGuided> listguided =  new ArrayList<>();

    public WorkoutGuidedAdapter(Context context, ArrayList<WorkoutGuided> listguided) {
        this.context = context;
        this.listguided = listguided;
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
        WorkoutGuided workoutGuided = listguided.get(position);
        holder.txtTitle.setText(workoutGuided.getsTitle());
        holder.Description.setText(workoutGuided.getsDes());
        String url = workoutGuided.getUrl().toString();
        Uri uri = Uri.parse(url);
        Picasso.get().load(uri).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuidedItem.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listguided.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txtTitle;
        TextView Description;
        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            img  = itemView.findViewById(R.id.imgguided);
            txtTitle = itemView.findViewById(R.id.txtguided);
            Description = itemView.findViewById(R.id.txtdescriptionguided);
        }
    }
}

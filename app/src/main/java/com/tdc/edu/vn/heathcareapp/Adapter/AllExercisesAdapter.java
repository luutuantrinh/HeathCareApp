package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
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
import com.tdc.edu.vn.heathcareapp.Model.AllExercises;

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
        String url = allExercises.getUrl().toString();
        Uri uri = Uri.parse(url);
        Picasso.get().load(uri).into(holder.img);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrendingItem.class);
                ((Activity)context).startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listallExercises.size();
    }

    public  class WorkoutGuidedViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txtTitle;

        public WorkoutGuidedViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.imgallExercises);
            txtTitle  = itemView.findViewById(R.id.txtallexerciese);
        }
    }
}

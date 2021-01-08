package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.Model.ProgressRecent;

import java.util.ArrayList;

public class ProgessRecentAdapter extends RecyclerView.Adapter<ProgessRecentAdapter.ProgressRecentViewHolder> {
    Context context;
    ArrayList<ProgressRecent> listrecent;
    public ProgessRecentAdapter(Context context, ArrayList<ProgressRecent> listrecent) {
        this.context = context;
        this.listrecent = listrecent;
    }


    @NonNull
    @Override
    public ProgressRecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recentprogress,parent,false);
        return new ProgressRecentViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressRecentViewHolder holder, int position) {
        ProgressRecent progressRecent = listrecent.get(position);
        holder.txtTitle.setText(progressRecent.getsTitle());
        holder.Description.setText(progressRecent.getsDes());

    }

    @Override
    public int getItemCount() {
        return listrecent.size();
    }

    public  class ProgressRecentViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public ProgressRecentViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgrecent);
            txtTitle  = itemView.findViewById(R.id.txttype);
            Description  = itemView.findViewById(R.id.txtTime);

        }
    }
}

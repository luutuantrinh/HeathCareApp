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
import com.tdc.edu.vn.heathcareapp.data_model.ProgressChallenges;
import com.tdc.edu.vn.heathcareapp.data_model.ProgressRecent;

import java.util.ArrayList;

public class ProgessChallengesAdapter extends RecyclerView.Adapter<ProgessChallengesAdapter.ProgressRecentViewHolder> {
    Context context;
    ArrayList<ProgressChallenges> listchallenges;
    public ProgessChallengesAdapter(Context context, ArrayList<ProgressChallenges> listchallenges) {
        this.context = context;
        this.listchallenges = listchallenges;
    }


    @NonNull
    @Override
    public ProgressRecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_challengesprogress,parent,false);
        return new ProgressRecentViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressRecentViewHolder holder, int position) {
        ProgressChallenges progressChallenges = listchallenges.get(position);
        holder.txtTitle.setText(progressChallenges.getsTitle());
        holder.Description.setText(progressChallenges.getsDes());

    }

    @Override
    public int getItemCount() {
        return listchallenges.size();
    }

    public  class ProgressRecentViewHolder extends RecyclerView.ViewHolder{

        ImageView iResource;
        TextView txtTitle;
        TextView Description;
        public ProgressRecentViewHolder(@NonNull View itemView) {

            super(itemView);
            iResource  = itemView.findViewById(R.id.imgchallenges);
            txtTitle  = itemView.findViewById(R.id.txttilechallenges);
            Description  = itemView.findViewById(R.id.txtdeschallenges);

        }
    }
}

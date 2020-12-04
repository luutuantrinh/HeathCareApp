package com.tdc.edu.vn.heathcareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.data_model.FeedBackA;

import java.util.ArrayList;

public class FeedBackAAdapter extends RecyclerView.Adapter<FeedBackAAdapter.FeedBackAAdapterViewHolder> {
    Context context;
    ArrayList<FeedBackA> listfeedback;

    public FeedBackAAdapter(Context context, ArrayList<FeedBackA> listfeedback) {
        this.context = context;
        this.listfeedback = listfeedback;
    }

    @NonNull
    @Override
    public FeedBackAAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_feedbacka,parent,false);
        return new FeedBackAAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedBackAAdapterViewHolder holder, int position) {
            FeedBackA feedBackA = listfeedback.get(position);
            holder.txtnamecheck.setText(feedBackA.getsNameCheck());

            holder.rdbcheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.rdbcheck.isChecked()){
                        listfeedback.get(position).setIscheck(true);
                    }else {
                        listfeedback.get(position).setIscheck(false);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return listfeedback.size();
    }


    public class FeedBackAAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtnamecheck;
        RadioButton rdbcheck;
        public FeedBackAAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnamecheck = itemView.findViewById(R.id.txtcheck);
            rdbcheck = itemView.findViewById(R.id.rdbcheck);
        }
    }
}

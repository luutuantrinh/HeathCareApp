package com.tdc.edu.vn.heathcareapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tdc.edu.vn.heathcareapp.ConversationDetailActivity;
import com.tdc.edu.vn.heathcareapp.DetailNewsActivity;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class CounselorAdapter extends RecyclerView.Adapter<CounselorAdapter.CounselorViewHolder> {
    Context context;
    ArrayList<Counselor> mDataCounselor;

    public CounselorAdapter(Context context, ArrayList<Counselor> mDataCounselor) {
        this.context = context;
        this.mDataCounselor = mDataCounselor;
    }

    @NonNull
    @Override
    public CounselorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new CounselorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounselorViewHolder holder, int position) {
        Counselor counselor = mDataCounselor.get(position);
        if (counselor == null){
            return;
        }

        holder.tv_name_counselor.setText(counselor.getName_counselor());
        holder.tv_des_counselor.setText(counselor.getDes_counselor());
        holder.img_button_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(holder.itemView, "Messenger for ...", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ConversationDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url_news", news.getUrl_new());
//                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataCounselor != null){
            return mDataCounselor.size();
        }
        return 0;
    }

    public class CounselorViewHolder extends RecyclerView.ViewHolder{
        ImageButton img_button_messenger;
        ImageView imageViewUser;
        TextView tv_name_counselor, tv_des_counselor;
        public CounselorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_des_counselor = itemView.findViewById(R.id.tv_description_item_chat);
            tv_name_counselor = itemView.findViewById(R.id.tv_username_item_chat);
            img_button_messenger = itemView.findViewById(R.id.btn_telegram_item_chat);
        }
    }
}

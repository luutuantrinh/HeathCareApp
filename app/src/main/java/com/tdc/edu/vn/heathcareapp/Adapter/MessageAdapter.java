package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int MES_TYPE_LEFT = 0;
    private static final int MES_TYPE_RIGHT = 1;

    Context context;
    ArrayList<Message> dataMessage = new ArrayList<>();
    String imageURL;

    public MessageAdapter(Context context, ArrayList<Message> dataMessage, String imageURL) {
        this.context = context;
        this.dataMessage = dataMessage;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MES_TYPE_LEFT){
            View view = LayoutInflater.from(context).inflate(R.layout.message_left, parent, false);
            return new MessageViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_right, parent, false);
            return new MessageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = dataMessage.get(position).getMessage();
        String timestamp = dataMessage.get(position).getTimestamp();

        holder.tv_message.setText(message);
    }

    @Override
    public int getItemCount() {
        if (dataMessage!=null){
            return dataMessage.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewUser;
        TextView tv_message, tv_timestamp, tv_idSeen;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUser = itemView.findViewById(R.id.img_user_message);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_timestamp = itemView.findViewById(R.id.tv_timestamp_message);
            tv_idSeen = itemView.findViewById(R.id.tv_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MES_TYPE_RIGHT;
        //return super.getItemViewType(position);
    }
}

package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.type.DateTime;
import com.tdc.edu.vn.heathcareapp.Model.Message;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int MES_TYPE_LEFT = 0;
    private static final int MES_TYPE_RIGHT = 1;
    Boolean visibleTimestamp = true;
    Context context;
    ArrayList<Message> dataMessage = new ArrayList<>();
    String imageURL;
    private FirebaseUser firebaseUser;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public MessageAdapter(Context context, ArrayList<Message> dataMessage, String imageURL) {
        this.context = context;
        this.dataMessage = dataMessage;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MES_TYPE_LEFT){
            view = LayoutInflater.from(context).inflate(R.layout.message_left, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.message_right, parent, false);
        }
        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = dataMessage.get(position).getMessage();

        String timestamp = dataMessage.get(position).getTimestamp();
        holder.tv_message.setText(message);
        holder.tv_timestamp.setText(getTimeAgo(Long.parseLong(timestamp), context));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visibleTimestamp == true){
                    holder.tv_timestamp.setVisibility(View.VISIBLE);
                    visibleTimestamp = false;
                }else {
                    holder.tv_timestamp.setVisibility(View.GONE);
                    visibleTimestamp = true;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        if (dataMessage!=null){
            return dataMessage.size();
        }
        return 0;
    }

    public static String getTimeAgo(long time, Context context) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return context.getResources().getString(R.string.just_now);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return context.getResources().getString(R.string.a_minute_ago);
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " " + context.getResources().getString(R.string.minutes_ago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            return context.getResources().getString(R.string.an_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " " + context.getResources().getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return context.getResources().getString(R.string.yesterday);
        } else {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(time);
            String dateTimeMes = DateFormat.format("dd/MM/yyyy hh:mm", calendar).toString();
            return diff / DAY_MILLIS + " " + context.getResources().getString(R.string.days_ago) +" | "+ dateTimeMes;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewUser;
        TextView tv_message, tv_timestamp, tv_idSeen;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUser = itemView.findViewById(R.id.img_user_message);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_timestamp = itemView.findViewById(R.id.tv_timestamp_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
       firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (dataMessage.get(position).getSender().equals(firebaseUser.getUid())){
            return MES_TYPE_RIGHT;
        }else {
            return MES_TYPE_LEFT;
        }

        //return super.getItemViewType(position);
    }
}

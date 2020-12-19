package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.SearchFriendViewHolder> {

    Context context;
    ArrayList<User> mDataUsers = new ArrayList<>();

    public SearchFriendAdapter(Context context, ArrayList<User> mDataUsers) {
        this.context = context;
        this.mDataUsers = mDataUsers;
    }

    @NonNull
    @Override
    public SearchFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_search, parent, false);
        return new SearchFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFriendViewHolder holder, int position) {
        User user = mDataUsers.get(position);
        holder.tv_firstName.setText(user.getFirst_name());
        holder.tv_LastName.setText(user.getLast_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "user_id" + user.getUser_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataUsers != null) {
            return mDataUsers.size();
        }
        return 0;
    }

    public class SearchFriendViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tv_firstName, tv_LastName;

        public SearchFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar_item_search_user);
            tv_firstName = itemView.findViewById(R.id.txt_firstName_item_search_user);
            tv_LastName = itemView.findViewById(R.id.txt_lastName_item_search_user);
        }
    }
}

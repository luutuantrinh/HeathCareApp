package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tdc.edu.vn.heathcareapp.Model.Counselor;
import com.tdc.edu.vn.heathcareapp.Model.User;
import com.tdc.edu.vn.heathcareapp.R;

import java.util.ArrayList;

public class CounselorAdapter extends RecyclerView.Adapter<CounselorAdapter.CounselorViewHolder> {
    Context context;
    ArrayList<Counselor> mCounselors = new ArrayList<>();
    FirebaseUser firebaseUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference UsersRef = database.getReference("Users");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<User> dataUsers = new ArrayList<>();

    public CounselorAdapter(Context context, ArrayList<Counselor> mCounselors) {
        this.context = context;
        this.mCounselors = mCounselors;
    }

    @NonNull
    @Override
    public CounselorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_counselor, parent, false);
        return new CounselorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounselorViewHolder holder, int position) {
        Counselor counselor = mCounselors.get(position);
        if (counselor == null) {
            return;
        }
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUsers.clear();
                if (snapshot.hasChild(counselor.getUser_id())) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(counselor.getId_counselor())) {
                            dataUsers.add(user);
                        }
                    }
                    if (dataUsers != null) {
                        holder.tv_fullName.setText(dataUsers.get(0).getFirst_name() + " " + dataUsers.get(0).getLast_name());
                        holder.tv_email.setText(dataUsers.get(0).getEmail());
                        holder.tv_phone.setText(dataUsers.get(0).getPhone());

                        String imgUser = dataUsers.get(0).getImage_id();
                        if (!imgUser.equals("")){

                            try {
                                StorageReference islandRef = storageRef.child("images/user/" + imgUser);
                                final long ONE_MEGABYTE = 1024 * 1024;
                                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Data for "images/island.jpg" is returns, use this as needed
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        holder.img_avatar.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            } catch (Exception ex) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.tv_position.setText(counselor.getPosition_counselor());
        holder.ratingBar.setRating(counselor.getTotal_ratting());
    }

    @Override
    public int getItemCount() {
        if (mCounselors != null) {
            return mCounselors.size();
        }
        return 0;
    }


    public class CounselorViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avatar;
        RatingBar ratingBar;
        TextView tv_fullName, tv_phone, tv_email, tv_position;

        public CounselorViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_item_counselor);
            ratingBar = itemView.findViewById(R.id.ratting_item_counselor);
            tv_email = itemView.findViewById(R.id.tv_email_item_counselor);
            tv_fullName = itemView.findViewById(R.id.tv_fullName_item_counselor);
            tv_phone = itemView.findViewById(R.id.tv_phone_item_counselor);
            tv_position = itemView.findViewById(R.id.tv_position_item_counselor);

        }
    }
}

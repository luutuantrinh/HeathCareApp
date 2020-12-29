package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.text.TextUtils.isEmpty;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

import android.os.Bundle;

public class EditProfile extends AppCompatActivity {

    ImageButton imageButtonBackSpace, imageButtonMore;
    EditText firstName, lastName, age, email, location, width,height;
    ImageView Avatar;
    Button btnJoin;
    CountryCodePicker ccp;
    RadioButton male,female;
    FirebaseUser user;
    FirebaseAuth auth;
    Intent intent;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String storagePath = "Users_Profile_Imgs/";
    ImageView coverIV;
    FloatingActionButton actionButton;
    ProgressDialog pd;
    String cameraPermission[];
    String storagePermission[];
    Uri imageUri;
    String profileCoverPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        auth = getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        DatabaseReference databaseIBM = firebaseDatabase.getReference("IBM");
        storageReference = FirebaseStorage.getInstance().getReference();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        btnJoin = (Button) findViewById(R.id.btnJoin);
        Avatar = (ImageView) findViewById(R.id.img_avatar);
        firstName = (EditText) findViewById(R.id.edtFirstName);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_edit_profile);
        lastName = (EditText) findViewById(R.id.edtLastName);
        age = (EditText) findViewById(R.id.edtAge);
        email = (EditText) findViewById(R.id.edtEmail);
        location = (EditText) findViewById(R.id.location);
        width = (EditText) findViewById(R.id.edtWidth);
        height = (EditText) findViewById(R.id.edtHeight);
        ccp = (CountryCodePicker) findViewById(R.id.cpp);
        male = (RadioButton) findViewById(R.id.radMale);
        female = (RadioButton) findViewById(R.id.radFemale);

        Query qq = databaseIBM.orderByChild("uid").equalTo(user.getUid());
        qq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String Height = "" + ds.child("height").getValue();
                    String Width = "" + ds.child("width").getValue();

                    height.setText(Height);
                    width.setText(Width);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    String LastName = "" + ds.child("last_name").getValue();
                    String FirstName = "" + ds.child("first_name").getValue();
                    String Email = "" + ds.child("email").getValue();
                    String Age = "" + ds.child("age").getValue();
                    String Location = "" + ds.child("location").getValue();
                    String image = "" + ds.child("image").getValue();
                    //String cover = "" + ds.child("cover").getValue();
                    String gender = "" + ds.child("gender").getValue();


                    lastName.setText(LastName);
                    firstName.setText(FirstName);
                    email.setText(Email);
                    age.setText(Age);
                    location.setText(Location);
                    if(gender.trim() == "0"){
                        male.setChecked(true);
                    }
                    else {
                        female.setChecked(true);
                    }
                    try {
                        Picasso.get().load(image).into(Avatar);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_cover).into(Avatar);
                    }
//                    try {
//                        Picasso.get().load(cover).into(coverIV);
//                    }
//                    catch (Exception e){
//                        //Picasso.get().load(R.drawable.ic_cover).into(avatar);
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThongTinThem();
                Update();
            }
        });
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void Update(){
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString().trim();
        String location = ccp.getSelectedCountryName();
        String Age = age.getText().toString().trim();
        if(!isEmpty(FirstName)) {
            //pd.show();
            String phone ="";
            String gender = "";
            if (male.isChecked()) {
                gender = "0";
            } else {
                gender = "1";
            }
            HashMap<String,Object> result = new HashMap<>();
            //result.put("email", email);
            result.put("image", "");
            result.put("last_name", LastName);
            result.put("first_name", FirstName);
            result.put("gender", gender);
            result.put("location", location);
            result.put("age", Age);
            databaseReference.child(user.getUid()).updateChildren(result)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //pd.dismiss();
                            Toast.makeText(EditProfile.this,"Update...",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //pd.dismiss();
                            Toast.makeText(EditProfile.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(EditProfile.this,"Please enter ",Toast.LENGTH_SHORT).show();
        }
    }
    private void ThongTinThem(){
        String Height = height.getText().toString().trim();
        String Width = width.getText().toString().trim();
        String uid = user.getUid();
        HashMap<Object,String> hashMap = new HashMap<>();
        hashMap.put("height", Height);
        hashMap.put("width", Width);
        hashMap.put("uid", uid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("IBM");
        reference.child(uid).setValue(hashMap);
    }
}
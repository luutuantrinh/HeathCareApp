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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ActivityProfile extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GARLLERY_REQUEST_CODE = 400;

    FirebaseUser user;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String storagePath = "Users_Profile_Imgs/";
    ImageView avatar, coverIV;
    TextView nameTV, emailTV, ageTV, locationTV;
    FloatingActionButton actionButton;
    ProgressDialog pd;
    String cameraPermission[];
    String storagePermission[];
    Uri imageUri;
    String profileCoverPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        bottomNavigationView = findViewById(R.id.BottomNavView);
        avatar = (ImageView) findViewById(R.id.avatar);
        nameTV = (TextView) findViewById(R.id.Name);
        emailTV = (TextView) findViewById(R.id.Email);
        ageTV = (TextView) findViewById(R.id.Age);
        locationTV = (TextView)findViewById(R.id.Location);
        coverIV = (ImageView) findViewById(R.id.coverIV);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        pd = new ProgressDialog(ActivityProfile.this);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NewFeed:
                        Intent intent = new Intent(getApplicationContext(), NewFeedActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        return true;
                    case R.id.Nutrition:
                        Intent inten = new Intent(getApplicationContext(), NutritionActivity.class);
                        startActivity(inten);
                        inten.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Profile:
                        return true;

                }
                return true;
            }
        });

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    String Lastname = "" + ds.child("last_name").getValue();
                    String Firstname = "" + ds.child("first_name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String age = "" + ds.child("age").getValue();
                    String location = "" + ds.child("location").getValue();
                    String image_id = "" + ds.child("image_id").getValue();
                    String cover = "" + ds.child("cover").getValue();
                    String gender = "" + ds.child("gender").getValue();

                    nameTV.setText(Firstname + " " + Lastname);
                    emailTV.setText(email);
                    ageTV.setText(age);
                    locationTV.setText(location);

                    try {
                        Picasso.get().load(image_id).into(avatar);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_cover).into(avatar);
                    }
                    try {
                        Picasso.get().load(cover).into(coverIV);
                    }
                    catch (Exception e){
                        //Picasso.get().load(R.drawable.ic_cover).into(avatar);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });
    }
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(ActivityProfile.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(){
        requestPermissions(storagePermission,STORAGE_REQUEST_CODE);

    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(ActivityProfile.this,Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(ActivityProfile.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission(){
        requestPermissions(cameraPermission,CAMERA_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(ActivityProfile.this,"Please enable permission camera", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if ( writeStorageAccepted){
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(ActivityProfile.this,"Please enable permission storage", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

    }

    private void pickFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery,IMAGE_PICK_GARLLERY_REQUEST_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Description");
        imageUri = ActivityProfile.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_CAMERA_REQUEST_CODE){
                uploadProfileCoverPhoto(imageUri);
            }
            if (requestCode == IMAGE_PICK_GARLLERY_REQUEST_CODE){
                imageUri = data.getData();
                uploadProfileCoverPhoto(imageUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(Uri uri) {
        pd.show();

        String filePathAndName = storagePath +  "" + profileCoverPhoto + "_" +  user.getUid();
        StorageReference reference = storageReference.child(filePathAndName);
        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String, Object> results = new HashMap<>();

                            results.put(profileCoverPhoto,downloadUri.toString());
                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();
                                            Toast.makeText(ActivityProfile.this,"Image Update...",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(ActivityProfile.this,"Errol Updating image...",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(ActivityProfile.this,"Some errol occured",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ActivityProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showEditProfileDialog() {
        String option[]={"Edit Profile Picture","Edit Cover Photo","Edit Profile","Sign Out"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProfile.this);

        builder.setTitle("Choose Action");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    pd.setMessage("Edit Profile Picture");
                    profileCoverPhoto = "image_id";
                    showImagePicDialog();
                }
                else if(which == 1){
                    pd.setMessage("Edit Cover Photo");
                    profileCoverPhoto = "cover";
                    showImagePicDialog();
                }
                else if(which == 2){
                    Intent intent = new Intent(ActivityProfile.this, EditProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                else if(which == 3){
                    try{
                        auth.signOut();
                        Intent intent = new Intent(ActivityProfile.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                    catch (Exception e){
                        e.getMessage();
                        Toast.makeText(ActivityProfile.this, "Please log in again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.create().show();
    }

//    private void showInfomation(String key) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProfile.this);
//        builder.setTitle("Update " + key);
//        LinearLayout linearLayout = new LinearLayout(ActivityProfile.this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(10,10,10,10);
//        EditText editText = new EditText(ActivityProfile.this);
//        editText.setHint("Enter " + key);
//        linearLayout.addView(editText);
//
//        builder.setView(linearLayout);
//        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String value = editText.getText().toString().trim();
//                if(!TextUtils.isEmpty(value)){
//                    pd.show();
//                    HashMap<String ,Object> result = new HashMap<>();
//                    result.put(key, value);
//                    databaseReference.child(user.getUid()).updateChildren(result)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    pd.dismiss();
//                                    Toast.makeText(ActivityProfile.this,"Update...",Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    pd.dismiss();
//                                    Toast.makeText(ActivityProfile.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
//                else {
//                    Toast.makeText(ActivityProfile.this,"Please enter "+key,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }

    private void showImagePicDialog() {
        String option[]={"camera","Garllery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProfile.this);

        builder.setTitle("Pick Image From");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                else if(which == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

}
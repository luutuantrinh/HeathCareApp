package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class RegistrationActive extends AppCompatActivity {
    private static final String TAG = "RegistrationActive";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GARLLERY_REQUEST_CODE = 400;
    Button btnJoin;
    EditText edtLastName;
    EditText edtFirstName;
    EditText edtAge;
    EditText edtEmail;
    EditText edtPassword;
    RadioButton radMale;
    RadioButton radFemale;
    ImageView imgAvartar;
    CountryCodePicker ccp;
    ProgressDialog pd;
    Intent intent;
    Uri imageUri;
    String cameraPermission[];
    String storagePermission[];
    DatabaseReference databaseReference;
    FirebaseUser user;
    StorageReference storageReference;
    private Uri imgUri;
    String image = "";
    String url_image = "";
    Boolean checkImage = false;
    private StorageTask uploadTask;
    String storagePath = "Users_Profile_Imgs/";
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static final int Request_code_image = 123;
    private FirebaseAuth mAuth;
    String profileCoverPhoto;
    private static final String tt = "EmailPassword";
    FirebaseDatabase database;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference(storagePath);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgAvartar = (ImageView) findViewById(R.id.imgAvatar);
        radMale = (RadioButton) findViewById(R.id.radMale);
        radFemale = (RadioButton) findViewById(R.id.radFemale);
        ccp = (CountryCodePicker) findViewById(R.id.Location);
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        User = new User();

        pd = new ProgressDialog(RegistrationActive.this);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();
                //uploadProfileCoverPhoto(imageUri);
            }
        });
        imgAvartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,Request_code_image);
                //showImagePicDialog();
                //uploadProfileCoverPhoto(imageUri);
                chooseImage();
                checkImage = true;
                Toast.makeText(RegistrationActive.this, "Choose Image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void DangKy() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrationActive.this, "Sign Up Success",
                                    Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            String firstName = edtFirstName.getText().toString().trim();
                            String lastName = edtLastName.getText().toString().trim();
                            String gender = "";
                            String location = ccp.getSelectedCountryName();
                            String age = edtAge.getText().toString().trim();
                            if (checkImage == true){
                                image = System.currentTimeMillis() + "." + getExtension(imgUri);
                                if (uploadTask != null && uploadTask.isInProgress()) {
                                    Toast.makeText(RegistrationActive.this, "In progress upload!", Toast.LENGTH_SHORT).show();
                                } else {
                                    uploadFile(image);
                                }
                            }else {
                                image = "";
                            }
                            String phone = "";
                            if (radMale.isChecked()) {
                                gender = "0";
                            } else {
                                gender = "1";
                            }
                            String email = user.getEmail();
                            String uid = user.getUid();
                            User = new User(System.currentTimeMillis()+"",age,gender,uid,firstName,lastName,image,email,phone,location);
//                            HashMap<Object,String> hashMap = new HashMap<>();
//                            hashMap.put("email", email);
//                            hashMap.put("name", firstName + " " + lastName);
//                            hashMap.put("image", "");
//                            hashMap.put("cover", "");
//                            hashMap.put("uid", uid);
//                            hashMap.put("gender", gender);
//                            hashMap.put("location", location);
//                            hashMap.put("age", age);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(User);
                            intent = new Intent(RegistrationActive.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActive.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

         if(resultCode == RESULT_OK)
         {
             if (requestCode == IMAGE_PICK_CAMERA_REQUEST_CODE){
                 //uploadProfileCoverPhoto(imageUri);
             }
             if (requestCode == IMAGE_PICK_GARLLERY_REQUEST_CODE){
                 imageUri = data.getData();
                 //uploadProfileCoverPhoto(imageUri);
             }
         }
         super.onActivityResult(requestCode, resultCode, data);
     }*/
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
                                            Toast.makeText(RegistrationActive.this,"Image Update...",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(RegistrationActive.this,"Errol Updating image...",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(RegistrationActive.this,"Some errol occured",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RegistrationActive.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(RegistrationActive.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(){
        requestPermissions(storagePermission,STORAGE_REQUEST_CODE);

    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(RegistrationActive.this,Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(RegistrationActive.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                        Toast.makeText(RegistrationActive.this,"Please enable permission camera", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegistrationActive.this,"Please enable permission storage", Toast.LENGTH_SHORT).show();
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
        imageUri = RegistrationActive.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST_CODE);
    }
    private void showImagePicDialog() {
        String option[]={"camera","Garllery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActive.this);

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
    private void uploadFile(String id_image) {
        StorageReference ref = storageRef.child(id_image);
        uploadTask = ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        url_image = taskSnapshot.getUploadSessionUri().toString();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Request_code_image);

    }
    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_code_image && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgAvartar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
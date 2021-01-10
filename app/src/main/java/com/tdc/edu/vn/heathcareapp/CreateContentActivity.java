package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.tdc.edu.vn.heathcareapp.Model.Post;
import com.tdc.edu.vn.heathcareapp.Model.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateContentActivity extends AppCompatActivity {

    TextView titleToolbar, buttonCancel, buttonPublish, textViewUserName;
    EditText txt_content;
    Button button_delete_image;
    ImageButton btn_getImgByGallery, imageButton_getImageByGallery;
    ImageView imageView, imageViewAvatarUser;
    LinearLayout linearLayoutImageView, linearLayoutInfoUserName;
    private final int PICK_IMAGE_REQUEST = 71;
    String id_image = "";
    String url_image = "";
    Boolean isImageHas = false;
    Boolean checkImage = false;
    ArrayList<User> dataUser = new ArrayList<>();
    String post_id = "";
    Boolean checkUpdate = false;
    ArrayList<Post> dataPost = new ArrayList<>();
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsRef = database.getReference("Posts");
    DatabaseReference userRef = database.getReference("Users");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Uri imgUri;
    private StorageTask uploadTask;

    // Date
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("images/posts");
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void setEvent() {
        try {
            post_id = getIntent().getExtras().getString("post_id");
        } catch (Exception e) {
        }

        if (!post_id.equals("")) {
            postsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataPost.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Post post = ds.getValue(Post.class);
                        if (post.getId_post().equals(post_id)) {
                            dataPost.add(post);
                        }
                    }
                    if (dataPost != null) {
                        try {
                            txt_content.setText(dataPost.get(0).getContent_post());
                            if (!dataPost.get(0).getImage_id().equals("")) {
                                checkImage = true;
                                isImageHas = true;
                                String img_id = dataPost.get(0).getImage_id().toString();
                                linearLayoutImageView.setVisibility(View.VISIBLE);
                                try {
                                    StorageReference islandRef = storageRef.child("images/posts/" + img_id);
                                    final long ONE_MEGABYTE = 1024 * 1024;
                                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            imageView.setImageBitmap(bitmap);
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
                        } catch (Exception exception) {

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        showInfo(currentUser.getUid().toString());
        btn_getImgByGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                linearLayoutImageView.setVisibility(View.VISIBLE);
                checkImage = true;
                Toast.makeText(CreateContentActivity.this, "Choose Image", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutImageView.setVisibility(View.GONE);
                id_image = "";
                checkImage = false;

            }
        });

        buttonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String user_id = currentUser.getUid().toString();
                String content = txt_content.getText().toString();
                if (checkImage == true) {
                    if (post_id.equals("") || isImageHas == false) {
                        id_image = System.currentTimeMillis() + "." + getExtension(imgUri);
                    } else {
                        try {
                            id_image = dataPost.get(0).getImage_id();
                        } catch (Exception exception) {

                        }
                    }

                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(CreateContentActivity.this, "In progress upload!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            uploadFile(id_image);
                        }catch (Exception exception){

                        }

                    }
                } else {
                    id_image = "";
                }




                if (post_id.equals("")) {
                    String id_posts = System.currentTimeMillis() + user_id;
                    Post post = new Post(id_posts, user_id, id_image, content, System.currentTimeMillis() + "", 0);
                    postsRef.child(id_posts).setValue(post);
                    onBackPressed();
                } else {
                    Post post = new Post(post_id, user_id, id_image, content, dataPost.get(0).getDay_create(), 0);
                    postsRef.child(post_id).setValue(post);
                    Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }
        });
    }

    private void showInfo(String uid) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getUser_id().equals(uid)) {
                            dataUser.add(user);
                        }

                    }
                }
                try {
                    if (dataUser != null) {
                        String imageUrl = dataUser.get(0).getImage_id().toString();
                        textViewUserName.setText(dataUser.get(0).getFirst_name() + " " + dataUser.get(0).getLast_name());
                        if (!imageUrl.equals("")) {
                            try {
                                StorageReference islandRef = storageRef.child("images/user/" + imageUrl);
                                final long ONE_MEGABYTE = 1024 * 1024;
                                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Data for "images/island.jpg" is returns, use this as needed
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        imageViewAvatarUser.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });
                                //linearLayoutInfoUserName.setVisibility(View.VISIBLE);

                            } catch (Exception ex) {

                            }
                        }
                    } else {

                    }
                } catch (Exception ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("WrongViewCast")
    private void setControl() {
        buttonCancel = findViewById(R.id.btn_cancel_createContent);
        buttonPublish = findViewById(R.id.btn_publish_createContent);
        titleToolbar = findViewById(R.id.title_toolbar_createContent);
        txt_content = findViewById(R.id.txt_content_create);
        button_delete_image = findViewById(R.id.btn_delete_image_create_content);
        imageView = findViewById(R.id.imageView_create_content);
        linearLayoutImageView = findViewById(R.id.lnl_image_create_content);
        btn_getImgByGallery = findViewById(R.id.imgBtn_getImg_Gallery_create_content);
        textViewUserName = findViewById(R.id.username_create_content);
        imageViewAvatarUser = findViewById(R.id.image_avatar_user_create_content);
        linearLayoutInfoUserName = findViewById(R.id.lnl_info_username);

    }

}
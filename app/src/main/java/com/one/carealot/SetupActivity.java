package com.one.carealot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private CircleImageView setupimage;
    private Uri mainImageURI = null;
    private EditText setupName;
    private Button setupbtn;
    private String user_id;
    private Uri newUri;
    private String File;



    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar setupProgress;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Toolbar setupToolbar = findViewById(R.id.SetupToolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Account setting");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = firebaseAuth.getCurrentUser().getUid();


        setupimage = findViewById(R.id.Setup_image);
        setupName = findViewById(R.id.setup_name);
        setupbtn = findViewById(R.id.setup_btn);
        setupProgress = findViewById(R.id.Setup_progress);
        setupProgress.setVisibility(View.VISIBLE);


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        String name = task.getResult().getString("name");
                        String image = task.getResult().getString("image");




                        setupName.setText(name);
                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.start);



                        Glide.with(SetupActivity.this).load(image).into(setupimage);





                    }








                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this,"FireStore Retrive Error: "+error, Toast.LENGTH_LONG).show();

                }

                setupProgress.setVisibility(View.INVISIBLE);


            }
        });

        setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_name = setupName.getText().toString();
                if (!TextUtils.isEmpty(user_name) && mainImageURI!=null) {

                        user_id = user_name;
                        setupProgress.setVisibility(View.VISIBLE);

                        final StorageReference image_path = storageReference.child("profile_images/").child(user_id + ".jpg");

                        image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()) {
                                    Log.d("TASKNEW", task.toString());
                                    Task<Uri> download_uri = task.getResult().getStorage().getDownloadUrl();
                                    Log.d("TEST", download_uri.toString());

                                    Map<String , String> userMap = new HashMap<>();
                                    userMap.put("name", user_name);
                                    userMap.put("image", download_uri.toString());
                                    firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                Toast.makeText(SetupActivity.this,"The user settings are updated", Toast.LENGTH_LONG).show();
                                                Intent mainintent = new Intent(SetupActivity.this,Mainpage.class);
                                                startActivity(mainintent);
                                                finish();

                                            }else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(SetupActivity.this,"FireStore Error: "+error, Toast.LENGTH_LONG).show();



                                            }
                                            setupProgress.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(SetupActivity.this, "ImageError: " + error, Toast.LENGTH_LONG).show();
                                    setupProgress.setVisibility(View.INVISIBLE);
                                }


                            }
                        });


                    }



            }
        });


        setupimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(SetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(SetupActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else{
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(SetupActivity.this);
                    }
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                Log.d("TESTING", mainImageURI.toString());
                setupimage.setImageURI(mainImageURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}

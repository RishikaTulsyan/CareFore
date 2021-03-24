package com.one.carealot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class stationary extends AppCompatActivity {

    private ImageView stationery_image;
    StorageReference sStorageRef;
    public Uri imgage;
    Button stationery_cart;
    private StorageTask uploadedTask;
    EditText stationary_type, brand;
     Stationery dataes;
    DatabaseReference dberrf;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary);


        getIntent().getExtras();



        Toolbar setupToolbar = findViewById(R.id.stationary_toolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Stationary Donation");

        stationery_image = findViewById(R.id.stationary_image);
        stationery_cart = findViewById(R.id.stationery_cart);
        stationary_type =findViewById(R.id.type_stationery);
        brand =findViewById(R.id.Brand);
        dataes=new Stationery();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        dberrf = FirebaseDatabase.getInstance().getReference().child("cart").child(mCurrentUser.getUid()).child("Stationery");
        sStorageRef= FirebaseStorage.getInstance().getReference("stationer_images");



        stationery_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadedTask != null && uploadedTask.isInProgress()){
                    Toast.makeText(stationary.this,"Upload in progress",Toast.LENGTH_LONG).show();

                }else {
                    Fileuploder();
                }
            }
        });


        stationery_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(stationary.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(stationary.this,"Permission Denied",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(stationary.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else{
                        Filechooser();
                    }
                }

            }
        });

    }

    private String getExtention(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void Fileuploder() {

        String imaged;
        imaged=System.currentTimeMillis()+'.'+getExtention(imgage);
        dataes.setStationery_Type(stationary_type.getText().toString().trim());
        dataes.setBrand(brand.getText().toString().trim());
        dataes.setImaged(imaged);
        dberrf.push().setValue(dataes);

        StorageReference Ref = sStorageRef.child(imaged);

        uploadedTask =Ref.putFile(imgage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Toast.makeText(stationary.this,"Image Uploded successfully",Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode == RESULT_OK && data != null  && data.getData()!=null){

            imgage=data.getData();
            stationery_image.setImageURI(imgage);
        }
    }

    private void Filechooser(){


        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , 1);
    }


    public static class Stationery {

        private  String stationery_Type;
        private String brand;
        private String Imaged;

        public Stationery() {
        }

        public String getStationery_Type() {
            return stationery_Type;
        }

        public void setStationery_Type(String stationery_Type) {
            this.stationery_Type = stationery_Type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getImaged() {
            return Imaged;
        }

        public void setImaged(String imaged) {
            Imaged = imaged;
        }
    }
}

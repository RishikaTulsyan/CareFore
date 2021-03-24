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

public class Others extends AppCompatActivity {

    private ImageView Other_image;
    StorageReference oStorageRef;
    public Uri imageuris;
    Button others_cart;
    private StorageTask uploadTask;
    EditText Name, Discription;
    other datab;
    DatabaseReference def;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        getIntent().getExtras();



        Toolbar setupToolbar = findViewById(R.id.others_toolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("other Donation");

        Other_image = findViewById(R.id.other_image);
        others_cart = findViewById(R.id.other_cart);
        Name =findViewById(R.id.Name);
        Discription =findViewById(R.id.dis);
        datab=new other();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        def = FirebaseDatabase.getInstance().getReference().child("cart").child(mCurrentUser.getUid()).child("Others");
        oStorageRef = FirebaseStorage.getInstance().getReference("Others_images");



        others_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(Others.this,"Upload in progress",Toast.LENGTH_LONG).show();

                }else {
                    Fileuploder();
                }
            }
        });


        Other_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(Others.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(Others.this,"Permission Denied",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(Others.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
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

        String imageid;
        imageid=System.currentTimeMillis()+'.'+getExtention(imageuris);
        datab.setName(Name.getText().toString().trim());
        datab.setDescription(Discription.getText().toString().trim());
        datab.setImageid(imageid);
        def.push().setValue(datab);

        StorageReference Ref = oStorageRef.child(imageid);

        uploadTask=Ref.putFile(imageuris)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Toast.makeText(Others.this,"Image Uploded successfully",Toast.LENGTH_LONG).show();
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

            imageuris =data.getData();
            Other_image.setImageURI(imageuris);
        }
    }

    private void Filechooser(){


        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , 1);
    }

    public static class other {

        private  String Name;
        private String Description;
        private String Imageid;

        public other() {
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getImageid() {
            return Imageid;
        }

        public void setImageid(String imageid) {
            Imageid = imageid;
        }
    }
}


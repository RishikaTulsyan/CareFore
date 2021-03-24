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

public class Medication extends AppCompatActivity {

    private ImageView medicine_image;
    StorageReference cStorageRef;
    public Uri imageuri;
    Button medicine_cart;
    private StorageTask uploadTask;
    EditText For, company_name;
    medicines data;
    DatabaseReference derf;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        getIntent().getExtras();



        Toolbar setupToolbar = findViewById(R.id.medication_toolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Medication Donation");

        medicine_image = findViewById(R.id.medicine_image);
        medicine_cart = findViewById(R.id.medicine_cart);
        For =findViewById(R.id.For);
        company_name =findViewById(R.id.company);
        data=new medicines();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        derf = FirebaseDatabase.getInstance().getReference().child("cart").child(mCurrentUser.getUid()).child("Medication");
        cStorageRef = FirebaseStorage.getInstance().getReference("medicine_images");



        medicine_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(Medication.this,"Upload in progress",Toast.LENGTH_LONG).show();

                }else {
                    Fileuploder();
                }
            }
        });


        medicine_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(Medication.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(Medication.this,"Permission Denied",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(Medication.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
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
        imageid=System.currentTimeMillis()+'.'+getExtention(imageuri);
        data.setIs_used_for(For.getText().toString().trim());
        data.setCompany_name(company_name.getText().toString().trim());
        data.setImageid(imageid);
        derf.push().setValue(data);

        StorageReference Ref = cStorageRef.child(imageid);

        uploadTask=Ref.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Toast.makeText(Medication.this,"Image Uploded successfully",Toast.LENGTH_LONG).show();
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

            imageuri =data.getData();
            medicine_image.setImageURI(imageuri);
        }
    }

    private void Filechooser(){


        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , 1);
    }

    public static class medicines {
        private  String Is_used_for;
        private String Company_name;
        private String Imageid;

        public medicines() {
        }

        public String getIs_used_for() {
            return Is_used_for;
        }

        public void setIs_used_for(String is_used_for) {
            Is_used_for = is_used_for;
        }

        public String getCompany_name() {
            return Company_name;
        }

        public void setCompany_name(String company_name) {
            Company_name = company_name;
        }

        public String getImageid() {
            return Imageid;
        }

        public void setImageid(String imageid) {
            Imageid = imageid;
        }
    }
}


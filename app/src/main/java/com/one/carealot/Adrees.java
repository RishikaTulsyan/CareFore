package com.one.carealot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class Adrees extends AppCompatActivity {
    EditText name,num,addr;
    Button fin;
    StorageReference mStorageRef;
    DatabaseReference derrf;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    Adreess data;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adrees);

        name=findViewById(R.id.Name_u);
        num=findViewById(R.id.num);
        addr=findViewById(R.id.Adress);
        fin=findViewById(R.id.finish);
        data=new Adreess();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        derrf = FirebaseDatabase.getInstance().getReference().child("Address details").child(mCurrentUser.getUid());

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(Adrees.this,"Upload in progress",Toast.LENGTH_LONG).show();

                }else {
                    Fileuploder();
                    Intent Intentcart = new Intent(Adrees.this,Mainpage.class);
                    startActivity(Intentcart);
                    Toast.makeText(Adrees.this, "Your data is submit", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }

    private void Fileuploder() {
        data.setName(name.getText().toString().trim());
        data.setNumber(num.getText().toString().trim());
        data.setAdress(addr.getText().toString().trim());

        derrf.push().setValue(data);
    }

    public static class Adreess {

        private  String name;
        private String number;
        private String Adress ;


        public Adreess() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAdress() {
            return Adress;
        }

        public void setAdress(String adress) {
            Adress = adress;
        }
    }
}

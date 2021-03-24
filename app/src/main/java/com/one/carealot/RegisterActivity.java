package com.one.carealot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText reg_email,reg_pass,reg_confirm_pass;
    private Button reg_btn,reg_login_btn;
    private ProgressBar reg_progress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();

        Toolbar setupToolbar = findViewById(R.id.Registeration);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Customer Registeration");

        reg_email=findViewById(R.id.reg_email);
        reg_pass=findViewById(R.id.reg_pass);
        reg_confirm_pass=findViewById(R.id.reg_confirm_pass);
        reg_btn=findViewById(R.id.reg_btn);
        reg_login_btn=findViewById(R.id.reg_login_btn);
        reg_progress=findViewById(R.id.regprogrerss);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =reg_email.getText().toString();
                String pass= reg_pass.getText().toString();
                String confirm_pass = reg_confirm_pass.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)){

                    if (pass.equals(confirm_pass)){

                        reg_progress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    Intent setupintent = new Intent(RegisterActivity.this,SetupActivity.class);
                                    startActivity(setupintent);
                                    finish();



                                }
                                else {
                                    String errorMassage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this,"Error: "+errorMassage,Toast.LENGTH_LONG).show();

                                }

                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });

                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"Confirm Password and Password Fiewld doesn't match",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent logintent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(logintent);
                finish();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this,Mainpage.class);
        startActivity(mainIntent);
        finish();
    }
}

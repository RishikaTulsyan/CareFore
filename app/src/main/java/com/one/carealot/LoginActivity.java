package com.one.carealot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText, loginPassText,ngologinEmailText, ngologinPassText;
    private Button loginBtn, loginRegBtn,ngologinBtn, ngologinRegBtn;

    private FirebaseAuth mAuth;
    String username , password;

    private ProgressBar loginProgress, ngologinProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TabHost tabs=(TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec= tabs.newTabSpec("tab1");
        spec.setContent(R.id.Customer);
        spec.setIndicator("Customer");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("tab2");
        spec.setContent(R.id.NGO);
        spec.setIndicator("NGO's");
        tabs.addTab(spec);

        mAuth = FirebaseAuth.getInstance();


        loginEmailText = findViewById(R.id.login_email);
        loginPassText = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        loginRegBtn = findViewById(R.id.login_reg_btn);
        loginProgress = findViewById(R.id.loginprogrerss);
        ngologinEmailText=findViewById(R.id.user_email);
        ngologinPassText=findViewById(R.id.ngo_password);
        ngologinBtn = findViewById(R.id.ngo_login_btn);
        ngologinRegBtn = findViewById(R.id.ngo_login_reg_btn);
        ngologinProgress = findViewById(R.id.ngologinprogrerss);


        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(regIntent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = loginEmailText.getText().toString();
                String loginPass = loginPassText.getText().toString();

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                    ngologinProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sendToMain();


                            } else {

                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();

                            }

                            ngologinProgress.setVisibility(View.INVISIBLE);
                        }


                    });
                }

            }
        });

        ngologinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = ngologinEmailText.getText().toString();
                String loginPass = ngologinPassText.getText().toString();

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                    loginProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sendtongomain();


                            } else {

                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();

                            }

                            loginProgress.setVisibility(View.INVISIBLE);
                        }


                    });
                }
            }
        });

        ngologinRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent egIntent = new Intent(LoginActivity.this,NGO_registeration.class);
                startActivity(egIntent);
            }
        });
    }

    private void sendtongomain() {
        Intent nmainIntent = new Intent(LoginActivity.this,Ngo_mainpage.class);
        startActivity(nmainIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            sendToMain();
        }

    }

    private void sendToMain() {

        Intent mainIntent = new Intent(LoginActivity.this,Mainpage.class);
        startActivity(mainIntent);
        finish();
    }


}


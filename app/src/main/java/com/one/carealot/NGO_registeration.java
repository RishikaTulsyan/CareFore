package com.one.carealot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NGO_registeration extends AppCompatActivity {
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_g_o_registeration);
        email =findViewById(R.id.Emali);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        Toolbar setupToolbar = findViewById(R.id.ngoReg);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Registeration");

    }

    private void sendMail() {

        String re = "nishitulsyan6@gmail.com";

        Intent ints = new Intent(Intent.ACTION_SEND);
        ints.putExtra(Intent.EXTRA_EMAIL,re);
        ints.setType("message/rfc822");
        startActivity(Intent.createChooser(ints,"Choose an email client"));
    }

}

package com.one.carealot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Contact_us extends Fragment {
    private TextView num , email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.contact_us,container,false);

        num = view.findViewById(R.id.Pnum);
        email =view.findViewById(R.id.Ema);


        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:8511885649"));
                startActivity(in);

            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });


       return view;
    }

    private void sendMail() {

        String re = "nishitulsyan6@gmail.com";

        Intent inte = new Intent(Intent.ACTION_SEND);
        inte.putExtra(Intent.EXTRA_EMAIL,re);
        inte.setType("message/rfc822");
        startActivity(Intent.createChooser(inte,"Choose an email client"));
    }


}

package com.one.carealot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Donate extends Fragment {

    private Button money , cloths,food,stationary,medication,others;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.donate,container,false);
        money=view.findViewById(R.id.Money);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mon =  new Intent(getActivity(),Payment.class);
                startActivity(Mon);

            }
        });


        cloths=view.findViewById(R.id.Cloths);
        cloths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mn =  new Intent(getActivity(),cloths.class);
                startActivity(Mn);

            }
        });



        stationary=view.findViewById(R.id.stationary);
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent M =  new Intent(getActivity(),stationary.class);
                startActivity(M);

            }
        });

        medication=view.findViewById(R.id.medication);
        medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mm =  new Intent(getActivity(),Medication.class);
                startActivity(Mm);

            }
        });

        others=view.findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mo =  new Intent(getActivity(),Others.class);
                startActivity(Mo);

            }
        });

        return view;



    }
}

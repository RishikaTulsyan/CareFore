package com.one.carealot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class NGOvloueenters extends Fragment {
    StorageReference NGOStorageRef;
    Button Submit;
    private StorageTask uploadTask;
    EditText Category, Discription;
    NGO data;
    DatabaseReference derf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ngovolunteer, container, false);
        Submit = view.findViewById(R.id.submit);
        Category =view.findViewById(R.id.category);
        Discription =view.findViewById(R.id.Discription);
        data=new NGO();
        derf = FirebaseDatabase.getInstance().getReference().child("Medication");

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Fileuploder();
                }

        });
        return  view;


    }

    private void Fileuploder() {


        data.setCategory(Category.getText().toString().trim());
        data.setDiscription(Discription.getText().toString().trim());

        derf.push().setValue(data);
    }

    public static class NGO {
        private String Category;
        private String Discription;

        public NGO() {
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public String getDiscription() {
            return Discription;
        }

        public void setDiscription(String discription) {
            Discription = discription;
        }
    }


}
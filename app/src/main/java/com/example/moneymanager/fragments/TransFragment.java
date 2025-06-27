package com.example.moneymanager.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.AddActivity;


public class TransFragment extends Fragment {
    ImageView btnAdd;


    public TransFragment() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransFragment.this, AddActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.trans_fragment, container, false);
    }


    private void initView(View view) {
        btnAdd = view.findViewById(R.id.btnAdd);
    }






    }
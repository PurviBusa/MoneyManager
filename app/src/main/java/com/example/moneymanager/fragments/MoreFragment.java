package com.example.moneymanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.ConfigurationActivity;

public class MoreFragment extends Fragment {

    private TextView configuration;
    private CardView cardViewConfi;
    


    public MoreFragment() {
        
        

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more, container, false);


        cardViewConfi = view.findViewById(R.id.cardViewconfi);
        configuration = view.findViewById(R.id.configuration);


        cardViewConfi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ConfigurationActivity.class);
            startActivity(intent);

            
        });
        
        

        return view;
    }


}
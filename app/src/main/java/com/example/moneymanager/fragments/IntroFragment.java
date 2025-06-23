package com.example.moneymanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanager.R;


public class IntroFragment extends Fragment {

    private ImageView imgChatImage,imgChatImage_2,imgChatImage_3;
    private TextView headrTxt,subTxt ,headrTxt_2,headrTxt_3,subTxt_2,subTxt_3;


    public IntroFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setValue(0);

    }

    private void setValue(int pos) {

        String txt1,txt2 = "0";
        int img;
        if (pos==0) {

            txt1 = "Earn Smartly";
            txt2 = "Explore different income streams tailed for you.";
            img = R.drawable.first_intro;

        }else if (pos==1){
            txt1 = "Track & Save";
            txt2 = "Monitor your earning &amp; set savings goals.";
            img = R.drawable.third_intro;
        }else{
            txt1 = "Grow Your Wealth";
            txt2 = "Learn &amp; invest weekly to maximize your income.";
            img = R.drawable.second_intro;
        }
        headrTxt.setText(txt1);
        subTxt.setText(txt2);
        imgChatImage.setImageResource(img);
        headrTxt_2.setText(txt1);
        subTxt_2.setText(txt2);
        imgChatImage_2.setImageResource(img);
        headrTxt_3.setText(txt1);
        subTxt_3.setText(txt2);
        imgChatImage_3.setImageResource(img);
    }

    private void initView(View view) {
        headrTxt = view.findViewById(R.id.headrTxt);
        subTxt = view.findViewById(R.id.subTxt);
        imgChatImage = view.findViewById(R.id.imgChatImage);
        headrTxt_2 = view.findViewById(R.id.headrTxt_2);
        imgChatImage_2 = view.findViewById(R.id.imgChatImage);
        subTxt_2 =view.findViewById(R.id.subTxt_2);
        headrTxt_3 = view.findViewById(R.id.headrTxt_3);
        subTxt_3 = view.findViewById(R.id.subTxt_3);
        imgChatImage_3 = view.findViewById(R.id.imgChatImage_3);




    }
}
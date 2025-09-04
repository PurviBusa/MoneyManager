package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.TextView;

import com.example.moneymanager.R;

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);


        ImageView backArrow_confi;
        TextView incomeCategory;

        backArrow_confi = findViewById(R.id.backArrow_confi);
        incomeCategory = findViewById(R.id.incomeCategory);


        backArrow_confi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        incomeCategory .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConfigurationActivity.this,IncomeCategoryActivity.class);
                startActivity(intent);
            }
        });

    }
}
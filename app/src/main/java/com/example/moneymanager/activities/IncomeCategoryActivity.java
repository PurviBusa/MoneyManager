package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.database.MoneyManagerDatabase;
import com.example.moneymanager.R;

public class IncomeCategoryActivity extends AppCompatActivity {

    ImageView income_plus, back_income , img_currency;
    MoneyManagerDatabase databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_category);

        databaseHelper = MoneyManagerDatabase.getDB(this);

        income_plus = findViewById(R.id.income_plus);
        back_income = findViewById(R.id.back_income);



        income_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(IncomeCategoryActivity.this,IncomeAdd.class);
             startActivity(intent);
            }
        });

        back_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





    }
}
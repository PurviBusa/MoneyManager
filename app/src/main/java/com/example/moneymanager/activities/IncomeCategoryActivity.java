package com.example.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.IncomeCategoryAdapter;
import com.example.moneymanager.database.MoneyManagerDatabase;
import com.example.moneymanager.entity.MoneyManager;

import java.util.ArrayList;
import java.util.List;

public class IncomeCategoryActivity extends AppCompatActivity {

    private static final int ADD_CATEGORY_REQUEST = 1;

    ImageView income_plus, back_income;
    RecyclerView incomeRecycler;
    MoneyManagerDatabase databaseHelper;
    IncomeCategoryAdapter adapter;
    List<MoneyManager> incomeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_category);

        databaseHelper = MoneyManagerDatabase.getDB(this);

        income_plus = findViewById(R.id.income_plus);
        back_income = findViewById(R.id.back_income);
        incomeRecycler = findViewById(R.id.income_recycler);


        incomeRecycler.setLayoutManager(new LinearLayoutManager(this));
        incomeList = databaseHelper.moneyManagerDao().getAll();

        adapter = new IncomeCategoryAdapter(this, incomeList);
        incomeRecycler.setAdapter(adapter);


        income_plus.setOnClickListener(v -> {
            Intent intent = new Intent(IncomeCategoryActivity.this, IncomeAdd.class);
            startActivityForResult(intent, ADD_CATEGORY_REQUEST);
        });


        back_income.setOnClickListener(v -> onBackPressed());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            incomeList = databaseHelper.moneyManagerDao().getAll();
            adapter.updateList(incomeList);
        }
    }
}

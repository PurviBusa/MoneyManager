package com.example.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.database.MoneyManagerDatabase;
import com.example.moneymanager.entity.MoneyManager;

public class IncomeAdd extends AppCompatActivity {

    EditText categoryET;
    TextView tvSave,sub_category;
    ImageView back_incomeCategory;
    MoneyManagerDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomeadd_category);


        categoryET = findViewById(R.id.categoryET);
        tvSave = findViewById(R.id.tvSave);
        back_incomeCategory = findViewById(R.id.back_incomeCategory);
        sub_category = findViewById(R.id.sub_category);
        database = MoneyManagerDatabase.getDB(this);

        sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeAdd.this,IncomeSubCategory.class);
                startActivity(intent);
            }
        });
        back_incomeCategory.setOnClickListener(v -> onBackPressed());


        tvSave.setOnClickListener(v -> {
            String categoryName = categoryET.getText().toString().trim();

            if (categoryName.isEmpty()) {
                Toast.makeText(IncomeAdd.this, "Please enter category name", Toast.LENGTH_SHORT).show();
                return;
            }
            int maxPosition = database.moneyManagerDao().getMaxPosition();

            MoneyManager moneyManager = new MoneyManager(categoryName);
            moneyManager.setPosition(maxPosition + 1);


            database.moneyManagerDao().insert(moneyManager);

            Toast.makeText(IncomeAdd.this, "Category saved", Toast.LENGTH_SHORT).show();



            setResult(RESULT_OK);
            finish();
        });
    }
}

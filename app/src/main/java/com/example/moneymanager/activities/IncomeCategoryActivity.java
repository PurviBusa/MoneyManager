package com.example.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private boolean dataChanged = false;

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


        adapter.setOnCategoryClickListener(new IncomeCategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(MoneyManager category) { }

            @Override
            public void onCategoryDeleted(MoneyManager category) {
                databaseHelper.moneyManagerDao().delete(category);
                Toast.makeText(IncomeCategoryActivity.this, "Deleted: " + category.getCategoryname(), Toast.LENGTH_SHORT).show();

                incomeList = databaseHelper.moneyManagerDao().getAll();
                adapter.updateList(incomeList);
                dataChanged = true;
            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                new Thread(() -> {
                    List<MoneyManager> updatedList = adapter.getCurrentList();
                    for (int i = 0; i < updatedList.size(); i++) {
                        MoneyManager item = updatedList.get(i);
                        item.setPosition(i);
                        databaseHelper.moneyManagerDao().updatePosition(item.getId(), i);
                    }
                }).start();
                dataChanged = true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(incomeRecycler);
        adapter.setItemTouchHelper(itemTouchHelper);


        income_plus.setOnClickListener(v -> {
            adapter.closeDeleteIfOpen();
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
            dataChanged = true;
        }
    }
    @Override
    public void onBackPressed() {
        if(dataChanged){
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}

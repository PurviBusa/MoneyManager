package com.example.moneymanager.activities;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.content.Intent;

import com.example.moneymanager.entity.Transaction;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Class.CustomKeyboard;
import com.example.moneymanager.R;
import com.example.moneymanager.adapter.AccountAdapter;
import com.example.moneymanager.adapter.CategoryRecyclerAdapter;
import com.example.moneymanager.adapter.SubCategoryAdapter;
import com.example.moneymanager.customs.keyboardListner;
import com.example.moneymanager.database.MoneyManagerDatabase;
import com.example.moneymanager.entity.MoneyManager;
import com.example.moneymanager.models.AccountItem;
import com.example.moneymanager.models.CategoryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements keyboardListner {

    private static final String TAG = "AddActivity";

    TextView income_button, expense_button, total_button, current_date, current_time, txt_amount,
            income_category, income_account, save_button;
    EditText income_note;
    int colorTheme;
    LinearLayout category_item, account_item;
    ImageView img_backarrow, img_more, img_edit;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    MoneyManagerDatabase databaseHelper;

    GridLayout gride_custom;
    View amount_view, date_view, category_view, account_view, note_view;
    private static CustomKeyboard customKeyboard;

    RecyclerView categoryRecyclerView, account_recycler, subcategory_recycler;

    CategoryRecyclerAdapter categoryAdapter;
    SubCategoryAdapter subCategoryAdapter;

    AccountAdapter accountAdapter;
    ArrayList<CategoryItem> categoryList;
    ArrayList<AccountItem> accountList;
    private static final int CATEGORY_REQUEST_CODE = 100;

    private String currentType = "income";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        colorTheme = R.color.blue;
        databaseHelper = MoneyManagerDatabase.getDB(this);
        initViews();
        loadCategoriesFromDatabase();
        setupCategoryRecycler();
        setupAmountRecycler();
        setupListeners();
        setupDateTime();


        category_item.setVisibility(GONE);
        account_item.setVisibility(GONE);

        txt_amount.requestFocus();
        setButtonSelection("income");
        showCustomKeyboard();


    }


    private void initViews() {
        income_button = findViewById(R.id.income_button);
        expense_button = findViewById(R.id.expense_button);
        total_button = findViewById(R.id.total_button);
        img_backarrow = findViewById(R.id.img_backarrow);
        current_date = findViewById(R.id.current_date);
        current_time = findViewById(R.id.current_time);
        txt_amount = findViewById(R.id.txt_amount);
        income_category = findViewById(R.id.income_category);
        income_account = findViewById(R.id.income_account);
        income_note = findViewById(R.id.income_note);
        gride_custom = findViewById(R.id.gride_custom);
        category_item = findViewById(R.id.category_item);
        customKeyboard = findViewById(R.id.custom_key);
        img_more = findViewById(R.id.img_more);
        amount_view = findViewById(R.id.amount_view);
        date_view = findViewById(R.id.date_view);
        category_view = findViewById(R.id.category_view);
        account_view = findViewById(R.id.account_view);
        note_view = findViewById(R.id.note_view);
        account_item = findViewById(R.id.account_item);
        save_button = findViewById(R.id.save_button);
        img_edit = findViewById(R.id.img_edit);
        account_recycler = findViewById(R.id.account_recycler);


        txt_amount.setShowSoftInputOnFocus(false);
        income_category.setShowSoftInputOnFocus(false);
        income_account.setShowSoftInputOnFocus(false);

        txt_amount.setRawInputType(InputType.TYPE_CLASS_TEXT);
        txt_amount.setCursorVisible(false);
        income_category.setRawInputType(InputType.TYPE_CLASS_TEXT);
        income_category.setCursorVisible(false);
        income_account.setRawInputType(InputType.TYPE_CLASS_TEXT);
        income_account.setCursorVisible(false);

        txt_amount.setTextIsSelectable(true);
        income_category.setTextIsSelectable(true);
        income_account.setTextIsSelectable(true);

    }


    private void setupAmountRecycler() {
        accountList = new ArrayList<>();
        accountList.add(new AccountItem("Cash"));
        accountList.add(new AccountItem("Account"));
        accountList.add(new AccountItem("Card"));
        accountList.add(new AccountItem("Debit Card"));

        accountAdapter = new AccountAdapter(accountList);
        accountAdapter.setOnAccountClickListener(accountItem -> {
            income_account.setText(accountItem.getAccountName());

            income_account.clearFocus();
            income_note.requestFocus();

            account_item.setVisibility(GONE);
            txt_amount.setVisibility(VISIBLE);


        });
        account_recycler.setLayoutManager(new GridLayoutManager(this, 3));
        account_recycler.setAdapter(accountAdapter);

    }


    private void setupCategoryRecycler() {
        categoryRecyclerView = findViewById(R.id.category_recycler);
        subcategory_recycler = findViewById(R.id.subcategory_recycler);
        categoryList = new ArrayList<>();

        loadCategoriesFromDatabase();

        categoryAdapter = new CategoryRecyclerAdapter(categoryList);
        subCategoryAdapter = new SubCategoryAdapter(new ArrayList<>());


        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(categoryAdapter);

        subcategory_recycler.setLayoutManager(new LinearLayoutManager(this));
        subcategory_recycler.setAdapter(subCategoryAdapter);

        categoryAdapter.setOnCategoryClickListener(category -> {
            selectedCategoryName = category.getCategoryName();


            if (category.getSubCategories() == null || category.getSubCategories().isEmpty()) {

                income_category.setText(selectedCategoryName);
                income_category.setCursorVisible(false);


                category_item.setVisibility(GONE);
                account_item.setVisibility(VISIBLE);

                category_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
                account_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));

                income_account.requestFocus();
            } else {

                subCategoryAdapter.updateList(category.getSubCategories());
            }
        });

        subCategoryAdapter.setOnSubCategoryClickListener(sub -> {
            income_category.setText(selectedCategoryName + " / " + sub);
            income_category.setCursorVisible(false);
            category_item.setVisibility(GONE);
            account_item.setVisibility(VISIBLE);

            category_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            account_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));

            income_account.requestFocus();
        });


    }


    private void loadCategoriesFromDatabase() {
//           categoryList.clear();

        categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("Salary", Arrays.asList("Monthly", "Bonus", "Incentives")));
        categoryList.add(new CategoryItem("Petty Cash", Arrays.asList("Office", "Personal")));
        categoryList.add(new CategoryItem("Bonus", Arrays.asList("Festival", "Performance")));
        categoryList.add(new CategoryItem("Allowance", Arrays.asList("Travel", "Food", "Other")));

        List<MoneyManager> dbCategories = databaseHelper.moneyManagerDao().getAll();
        for (MoneyManager dbCategory : dbCategories) {

            categoryList.add(new CategoryItem(dbCategory.getCategoryname(), new ArrayList<>()));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategoriesFromDatabase();
        setupCategoryRecycler();
    }

    public String selectedCategoryName = "";

    private void setupListeners() {
        img_backarrow.setOnClickListener(v -> onBackPressed());

        customKeyboard.setListener(this);


        txt_amount.setOnFocusChangeListener((v, hasFocus) -> {


            if (hasFocus) {
                gride_custom.setVisibility(VISIBLE);
                category_item.setVisibility(GONE);
                account_item.setVisibility(GONE);
                amount_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
                showCustomKeyboard();


            } else {
                amount_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
                hideCustomKeyboard();

            }
        });


        income_note.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideCustomKeyboard();
                gride_custom.setVisibility(GONE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(income_note, InputMethodManager.SHOW_IMPLICIT);

                category_item.setVisibility(GONE);
                account_item.setVisibility(GONE);
                note_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            } else {
                note_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            }
        });



        save_button.setOnClickListener(v -> {
            String amountText = txt_amount.getText().toString()
                    .replace("£", "")
                    .replace(" ", "")
                    .trim();

            if (amountText.isEmpty() || amountText.equals("-")) {
                txt_amount.setError("Enter amount");
                return;
            }

            String cleanAmount = amountText.replace("-", "");

            double amount;
            try {
                amount = Double.parseDouble(cleanAmount);
            } catch (NumberFormatException e) {
                txt_amount.setError("Invalid amount format");
                Log.e(TAG, "Invalid amount: " + amountText, e);
                return;
            }

            Transaction tx = new Transaction();
            tx.setType(currentType);
            tx.setAmount(amount);
            tx.setCategory(income_category.getText().toString());
            tx.setAccount(income_account.getText().toString());
            tx.setNote(income_note.getText().toString());

            String dateOnly = simpleDateFormat.format(calendar.getTime());
            tx.setDate(dateOnly);

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            String timeOnly = timeFormat.format(calendar.getTime());
            tx.setTime(timeOnly);


            new Thread(() -> {
                databaseHelper.transactionDao().insert(tx);

                // VERIFY - Check if it was actually saved
                List<Transaction> savedTransactions = databaseHelper.transactionDao().getByDate(dateOnly);
                Log.d(TAG, "Verification: Found " + savedTransactions.size() + " transactions for date: " + dateOnly);

                runOnUiThread(() -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data_saved", true);
                    resultIntent.putExtra("saved_date", dateOnly);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                });
            }).start();
        });
        img_more.setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, IncomeCategoryActivity.class);
            startActivityForResult(intent, CATEGORY_REQUEST_CODE);
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihome = new Intent(AddActivity.this, AccountSetting.class);
                startActivity(ihome);
            }
        });

        income_category.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(income_category.getWindowToken(), 0);
            }
            gride_custom.setVisibility(GONE);
            account_item.setVisibility(GONE);
            category_item.setVisibility(VISIBLE);
            category_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
        });


        income_category.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(income_category.getWindowToken(), 0);
                }
                gride_custom.setVisibility(GONE);
                account_item.setVisibility(GONE);
                category_item.setVisibility(VISIBLE);
                category_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            } else {
                category_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            }
        });

        income_account.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(income_account.getWindowToken(), 0);
                }
                gride_custom.setVisibility(GONE);
                category_item.setVisibility(GONE);
                account_item.setVisibility(VISIBLE);
                account_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            } else {
                account_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            }
        });


        income_button.setOnClickListener(v -> setButtonSelection("income"));
        expense_button.setOnClickListener(v -> setButtonSelection("expense"));
        total_button.setOnClickListener(v -> setButtonSelection("total"));
    }

    private void setupDateTime() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        // CRITICAL: Use dd/MM/yyyy format to match database
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        simpleDateFormat2 = new SimpleDateFormat("EEE", Locale.ENGLISH);

        // Log to verify current date
        Log.d(TAG, "Current date from calendar: " + simpleDateFormat.format(calendar.getTime()));

        updateDateTime();

        current_date.setOnClickListener(v -> {
            date_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            openDatePicker();
        });

        current_time.setOnClickListener(v -> {
            date_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
            openTimePicker();
        });
    }

    private void setButtonSelection(String type) {
        currentType = type;
        switch (type) {
            case "income":
                income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.incomebutton));
                expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                save_button.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                colorTheme = R.color.blue;
                txt_amount.clearFocus();
                income_note.clearFocus();
                income_category.clearFocus();
                income_account.clearFocus();
                txt_amount.requestFocus();
                break;
            case "expense":
                income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.expense_selector));
                total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
                total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                save_button.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.orange));
                colorTheme = R.color.orange;
                txt_amount.clearFocus();
                income_note.clearFocus();
                income_category.clearFocus();
                income_account.clearFocus();
                txt_amount.requestFocus();
                break;
            case "total":
                income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_selected));
                income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.darkgray));
                save_button.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.darkgray));
                colorTheme = R.color.darkgray;
                txt_amount.clearFocus();
                income_note.clearFocus();
                income_category.clearFocus();
                income_account.clearFocus();
                txt_amount.requestFocus();
                break;
        }
    }

    private void openDatePicker() {
        if (calendar == null) calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTime();
            txt_amount.requestFocus();
        }, YEAR, MONTH, DATE);

        datePickerDialog.setOnDismissListener(dialog ->
                date_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
        );

        datePickerDialog.show();
    }

    private void openTimePicker() {
        if (calendar == null) calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            updateDateTime();
            txt_amount.requestFocus();
        }, HOUR, MINUTE, false);

        timePickerDialog.setOnDismissListener(dialog ->
                date_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
        );

        timePickerDialog.show();
    }
    public void showCustomKeyboard() {
        customKeyboard.setVisibility(VISIBLE);
        hideSystemKeyboard();
    }

    public void hideCustomKeyboard() {
        customKeyboard.setVisibility(GONE);
    }

    public void hideSystemKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(customKeyboard.getWindowToken(), 0);
        }
    }

    @Override
    public void setValue(String value) {
        String currentText = txt_amount.getText().toString().replace("£", "").trim();
        boolean isNegative = false;
        String numberPart = currentText;

        if (currentText.startsWith("-")) {
            isNegative = true;
            numberPart = currentText.substring(1);
        }

        switch (value) {
            case "DEL":
                if (!numberPart.isEmpty())
                    numberPart = numberPart.substring(0, numberPart.length() - 1);
                if (numberPart.isEmpty()) isNegative = false;
                break;
            case "-":
                isNegative = !isNegative;
                break;

            case "DONE":

                txt_amount.clearFocus();
                income_category.requestFocus();
                return;

            default:
                numberPart += value;
                break;
        }

        if (numberPart.isEmpty()) txt_amount.setText("");
        else txt_amount.setText("£  " + (isNegative ? "-" : "") + numberPart);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {

            loadCategoriesFromDatabase();
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override

    public void onBackPressed() {
        if (customKeyboard.getVisibility() == VISIBLE || category_item.getVisibility() == VISIBLE || account_item.getVisibility() == VISIBLE) {
            customKeyboard.setVisibility(GONE);
            category_item.setVisibility(GONE);
            account_item.setVisibility(GONE);

            getWindow().getDecorView().clearFocus();
        } else {
            super.onBackPressed();
        }
    }

    private void updateDateTime() {
        String inputDate = simpleDateFormat.format(calendar.getTime());
        String day = simpleDateFormat2.format(calendar.getTime());
        current_date.setText(inputDate + " (" + day + ")");

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        current_time.setText(timeFormat.format(calendar.getTime()));

        Log.d(TAG, "Updated DateTime - Date: " + inputDate + ", Time: " + timeFormat.format(calendar.getTime()));
    }

}
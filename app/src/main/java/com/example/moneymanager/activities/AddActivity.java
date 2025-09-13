package com.example.moneymanager.activities;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import com.example.moneymanager.customs.keyboardListner;
import com.example.moneymanager.models.AccountItem;
import com.example.moneymanager.models.CategoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements keyboardListner {

    private static final String TAG = "AddActivity";

    TextView income_button, expense_button, total_button, current_date, current_time, txt_amount,
            income_category, income_account, save_button;
    EditText income_note;
    int colorTheme;
    LinearLayout category_item, account_item;
    ImageView img_backarrow, img_more;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    GridLayout gride_custom;
    View amount_view, date_view, category_view, account_view, note_view;
    private static CustomKeyboard customKeyboard;

    RecyclerView categoryRecyclerView,account_recycler;
    ConstraintLayout category_main;
    CategoryRecyclerAdapter categoryAdapter;

    AccountAdapter accountAdapter;
    ArrayList<CategoryItem> categoryList;
    ArrayList<AccountItem> accountList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        colorTheme = R.color.blue;
        initViews();
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
        categoryRecyclerView = findViewById(R.id.category_recycler);
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
            gride_custom.setVisibility(GONE);
            category_item.setVisibility(GONE);


        });
        account_recycler.setLayoutManager(new GridLayoutManager(this,3));
        account_recycler.setAdapter(accountAdapter);

    }


    private void setupCategoryRecycler() {
        categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("Salary"));
        categoryList.add(new CategoryItem("Petty Cash"));
        categoryList.add(new CategoryItem("Bonus"));
        categoryList.add(new CategoryItem("Award"));
        categoryList.add(new CategoryItem("Allowance"));

        categoryAdapter = new CategoryRecyclerAdapter(categoryList);
        categoryAdapter.setOnCategoryClickListener(categoryItem -> {

            income_category.setText(categoryItem.getCategoryName());
            category_item.setVisibility(GONE);
            account_item.setVisibility(GONE);
        });

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

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
                category_item.setVisibility(GONE);
                account_item.setVisibility(GONE);
                note_view.setBackgroundColor(ContextCompat.getColor(this, colorTheme));
                hideCustomKeyboard();
            } else {
                note_view.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            }
        });

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, IncomeCategoryActivity.class);
                startActivity(intent);
            }
        });

        income_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View view = income_category;
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    gride_custom.setVisibility(GONE);
                    account_item.setVisibility(GONE);
                    category_item.setVisibility(VISIBLE);
                    category_view.setBackgroundColor(ContextCompat.getColor(AddActivity.this, colorTheme));

                } else {
                    category_view.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.lightGray));
                }
            }
        });
        income_account.setOnFocusChangeListener((v, hasFocus) -> {

                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View view = income_account;
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        if (calendar == null) calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        simpleDateFormat2 = new SimpleDateFormat("EEE", Locale.getDefault());

        String inputDate = simpleDateFormat.format(calendar.getTime());
        String day = simpleDateFormat2.format(new Date());
        current_date.setText(inputDate + " (" + day + ")");

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        current_time.setText(timeFormat.format(calendar.getTime()));

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
        switch (type) {
            case "income":
                income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.incomebutton));
                expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                save_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
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
                save_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
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
                save_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.darkgray));
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
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat(" dd/MM/yyyy (EEE)", Locale.getDefault());
            current_date.setText(dateFormat.format(selectedDate.getTime()));
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

            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedTime.set(Calendar.MINUTE, minute);


            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String formattedTime = timeFormat.format(selectedTime.getTime());

            current_time.setText(formattedTime);
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
            default:
                numberPart += value;
                break;
        }

        if (numberPart.isEmpty()) txt_amount.setText("");
        else txt_amount.setText("£  " + (isNegative ? "-" : "") + numberPart);
    }

    @Override

    public void onBackPressed() {
        if (customKeyboard.getVisibility() == VISIBLE || category_item.getVisibility() == VISIBLE) {
            customKeyboard.setVisibility(GONE);
            category_item.setVisibility(GONE);


            getWindow().getDecorView().clearFocus();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (customKeyboard.getVisibility() == VISIBLE) {
//                Rect outRect = new Rect();
//                customKeyboard.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
//                    customKeyboard.setVisibility(GONE);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


}
package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Class.CustomKeyboard;
import com.example.moneymanager.Class.DatabaseHelper;
import com.example.moneymanager.R;
import com.example.moneymanager.adapter.CategoryRecyclerAdapter;
import com.example.moneymanager.customs.keyboardListner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements keyboardListner {

    private static final String TAG = "AddActivity";
    TextView income_button, expense_button, total_button, current_date, current_time, txt_amount,
            income_category, income_account;
    EditText income_note;
    ImageView img_backarrow;
    Calendar calendar;
    LinearLayout LinerLL;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    View date_view, amount_view, category_view, account_view, note_view;
    DatabaseHelper databaseHelper;
    GridLayout gride_custom;

    RecyclerView category_recycler;

    CategoryRecyclerAdapter adpter;
    private static CustomKeyboard customKeyboard;

    String inputDate = "";
    Date date = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Salary");
        categoryList.add("Petty Cash");
        categoryList.add("Bonus");
        categoryList.add("Award");
        categoryList.add("Allownce");


//      setupFocusListeners();
        customKeyboard = findViewById(R.id.custom_key);
//        Log.d("", "customKeyboard::::::::::::::" + customKeyboard);
        income_button = findViewById(R.id.income_button);
        expense_button = findViewById(R.id.expense_button);
        total_button = findViewById(R.id.total_button);
        img_backarrow = findViewById(R.id.img_backarrow);
        current_date = findViewById(R.id.current_date);
        current_time = findViewById(R.id.current_time);
        txt_amount = findViewById(R.id.txt_amount);
        img_backarrow = findViewById(R.id.img_backarrow);
        date_view = findViewById(R.id.date_view);
        current_date = findViewById(R.id.current_date);
        income_category = findViewById(R.id.income_category);
        income_account = findViewById(R.id.income_account);
        income_note = findViewById(R.id.income_note);
        amount_view = findViewById(R.id.account_view);
        category_view = findViewById(R.id.category_view);
        account_view = findViewById(R.id.account_view);
        note_view = findViewById(R.id.note_view);
        gride_custom = findViewById(R.id.gride_custom);
        category_recycler = findViewById(R.id.category_recycler);


//        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);


        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        txt_amount.setShowSoftInputOnFocus(false);
        income_category.setShowSoftInputOnFocus(false);
        income_account.setShowSoftInputOnFocus(false);


        income_note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideCustomKeyboard();
                }
            }
        });


        customKeyboard.setListener(this);

        txt_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    category_recycler.setVisibility(View.GONE);
                    gride_custom.setVisibility(View.VISIBLE);
                    showCustomKeyboard();

                } else
                    hideCustomKeyboard();


            }
        });

        income_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


//                Log.d(TAG, "onFocusChange: " + hasFocus);

                if (hasFocus) {
                    gride_custom.setVisibility(View.GONE);
                    category_recycler.setVisibility(View.VISIBLE);
                    showCustomKeyboard();

                } else
                    hideCustomKeyboard();

            }
        });

        income_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


//                Log.d(TAG, "onFocusChange: " + hasFocus);

                if (hasFocus) {
                    showCustomKeyboard();

                } else
                    hideCustomKeyboard();
            }
        });
        txt_amount.setRawInputType(InputType.TYPE_CLASS_TEXT);
        income_category.setRawInputType(InputType.TYPE_CLASS_TEXT);
        income_account.setRawInputType(InputType.TYPE_CLASS_TEXT);
        txt_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customKeyboard.setVisibility(View.VISIBLE);
                category_recycler.setVisibility(View.GONE);
            }
        });


        txt_amount.setTextIsSelectable(true);
        income_category.setTextIsSelectable(true);
        income_account.setTextIsSelectable(true);


        if (calendar == null)
            calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            simpleDateFormat2 = new SimpleDateFormat("EEE", Locale.getDefault());
            inputDate = simpleDateFormat.format(calendar.getTime());
            String day = simpleDateFormat2.format(new Date());

            current_date.setText(inputDate + " (" + day + ")");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat("hh:mm a");
                inputDate = simpleDateFormat.format(calendar.getTime());
            }
            current_time.setText(inputDate);

            current_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDatePicker();


                }
            });

            current_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTimePicker();
                }
            });
            img_backarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            income_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.incomebutton));
                    expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
            });

            expense_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.expense_selector));
                    total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
                    total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));

                }
            });
            total_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    income_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    expense_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_expence));
                    total_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.total_selected));
                    income_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    expense_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    total_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.darkgray));
                }
            });


        }


    }

    private void openTimePicker() {

        if (calendar == null)
            calendar = Calendar.getInstance();

        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                current_time.setText(hourOfDay + ":" + minute);

            }


        }, HOUR, MINUTE, false);
        timePickerDialog.show();
    }

    private void openDatePicker() {
        if (calendar == null)
            calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month - 1, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat(" dd/MM/yyyy (EEE)", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());

                current_date.setText(formattedDate);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    public void showCustomKeyboard() {
        customKeyboard.setVisibility(View.VISIBLE);
        hideSystemKeyboard();
    }

    public void hideCustomKeyboard() {

        customKeyboard.setVisibility(View.GONE);
        Log.d(TAG, "hideCustomKeyboard: ");
    }

    public void hideSystemKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(customKeyboard.getWindowToken(), 0);
        }

    }


    @Override

    public void setValue(String value) {
        Log.d("TAG", "setValue: " + value);

        // Remove formatting characters
        String currentText = txt_amount.getText().toString().replace("£", "").trim();


        boolean isNegative = false;
        String numberPart;

        // Check for negative sign
        if (currentText.startsWith("-")) {
            isNegative = true;
            numberPart = currentText.substring(1);
        } else {
            numberPart = currentText;
        }

        switch (value) {
            case "DEL":
                if (!numberPart.isEmpty()) {
                    numberPart = numberPart.substring(0, numberPart.length() - 1);
                }
                if (numberPart.isEmpty()) {
                    isNegative = false; // clear minus when number is empty
                }
                break;

            case "-":
                isNegative = !isNegative;
                break;

            default:
                numberPart += value;
                break;
        }

        // Set final formatted text
        if (numberPart.isEmpty()) {
            txt_amount.setText(""); // show nothing
        } else {
            String finalText = "£  " + (isNegative ? "-" : "") + numberPart;
            txt_amount.setText(finalText); // 💥 OUTPUT WILL BE LIKE: £  -123
        }
    }
//    public void setValue(String value) {
//
//
//        Log.d(TAG, "setValue: " + value);
//        String currentText = txt_amount.getText().toString();
//
//
//        String rawText = currentText.replace("£", "").replace(" ", "");
//        boolean isNegative = rawText.startsWith("-");
//        String numberPart = rawText.replace("-", "");
//
//        switch (value) {
//            case "DEL":
//                if (!numberPart.isEmpty()) {
//                    numberPart = numberPart.substring(0, numberPart.length() - 1);
//                } else {
//
//                    isNegative = false;
//                }
//                break;
//
//            case "-":
//                isNegative = !isNegative;
//                break;
//
//            default:
//                numberPart += value;
//                break;
//        }
//
//
//        if (numberPart.isEmpty()) {
//            txt_amount.setText("");
//        } else {
//            StringBuilder finalText = new StringBuilder("£  ");
//            if (isNegative) finalText.append("-");
//            finalText.append(numberPart);
//            txt_amount.setText(finalText.toString());
//
//
//        }
//

//        txt_amount.setText(txt_amount.getText().length());
//    }

    public void onBackPressed() {
        if (customKeyboard.getVisibility() == View.VISIBLE) {

            customKeyboard.setVisibility(View.GONE);
            getWindow().getDecorView().clearFocus();

        } else {
            super.onBackPressed();
        }


    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && customKeyboard.getVisibility() == View.VISIBLE) {
                Rect outRect = new Rect();
                customKeyboard.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    customKeyboard.setVisibility(View.GONE);
                }
            }
        }
        return super.dispatchTouchEvent(ev);


    }
}


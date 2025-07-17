package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moneymanager.R;
import com.example.moneymanager.customs.keyboardListner;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements keyboardListner {

    private static final String TAG = "AddActivity";
    TextView income_button, expense_button, total_button, current_date, current_time, txt_amount;
    ImageView img_backarrow;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    DatabaseHelper databaseHelper;

    private static CustomKeyboard customKeyboard;

    String inputDate = "";
    Date date = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        txt_amount.setShowSoftInputOnFocus(false);
        InputConnection inputConnection = txt_amount.onCreateInputConnection(new EditorInfo());
        customKeyboard.setListener(this);
        txt_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Log.d(TAG, "onFocusChange: "+hasFocus);

                if (hasFocus) {

//                    customKeyboard.setVisibility(View.VISIBLE);
//
                    showCustomKeyboard();

                } else
                    hideCustomKeyboard();
//                    customKeyboard.setVisibility(View.GONE);
            }
        });
        txt_amount.setRawInputType(InputType.TYPE_CLASS_TEXT);
        txt_amount.setTextIsSelectable(true);


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
            imm.hideSoftInputFromWindow(txt_amount.getWindowToken(), 0);
        }

    }


    @Override
    public void setValue(String value) {
//        Log.d(TAG, "setValue: " + value);
//        String currentText = txt_amount.getText().toString();
//
//        if (!currentText.startsWith("£")) {
//            currentText = "£  " + currentText;
//            txt_amount.setText(currentText);
//        }
//
//        switch (value) {
//            case "DEL":
//
//                if (!currentText.isEmpty()) {
//                    currentText = currentText.substring(0, currentText.length() - 1);
//                    txt_amount.setText(currentText);
//                }
//                break;
//
//            case "-":
//
//                if (!currentText.contains("-")) {
//                    txt_amount.setText("£  -" + currentText.substring(2));
////                    txt_amount.setText(value + txt_amount.getText().toString());
//
//                }
//                break;
//
//
//            default:
//
//                txt_amount.append(value);
//                break;
//        }

        Log.d(TAG, "setValue: " + value);
        String currentText = txt_amount.getText().toString();


        if (!currentText.startsWith("$ ")) {
            currentText = "$ ";
            txt_amount.setText(currentText);
        }

        switch (value) {
            case "DEL":
                if (currentText.length() > 2) {
                    String updated = currentText.substring(0, currentText.length() - 1);
                    txt_amount.setText(updated);
                }

                break;

            case "-":
                if (!currentText.contains("-")) {
                    String numberPart = currentText.substring(2);
                    txt_amount.setText("$ -" + numberPart);
                }
                break;

            default:

                String insertText = currentText;

                if (currentText.contains("-")) {
                    insertText = "$ -" + currentText.substring(3) + value;
                } else {
                    insertText = "$ " + currentText.substring(2) + value;
                }

                txt_amount.setText(insertText);
                break;
        }


        txt_amount.setText(txt_amount.getText().toString());
    }

        }


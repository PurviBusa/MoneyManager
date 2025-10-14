package com.example.moneymanager.Class;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.customs.keyboardListner;

import java.util.ArrayList;

public class CustomKeyboard extends RelativeLayout implements View.OnClickListener {
    InputConnection inputConnection;


    TextView txt_amount;
    private TextView txt_1, txt_2, txt_3, txt_4, txt_5, txt_6, txt_7, txt_8, txt_9, txt_0, txt_dot, txt_minus, txt_done;
    private ImageView img_backSpace, img_calculator, img_currency, img_close;


    private keyboardListner listner;


    public CustomKeyboard(Context context, keyboardListner listner) {
        super(context);
        initialization(context);
        this.listner = listner;
    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialization(context);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization(context);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialization(context);

    }

    private void initialization(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_keyboard, this, true);
        txt_1 = findViewById(R.id.txt_1);
        txt_1.setOnClickListener(this);
        txt_2 = findViewById(R.id.txt_2);
        txt_2.setOnClickListener(this);
        txt_3 = findViewById(R.id.txt_3);
        txt_3.setOnClickListener(this);
        txt_4 = findViewById(R.id.txt_4);
        txt_4.setOnClickListener(this);
        txt_5 = findViewById(R.id.txt_5);
        txt_5.setOnClickListener(this);
        txt_6 = findViewById(R.id.txt_6);
        txt_6.setOnClickListener(this);
        txt_7 = findViewById(R.id.txt_7);
        txt_7.setOnClickListener(this);
        txt_8 = findViewById(R.id.txt_8);
        txt_8.setOnClickListener(this);
        txt_9 = findViewById(R.id.txt_9);
        txt_9.setOnClickListener(this);
        txt_0 = findViewById(R.id.txt_0);
        txt_0.setOnClickListener(this);
        txt_minus = findViewById(R.id.txt_minus);
        txt_minus.setOnClickListener(this);
        txt_done = findViewById(R.id.txt_done);
        txt_done.setOnClickListener(this);
        txt_dot = findViewById(R.id.txt_dot);
        txt_dot.setOnClickListener(this);
        img_backSpace = findViewById(R.id.img_backSpace);
        img_backSpace.setOnClickListener(this);
        img_calculator = findViewById(R.id.img_calculator);
        img_calculator.setOnClickListener(this);
        img_currency = findViewById(R.id.img_currency);
        img_currency.setOnClickListener(this);
        img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
        txt_amount = findViewById(R.id.txt_amount);


    }

    @Override
    public void onClick(View view) {
        if (listner == null) return;

        int id = view.getId();

        if (id == R.id.txt_minus) {
            listner.setValue("-");
        } else if (id == R.id.img_backSpace) {
            listner.setValue("DEL");
        } else if (id == R.id.txt_done) {
            listner.setValue("DONE");
        } else if (id == R.id.txt_dot || id == R.id.txt_0 || id == R.id.txt_1 ||
                id == R.id.txt_2 || id == R.id.txt_3 || id == R.id.txt_4 ||
                id == R.id.txt_5 || id == R.id.txt_6 || id == R.id.txt_7 ||
                id == R.id.txt_8 || id == R.id.txt_9) {

            String value = ((TextView) view).getText().toString();
            listner.setValue(value);
        }
    }

    public void setListener(keyboardListner listner) {
        this.listner = listner;
    }


}

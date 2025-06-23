package com.example.moneymanager.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.moneymanager.R;
import com.example.moneymanager.models.ScreenItem;
import com.example.moneymanager.customs.ViewPagerAdpter;

import java.util.ArrayList;
import java.util.List;

public class IntroScreen extends AppCompatActivity {

    private

    ImageView firstArrow, dot_1, dot_2, dot_3;
    private ViewPager viewPage;
    TextView txtGotIt;
    ViewPagerAdpter viewPagerAdpter;
//    TabLayout tabIndicator;


    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (restorePrefData()) {
            Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_intro_screen);

        initView();

        dot1();

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Earn Smartly", "Explore different income streams tailed for you.", R.drawable.first_intro));
        mList.add(new ScreenItem("Track & Save", "Monitor your earning &amp; set savings goals.", R.drawable.second_intro));
        mList.add(new ScreenItem("Grow Your Wealth", "Learn &amp; invest weekly to maximize your income.", R.drawable.third_intro));


        viewPagerAdpter = new ViewPagerAdpter(this, mList);

        viewPage.setAdapter(viewPagerAdpter);


        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    firstArrow.setImageResource(R.drawable.second_arrow);
                    loadLastScreen(1);
                    dot2();
                } else if (position == 0) {
                    firstArrow.setImageResource(R.drawable.first_arrow);
                    loadLastScreen(1);
                    dot1();
                } else {
                    loadLastScreen(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        firstArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPage.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    viewPage.setCurrentItem(position);

                }
                if (position == mList.size() - 1) {
                    loadLastScreen(0);

                }


            }
        });


        txtGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
                savePrefsData();

//                finish();

            }
        });

    }

    private void dot1() {
        dot_1.setImageResource(R.drawable.gray_dot_1);
        dot_2.setImageResource(R.drawable.gray_dot);
    }

    private void dot2() {
        dot_1.setImageResource(R.drawable.gray_dot);
        dot_2.setImageResource(R.drawable.gray_dot_2);
    }

    private void initView() {
        firstArrow = findViewById(R.id.firstArrow);
        dot_1 = findViewById(R.id.dot_1);
        dot_2 = findViewById(R.id.dot_2);
        viewPage = findViewById(R.id.viewPage);
        txtGotIt = findViewById(R.id.txtGotIt);
        viewPage = findViewById(R.id.viewPage);

    }


    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;
    }

    private boolean savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();

        return true;
    }

    private void loadLastScreen(int i) {
        if (i == 0) {
            firstArrow.setVisibility(View.GONE);
            txtGotIt.setVisibility(View.VISIBLE);
        }else{
            firstArrow.setVisibility(View.VISIBLE);
            txtGotIt.setVisibility(View.GONE);
        }


    }


}
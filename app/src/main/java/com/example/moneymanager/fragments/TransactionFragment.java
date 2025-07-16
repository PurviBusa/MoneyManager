package com.example.moneymanager.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.AddActivity;
import com.example.moneymanager.adapter.TransViewPagerAdpter;
import com.example.moneymanager.adapter.ViewPagerAdpter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TransactionFragment extends Fragment {

    private LinearLayout todoLL;
    private View saperater;
    ImageView btnAdd,backArrow,next_arrow;
    TextView daily_trans, calender_trans, monthly_trans, total_trans, note_trans,currentDate;
    private LinearLayout linear_daily, linear_calender, linear_monthly, linear_total, linear_note;
    private ViewPager2 trans_viewPager;
    private FragmentStateAdapter adapter;
    Calendar calendar;

    View indicator_daily, indicator_calender, indicator_monthly, indicator_total, indicator_note;


    public TransactionFragment() {

    }


    private final List<Fragment> fragmentList = Arrays.asList(
            new DailyFragment(),
            new CalenderFragment(),
            new MonthlyFragment(),
            new TotalFragment(),
            new NoteFragment()

    );


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        initViews(view);
        setViewPager();

        calendar = Calendar.getInstance();
        updateDate();


        btnAdd = view.findViewById(R.id.btnAdd);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                updateDate();


            }
        });

        next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                updateDate();


            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);


            }
        });

        trans_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateSelectedButton(position);
            }
        });
        updateSelectedButton(0);


        return view;


    }


    private void setViewPager() {
        linear_daily.setOnClickListener(v -> trans_viewPager.setCurrentItem(0, false));
        updateSelectedButton(0);
        linear_calender.setOnClickListener(v -> trans_viewPager.setCurrentItem(1, false));
        updateSelectedButton(1);
        linear_monthly.setOnClickListener(v -> trans_viewPager.setCurrentItem(2, false));
        updateSelectedButton(2);
        linear_total.setOnClickListener(v -> trans_viewPager.setCurrentItem(3, false));
        updateSelectedButton(3);
        linear_note.setOnClickListener(v -> trans_viewPager.setCurrentItem(4, false));
        updateSelectedButton(4);


        adapter = new ScreenSlidePagerAdapter(this);
        trans_viewPager.setAdapter(adapter);
        trans_viewPager.setUserInputEnabled(false);
    }



    private void initViews(View view) {

        daily_trans = view.findViewById(R.id.daily_trans);
        calender_trans = view.findViewById(R.id.calender_trans);
        monthly_trans = view.findViewById(R.id.monthly_trans);
        total_trans = view.findViewById(R.id.total_trans);
        note_trans = view.findViewById(R.id.note_trans);
        indicator_daily = view.findViewById(R.id.indicator_daily);
        indicator_calender = view.findViewById(R.id.indicator_calender);
        indicator_monthly = view.findViewById(R.id.indicator_monthly);
        indicator_total = view.findViewById(R.id.indicator_total);
        indicator_note = view.findViewById(R.id.indicator_note);
        trans_viewPager = view.findViewById(R.id.trans_viewPager);
        todoLL = view.findViewById(R.id.todoLL);
        saperater = view.findViewById(R.id.saperater);
        linear_daily = view.findViewById(R.id.linear_daily);
        linear_calender = view.findViewById(R.id.linear_calender);
        linear_monthly = view.findViewById(R.id.linear_monthly);
        linear_total = view.findViewById(R.id.linear_total);
        linear_note = view.findViewById(R.id.linear_note);
        currentDate = view.findViewById(R.id.currentDate);
        next_arrow = view.findViewById(R.id.next_arrow);
        backArrow = view.findViewById(R.id.backArrow);



    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    private void updateSelectedButton(int index) {
        int selectedColor = ContextCompat.getColor(requireContext(), R.color.black);
        int defaultColor = ContextCompat.getColor(requireContext(), R.color.gray);

        daily_trans.setTextColor(index == 0 ? selectedColor : defaultColor);
        calender_trans.setTextColor(index == 1 ? selectedColor : defaultColor);
        monthly_trans.setTextColor(index == 2 ? selectedColor : defaultColor);
        total_trans.setTextColor(index == 3 ? selectedColor : defaultColor);
        note_trans.setTextColor(index == 4 ? selectedColor : defaultColor);

        indicator_daily.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        indicator_calender.setVisibility(index == 1 ? View.VISIBLE : View.GONE);
        indicator_monthly.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
        indicator_total.setVisibility(index == 3 ? View.VISIBLE : View.GONE);
        indicator_note.setVisibility(index == 4 ? View.VISIBLE : View.GONE);

        if (index == 4) {
            todoLL.setVisibility(View.GONE);
            saperater.setVisibility(View.GONE);
        } else {
            todoLL.setVisibility(View.VISIBLE);
            saperater.setVisibility(View.VISIBLE);
        }

    }
    void updateDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        String formattedDate = sdf.format(calendar.getTime()).toUpperCase();
        currentDate.setText(formattedDate);
    }


}



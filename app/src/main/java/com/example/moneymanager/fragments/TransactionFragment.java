package com.example.moneymanager.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.AddActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class TransactionFragment extends Fragment implements DailyFragment.OnDailyPageChangeListener {

    private LinearLayout todoLL;
    private View saperater;
    DailyFragment dailyFragment;
    int currentPage = 0;


    ImageView btnAdd, backArrow, next_arrow;
    TextView daily_trans, calender_trans, monthly_trans, total_trans, note_trans, currentDate;

    private LinearLayout linear_daily, linear_calender, linear_monthly, linear_total, linear_note;
    private ViewPager2 trans_viewPager;

    private Calendar calendar;

    ViewPager daily_viewpager;

    View indicator_daily, indicator_calender, indicator_monthly, indicator_total, indicator_note;

    public TransactionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        initViews(view);
        setViewPager();
        updateMonthYear(0);

        dailyFragment = new DailyFragment();
        dailyFragment.setDailyPageChangeListener(this);


        calendar = Calendar.getInstance();
        updateDate();

        currentDate.setOnClickListener(v -> showCustomPopup());

        backArrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateDate();
        });

        next_arrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateDate();
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
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
        backArrow = view.findViewById(R.id.backArrow);
        next_arrow = view.findViewById(R.id.next_arrow);
        btnAdd = view.findViewById(R.id.btnAdd);
        daily_viewpager = view.findViewById(R.id.daily_viewpager);
    }


    private void setViewPager() {

        linear_daily.setOnClickListener(v -> trans_viewPager.setCurrentItem(0, false));
        linear_calender.setOnClickListener(v -> trans_viewPager.setCurrentItem(1, false));
        linear_monthly.setOnClickListener(v -> trans_viewPager.setCurrentItem(2, false));
        linear_total.setOnClickListener(v -> trans_viewPager.setCurrentItem(3, false));
        linear_note.setOnClickListener(v -> trans_viewPager.setCurrentItem(4, false));

        trans_viewPager.setAdapter(new ScreenSlidePagerAdapter(this));
        trans_viewPager.setUserInputEnabled(false);
    }



    @Override
    public void onDailyPageChanged(int position) {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, position);
        updateDate();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, position);
        String formatted = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).format(cal.getTime());
//        String monthYear = new SimpleDateFormat("MMM yyyy").format(cal.getTime());
        currentDate.setText(formatted.toUpperCase());
    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Fragment f = Arrays.asList(
                    new DailyFragment(),
                    new CalendarFragment(),
                    new MonthlyFragment(),
                    new TotalFragment(),
                    new NoteFragment()
            ).get(position);

            if (f instanceof DailyFragment) {
                ((DailyFragment) f).setDailyPageChangeListener(TransactionFragment.this);
            }

            return f;
        }

        @Override
        public int getItemCount() {
            return 5;
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


    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        currentDate.setText(sdf.format(calendar.getTime()).toUpperCase());
    }


    private void showCustomPopup() {

        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_menu, null);

        int width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 310, getResources().getDisplayMetrics());

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setOutsideTouchable(true);

        popupWindow.showAsDropDown(currentDate, -20, 10);

        TextView[] months = new TextView[]{
                popupView.findViewById(R.id.tvJan),
                popupView.findViewById(R.id.tvFeb),
                popupView.findViewById(R.id.tvMar),
                popupView.findViewById(R.id.tvApr),
                popupView.findViewById(R.id.tvMay),
                popupView.findViewById(R.id.tvJun),
                popupView.findViewById(R.id.tvJul),
                popupView.findViewById(R.id.tvAug),
                popupView.findViewById(R.id.tvSept),
                popupView.findViewById(R.id.tvOct),
                popupView.findViewById(R.id.tvNov),
                popupView.findViewById(R.id.tvDec)
        };

        TextView popupYear = popupView.findViewById(R.id.year);
        ImageView backArrow = popupView.findViewById(R.id.back_arrow_year);
        ImageView forwardArrow = popupView.findViewById(R.id.forwrd_arrow_year);
        ImageView closePopup = popupView.findViewById(R.id.closePopup);

        int colorRed = ContextCompat.getColor(requireContext(), R.color.red);
        int colorDefault = ContextCompat.getColor(requireContext(), R.color.black);

        final Calendar tempCalendar = (Calendar) calendar.clone();
        popupYear.setText(String.valueOf(tempCalendar.get(Calendar.YEAR)));

        for (int i = 0; i < months.length; i++) {

            months[i].setTextColor(i == tempCalendar.get(Calendar.MONTH) ? colorRed : colorDefault);

            final int monthIndex = i;
            months[i].setOnClickListener(v -> {

                tempCalendar.set(Calendar.MONTH, monthIndex);

                for (int j = 0; j < months.length; j++) {
                    months[j].setTextColor(j == monthIndex ? colorRed : colorDefault);
                }

                calendar.set(Calendar.MONTH, monthIndex);
                updateDate();
                popupWindow.dismiss();
            });
        }

        backArrow.setOnClickListener(v -> {
            tempCalendar.add(Calendar.YEAR, -1);
            popupYear.setText(String.valueOf(tempCalendar.get(Calendar.YEAR)));
        });

        forwardArrow.setOnClickListener(v -> {
            tempCalendar.add(Calendar.YEAR, 1);
            popupYear.setText(String.valueOf(tempCalendar.get(Calendar.YEAR)));
        });

        closePopup.setOnClickListener(v -> popupWindow.dismiss());
    }

    private void updateMonthYear(int dayOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dayOffset);

        String formatted = new SimpleDateFormat("MMM yyyy").format(cal.getTime());
        currentDate.setText(formatted);
    }
}

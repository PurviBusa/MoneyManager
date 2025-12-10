package com.example.moneymanager.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private GridView calendarGrid;
    private Calendar calendar;
    private CalendarAdapter adapter;
    private GestureDetector gestureDetector;

    public CalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarGrid = view.findViewById(R.id.calendarGrid);
        calendar = Calendar.getInstance();

        setupCalendar();
        setupGridHeight();

        gestureDetector = new GestureDetector(getContext(), new SwipeGestureListener());

        calendarGrid.setOnTouchListener((v, event) -> {
            ViewPager viewPager = findParentViewPager(v);
            if (viewPager != null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_MOVE) {
                    viewPager.requestDisallowInterceptTouchEvent(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP ||
                        event.getAction() == MotionEvent.ACTION_CANCEL) {
                    viewPager.requestDisallowInterceptTouchEvent(false);
                }
            }

            boolean handled = gestureDetector.onTouchEvent(event);

            if (event.getAction() == MotionEvent.ACTION_DOWN) return true;
            return gestureDetector.onTouchEvent(event);

        });

        return view;
    }

    private ViewPager findParentViewPager(View v) {
        View parent = (View) v.getParent();
        while (parent != null) {
            if (parent instanceof ViewPager) {
                return (ViewPager) parent;
            }
            if (parent.getParent() instanceof View) {
                parent = (View) parent.getParent();
            } else {
                parent = null;
            }
        }
        return null;
    }

    private void setupCalendar() {
        ArrayList<Date> dates = new ArrayList<>();
        Calendar cal = (Calendar) calendar.clone();

        cal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        cal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);

        while (dates.size() < 42) {
            dates.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        adapter = new CalendarAdapter(getContext(), dates, calendar);
        calendarGrid.setAdapter(adapter);
    }

    private void setupGridHeight() {
        calendarGrid.post(() -> {
            ViewGroup.LayoutParams params = calendarGrid.getLayoutParams();
            int numRows = 6;
            int availableHeight = calendarGrid.getHeight();
            if (availableHeight > 0) {
                int cellHeight = availableHeight / numRows;
                if (adapter != null) {
                    adapter.setCellHeight(cellHeight);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 100;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) return false;
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();

            if (Math.abs(deltaY) > Math.abs(deltaX)) {
                return false;
            }

            if (Math.abs(deltaX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (deltaX > 0) {
                    calendar.add(Calendar.MONTH, -1);
                } else {
                    calendar.add(Calendar.MONTH, 1);
                }

                setupCalendar();
                setupGridHeight();
                return true;
            }
            return false;
        }

    }
}

package com.example.moneymanager.fragments;

import static android.app.Activity.RESULT_OK;
import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.AddActivity;
import com.example.moneymanager.database.MoneyManagerDatabase;

public class DailyFragment extends Fragment {

    private MoneyManagerDatabase database;
    private OnDailyPageChangeListener listener;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;

    private static final int REQUEST_CODE_ADD = 1;
    private static final int TOTAL_PAGES = 31;
    private static final int CENTER = TOTAL_PAGES / 2;

    public interface OnDailyPageChangeListener {
        void onDailyPageChanged(int position);
    }

    public void setDailyPageChangeListener(OnDailyPageChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            if (viewPager != null && adapter != null) {
                int currentPosition = viewPager.getCurrentItem();
                adapter.notifyDataSetChanged();

                refreshAllVisibleFragments();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        database = MoneyManagerDatabase.getDB(getContext());
        viewPager = view.findViewById(R.id.daily_viewpager);

        adapter = new FragmentStatePagerAdapter(
                getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @NonNull
            @Override
            public Fragment getItem(int position) {
                int offset = position - CENTER;
                return DailyItemFragment.newInstance(offset);
            }

            @Override
            public int getCount() {
                return TOTAL_PAGES;
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(CENTER, false);

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (listener != null) {
                    int offset = position - CENTER;
                    listener.onDailyPageChanged(offset);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAllVisibleFragments();
    }

    private void refreshAllVisibleFragments() {
        if (adapter != null && viewPager != null && getActivity() != null) {
            int currentPosition = viewPager.getCurrentItem();

            for (int i = currentPosition - 2; i <= currentPosition + 2; i++) {
                if (i >= 0 && i < TOTAL_PAGES) {
                    Fragment fragment = getChildFragmentManager()
                            .findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + i);

                    if (fragment instanceof DailyItemFragment && fragment.isAdded()) {
                        ((DailyItemFragment) fragment).refreshData();
                    }
                }
            }
        }
    }

    public void refreshData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        refreshAllVisibleFragments();
    }

    public void refreshAllPages() {
        if (adapter != null && viewPager != null) {
            int currentPosition = viewPager.getCurrentItem();

            adapter = new FragmentStatePagerAdapter(
                    getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

                @NonNull
                @Override
                public Fragment getItem(int position) {
                    int offset = position - CENTER;
                    return DailyItemFragment.newInstance(offset);
                }

                @Override
                public int getCount() {
                    return TOTAL_PAGES;
                }

                @Override
                public int getItemPosition(@NonNull Object object) {
                    return POSITION_NONE;
                }
            };

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(currentPosition, false);
        }
    }
}
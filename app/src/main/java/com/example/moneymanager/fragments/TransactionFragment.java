package com.example.moneymanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.moneymanager.R;
import com.example.moneymanager.activities.AddActivity;
import com.example.moneymanager.adapter.TransViewPagerAdpter;
import com.example.moneymanager.adapter.ViewPagerAdpter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class TransactionFragment extends Fragment {
    public TransactionFragment() {

    }

    ImageView btnAdd;
    TabLayout tabLayout;
    TextView daily_trans;
    private BottomNavigationView navView;
    private NavController navController;

    private ViewPager trans_viewPager;
    private TransViewPagerAdpter transViewPagerAdpter;


    private final int[] tabDestination = {
            R.id.navigation_app_daily,
            R.id.navigation_app_calender,
            R.id.navigation_app_monthly,
            R.id.navigation_app_total,
            R.id.navigation_app_note,

    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        TextView daily_trans = view.findViewById(R.id.daily_trans);
        TextView calender_trans = view.findViewById(R.id.calender_trans);
        TextView monthly_trans = view.findViewById(R.id.monthly_trans);
//        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_activity_home);
        daily_trans.setOnClickListener(v -> navController .navigate(tabDestination[0]));
        calender_trans.setOnClickListener(v -> navController .navigate(tabDestination[1]));
        monthly_trans.setOnClickListener(v -> navController .navigate(tabDestination[2]));
        trans_viewPager = view.findViewById(R.id.trans_viewPager);



        btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);


            }
        });

//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                int position = tab.getPosition();
////                if (position >= 0 && position < tabDestination.length) {
////                    navController.navigate(tabDestination[position]);
////
////                }
//            }
//
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });


        return view;


    }

//    private void addTabs (ViewPager viewPager){
//
//        ViewPagerAdpter adpter =new ViewPagerAdpter()
//
//    }


}
package com.example.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.moneymanager.R;
import com.example.moneymanager.fragments.DailyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

public class Mainactivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private BottomNavigationView navView;
    private NavController navController;
    private static final int REQUEST_CODE_ADD = 1;
//    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.bottomNavigation);
        navController = getNavigationController();
        navView.setItemIconTintList(null);

        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(this);
//        toolbar = findViewById(R.id.toolbar);

    }

    public NavController getNavigationController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_activity_home);
        return navHostFragment.getNavController();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = getNavigationController();
        boolean b = navController.navigateUp() || super.onSupportNavigateUp();
        return b;
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
        updateToolBarIconAndTitle(navDestination, bundle);
    }

    private void updateToolBarIconAndTitle(@NonNull NavDestination destination, @Nullable Bundle arguments) {

    }

    public void openAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            new android.os.Handler().postDelayed(() -> {
                refreshDailyFragment();
            }, 100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning to MainActivity
        new android.os.Handler().postDelayed(() -> {
            refreshDailyFragment();
        }, 100);
    }
    private void refreshDailyFragment() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_activity_home);

        if (navHostFragment != null) {
            List<Fragment> fragments = navHostFragment.getChildFragmentManager().getFragments();

            for (Fragment fragment : fragments) {
                if (fragment instanceof DailyFragment && fragment.isVisible()) {
                    ((DailyFragment) fragment).refreshData();
                    break;
                }
            }
        }
    }

    private void refreshDailyFragmentByDestination() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_activity_home);

        if (navHostFragment != null) {
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();

            if (navController.getCurrentDestination() != null &&
                    navController.getCurrentDestination().getId() == R.id.navigation_app_transaction) {

                if (currentFragment instanceof DailyFragment) {
                    ((DailyFragment) currentFragment).refreshData();
                }
            }
        }
    }
}
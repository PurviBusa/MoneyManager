package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.moneymanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;

public class Mainactivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private BottomNavigationView navView;
    private NavController navController;
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
//        toolbar = findViewById(R.id.toolbar);
//        setToolbarTitle(destination.getLabel().toString());

//        float elevation = destination.getId() == R.id.navigation_app_notification_settings
//                ? getResources().getDimension(R.dimen.activity_margin_register)
//                : 0f;
//        toolbar.setElevation(elevation);

//        hideToolbarButton();
//
//        int statusBarColorRes = destination.getId() == R.id.navigation_app_edit_account
//                ? android.R.color.transparent
//                : android.R.color.white;
//
//        int statusBarColor = ContextCompat.getColor(this, statusBarColorRes);
//        gozemNavigation.setStatusBarColor(statusBarColor);

//        switch (destination.getId()) {
////            case R.id.navigation_app_transaction:
//                toolbar.setVisibility(View.VISIBLE);
//                navView.setVisibility(View.VISIBLE);
//                toolbar.setNavigationIcon(null);
//                break;
//
//            case R.id.navigation_app_stats:
//                toolbar.setVisibility(View.VISIBLE);
//                navView.setVisibility(View.VISIBLE);
//                toolbar.setNavigationIcon(null);
////                toolbar.setTitle(getString(R.string.app_menu_address_book));
//                break;
//
//            case R.id.navigation_app_account:
//                toolbar.setVisibility(View.VISIBLE);
//                navView.setVisibility(View.VISIBLE);
//                toolbar.setNavigationIcon(null);
//                break;
//
//            case R.id.navigation_app_more:
//                toolbar.setVisibility(View.VISIBLE);
//                navView.setVisibility(View.VISIBLE);
//                setToolbarTitle(getString(R.string.text_settings));
//                toolbar.setNavigationIcon(null);
//                break;
//
//        }
    }


//
}

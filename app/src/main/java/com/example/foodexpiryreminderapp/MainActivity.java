package com.example.foodexpiryreminderapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.foodexpiryreminderapp.Adapter.ViewPagerAdapter;
import com.example.foodexpiryreminderapp.Fragments.AddFoodFragment;
import com.example.foodexpiryreminderapp.Fragments.FoodListFragment;
import com.example.foodexpiryreminderapp.Helper.Constants;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.fxn.stash.Stash;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int PERMISSION_REQUEST_CODE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Constants.checkApp(MainActivity.this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        Stash.clearAll();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddFoodFragment(), "Add Food");
        adapter.addFragment(new FoodListFragment(), "Food List");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        initializeDefaultFoodItems();

        if (Build.VERSION.SDK_INT > 32) {
            if (!shouldShowRequestPermissionRationale("112")) {
                getNotificationPermission();
            }
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Permisssion denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }

    }
    private void initializeDefaultFoodItems() {
        ArrayList<FoodItem> foodItemArrayList = Stash.getArrayList(Constants.FOOD_LIST_KEY, FoodItem.class);

        if (foodItemArrayList == null || foodItemArrayList.isEmpty()) {
            foodItemArrayList = new ArrayList<>();

            foodItemArrayList.add(new FoodItem("Apples", "10/10/2024", Constants.getNewID(), false));
            foodItemArrayList.add(new FoodItem("Bread", "15/11/2024", Constants.getNewID(), false));
            foodItemArrayList.add(new FoodItem("Milk", "22/09/2024", Constants.getNewID(), false));

            Stash.put(Constants.FOOD_LIST_KEY, foodItemArrayList);
        }
    }
}

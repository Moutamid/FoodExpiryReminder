package com.example.foodexpiryreminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// MainActivity.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodexpiryreminderapp.Adapter.ViewPagerAdapter;
import com.example.foodexpiryreminderapp.Fragments.AddFoodFragment;
import com.example.foodexpiryreminderapp.Fragments.FoodListFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddFoodFragment(), "Add Food");
        adapter.addFragment(new FoodListFragment(), "Food List");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

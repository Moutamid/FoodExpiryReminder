package com.example.foodexpiryreminderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpiryreminderapp.Adapter.AllDenotedFoodAdapter;
import com.example.foodexpiryreminderapp.Helper.Constants;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.fxn.stash.Stash;

import java.util.ArrayList;
import java.util.List;

public class AllDonatedItemsActivity extends AppCompatActivity {
    RecyclerView content_rcv;
    List<FoodItem> foodItemArrayList;
    AllDenotedFoodAdapter allFoodAdapter;
    TextView no_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donated_items);
        content_rcv = findViewById(R.id.content_rcv);
        no_text = findViewById(R.id.no_text);
        content_rcv.setLayoutManager(new GridLayoutManager(AllDonatedItemsActivity.this, 1));

        // Load data when fragment becomes visible
        loadAndUpdateData();

    }

    private void loadAndUpdateData() {
        // Retrieve the list from Stash
        foodItemArrayList = Stash.getArrayList(Constants.FOOD_LIST_KEY, FoodItem.class);
        if (foodItemArrayList == null) {
            foodItemArrayList = new ArrayList<>();
        }

        // Filter the list where isDonated value is false
        List<FoodItem> filteredList = getNonDonatedItems(foodItemArrayList);

        // Update the adapter with the filtered data
        if (allFoodAdapter == null) {
            allFoodAdapter = new AllDenotedFoodAdapter(AllDonatedItemsActivity.this, filteredList);
            content_rcv.setAdapter(allFoodAdapter);
        } else {
            allFoodAdapter.setData(filteredList);
            allFoodAdapter.notifyDataSetChanged();
        }

        // Show/hide the "no data" text based on the filtered list
        if (filteredList.isEmpty()) {
            no_text.setVisibility(View.VISIBLE);
        } else {
            no_text.setVisibility(View.GONE);
        }
    }

    private List<FoodItem> getNonDonatedItems(List<FoodItem> itemList) {
        List<FoodItem> nonDonatedItems = new ArrayList<>();
        for (FoodItem item : itemList) {
            if (item.isDonated()) {
                nonDonatedItems.add(item);
            }
        }
        return nonDonatedItems;
    }


    public void back(View view) {
        finish();
    }
}
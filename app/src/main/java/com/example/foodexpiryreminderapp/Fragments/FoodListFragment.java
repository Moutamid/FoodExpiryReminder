package com.example.foodexpiryreminderapp.Fragments;
// FoodListFragment.java
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpiryreminderapp.Adapter.AllFoodAdapter;
import com.example.foodexpiryreminderapp.AllDonatedItemsActivity;
import com.example.foodexpiryreminderapp.Helper.Constants;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;
import com.fxn.stash.Stash;

import java.util.ArrayList;
import java.util.List;

public class FoodListFragment extends Fragment {

    RecyclerView content_rcv;
    List<FoodItem> foodItemArrayList;
    AllFoodAdapter allFoodAdapter;
    TextView no_text;
    TextView all_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        content_rcv = view.findViewById(R.id.content_rcv);
        no_text = view.findViewById(R.id.no_text);
        all_items = view.findViewById(R.id.all_items);
        content_rcv.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Load data when fragment becomes visible
        loadAndUpdateData();
        all_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AllDonatedItemsActivity.class));
            }
        });
        return view;
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
            allFoodAdapter = new AllFoodAdapter(getContext(), filteredList);
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
            if (!item.isDonated()) {
                nonDonatedItems.add(item);
            }
        }
        return nonDonatedItems;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("FragmentVisibility", "FoodListFragment is visible: " + isVisibleToUser);

        // Load data when the fragment becomes visible
        if (isVisibleToUser) {
            loadAndUpdateData();
        }
    }

}

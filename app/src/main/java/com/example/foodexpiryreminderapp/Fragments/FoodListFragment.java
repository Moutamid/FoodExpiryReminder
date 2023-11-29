package com.example.foodexpiryreminderapp.Fragments;
// FoodListFragment.java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodexpiryreminderapp.Helper.Config;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;
import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

// ... (other imports and code)

public class FoodListFragment extends Fragment {

    private ListView listViewFoodItems;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        listViewFoodItems = view.findViewById(R.id.listViewFoodItems);
        gson = new Gson();

        // Retrieve the JSON string from Stash
        String jsonString = Stash.getString(Config.FOOD_LIST_KEY);

        // Check if the JSON string is not null
        if (jsonString != null) {
            // Parse the JSON array using TypeToken<List<FoodItem>>
            TypeToken<List<FoodItem>> typeToken = new TypeToken<List<FoodItem>>() {};
            List<FoodItem> foodItemList = gson.fromJson(jsonString, typeToken.getType());

            // Display the food items in a ListView
            if (foodItemList != null) {
                List<String> foodItemStrings = new ArrayList<>();
                for (FoodItem foodItem : foodItemList) {
                    foodItemStrings.add(foodItem.getName() + " - " + foodItem.getExpiryDate());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, foodItemStrings);
                listViewFoodItems.setAdapter(adapter);
            }
        }

        return view;
    }

    // ... (other methods if any)
}

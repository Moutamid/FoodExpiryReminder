package com.example.foodexpiryreminderapp.Fragments;
// AddFoodFragment.java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import com.example.foodexpiryreminderapp.Helper.Config;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;
import com.fxn.stash.Stash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AddFoodFragment extends Fragment {

    private EditText editTextFoodName;
    private DatePicker datePickerExpiry;
    private Button buttonAddFood;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        editTextFoodName = view.findViewById(R.id.editTextFoodName);
        datePickerExpiry = view.findViewById(R.id.datePickerExpiry);
        buttonAddFood = view.findViewById(R.id.buttonAddFood);

        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input values
                String foodName = editTextFoodName.getText().toString();
                String expiryDate = getFormattedDate(datePickerExpiry);

                // Create a new FoodItem
                FoodItem foodItem = new FoodItem(foodName, expiryDate);

                // Add the FoodItem to the list using Stash
                Stash.put(Config.FOOD_LIST_KEY, foodItem);

                // Clear the input fields
                editTextFoodName.setText("");
            }
        });

        return view;
    }

    private String getFormattedDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is 0-based
        int year = datePicker.getYear();
        return day + "/" + month + "/" + year;
    }
}

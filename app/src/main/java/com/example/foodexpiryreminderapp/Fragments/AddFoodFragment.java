package com.example.foodexpiryreminderapp.Fragments;
// AddFoodFragment.java
// AddFoodFragment.java

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodexpiryreminderapp.Helper.Constants;
import com.example.foodexpiryreminderapp.MainActivity;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;
import com.example.foodexpiryreminderapp.notification.NotificationScheduler;
import com.fxn.stash.Stash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFoodFragment extends Fragment {

    private EditText editTextFoodName;
    private DatePicker datePickerExpiry;
    private Button buttonAddFood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

                FoodItem foodItem = new FoodItem(foodName, expiryDate,  Constants.getNewID(), false);
                // Retrieve existing list or create a new one
                ArrayList<FoodItem> foodItemArrayList = Stash.getArrayList(Constants.FOOD_LIST_KEY, FoodItem.class);
                if (foodItemArrayList == null) {
                    foodItemArrayList = new ArrayList<>();
                }
                foodItemArrayList.add(foodItem);
                Stash.put(Constants.FOOD_LIST_KEY, foodItemArrayList);
                editTextFoodName.setText("");
                setNotificationForOneMonthBeforeExpiry(foodName, expiryDate);
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
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

    private void setNotificationForOneMonthBeforeExpiry(String name, String expiryDate) {

        FoodItem reminderModel = new FoodItem();
        reminderModel.notificationID = Constants.getNewID();
        reminderModel.name = name;
        reminderModel.expiryDate = expiryDate;
        Calendar expiryCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date parsedExpiryDate = dateFormat.parse(expiryDate);
            expiryCalendar.setTime(parsedExpiryDate);

            // Set the notification date to one month before expiry
            expiryCalendar.add(Calendar.MONTH, -1);


            // Check if the notification time is in the past, if yes, schedule it for the next day
            if (Calendar.getInstance().after(expiryCalendar)) {
                expiryCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            // Schedule the notification
            String reminderMsg = "Please donate "+name+" food to \"Houston food bank\"";

            NotificationScheduler.scheduleNotification(
                    getContext(), expiryCalendar,
                    reminderMsg, Constants.MEDICINE_REMINDER);

            // Save the reminder to the list
            ArrayList<FoodItem> reminderModelArrayList =
                    Stash.getArrayList(Constants.REMINDERS_LIST, FoodItem.class);
            reminderModelArrayList.add(reminderModel);
            Stash.put(Constants.REMINDERS_LIST, reminderModelArrayList);

            Toast.makeText(getContext(), "Item is successfully saved", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing exception
        }
    }
}

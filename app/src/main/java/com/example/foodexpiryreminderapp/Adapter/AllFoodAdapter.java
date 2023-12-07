package com.example.foodexpiryreminderapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpiryreminderapp.Helper.Constants;
import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;
import com.fxn.stash.Stash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllFoodAdapter extends RecyclerView.Adapter<AllFoodAdapter.GalleryPhotosViewHolder> {

    private Context ctx;
    private List<FoodItem> productModels;

    public AllFoodAdapter(Context ctx, List<FoodItem> productModels) {
        this.ctx = ctx;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        FoodItem foodItem = productModels.get(position);
        holder.name.setText(foodItem.getName());
        holder.date.setText(foodItem.getExpiryDate());

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date expiryDate = dateFormat.parse(foodItem.getExpiryDate());
            Date currentDate = new Date();

            long diffInMillies = expiryDate.getTime() - currentDate.getTime();
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

            if (diffInDays < 0 ) {
                holder.date.setTextColor(Color.RED); // Item has expired and not donated
                holder.name.setTextColor(Color.RED); // Item has expired and not donated
            } else if (diffInDays <= 30) {
                holder.name.setTextColor(Color.parseColor("#FFA500")); // Less than 1 month to expiry
                holder.date.setTextColor(Color.parseColor("#FFA500")); // Less than 1 month to expiry
            } else {
                holder.date.setTextColor(Color.BLACK); // Default color for other cases
                holder.name.setTextColor(Color.BLACK); // Default color for other cases
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            holder.date.setTextColor(Color.BLACK); // Default color if parsing fails
            holder.name.setTextColor(Color.BLACK); // Default color if parsing fails
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodItem foodItem = productModels.get(position);

                // Display an alert dialog for confirmation
                showConfirmationDialog(foodItem, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView name, date;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
        }
    }

    public void setData(List<FoodItem> newData) {
        productModels.clear();
        productModels.addAll(newData);
        notifyDataSetChanged();  // Notify the adapter that the dataset has changed
    }
    private void showConfirmationDialog(final FoodItem item, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Have you donated this item?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the isDonated value of the item
                item.setDonated(true);

                // Update the item in Stash
                updateInStash(item);
                productModels.remove(position);
                notifyItemRemoved(position);
                // Add the item to the donated items list

                // Refresh the UI if needed
                notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updateInStash(FoodItem updatedItem) {
        // Retrieve the existing list from Stash
        List<FoodItem> foodItems = Stash.getArrayList(Constants.FOOD_LIST_KEY, FoodItem.class);
        if (foodItems != null) {
            // Find the item to update in the list
            for (int i = 0; i < foodItems.size(); i++) {
                FoodItem item = foodItems.get(i);
                if (item.getNotificationID()==(updatedItem.getNotificationID())) {// Assuming item has an ID for identification
                    // Update the isDonated value
                    item.setDonated(updatedItem.isDonated());

                    // Save the updated list in Stash
                    Stash.put(Constants.FOOD_LIST_KEY, foodItems);
                    break;
                }
            }
        }
    }

}

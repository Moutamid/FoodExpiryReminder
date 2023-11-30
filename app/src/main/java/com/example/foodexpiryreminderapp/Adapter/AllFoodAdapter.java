package com.example.foodexpiryreminderapp.Adapter;
// AllFoodAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpiryreminderapp.Model.FoodItem;
import com.example.foodexpiryreminderapp.R;

import java.util.List;

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

}

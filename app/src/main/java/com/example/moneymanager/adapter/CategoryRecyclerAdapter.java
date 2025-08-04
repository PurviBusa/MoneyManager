package com.example.moneymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private List<CategoryItem> categories;

    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryItem category);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    public CategoryRecyclerAdapter(ArrayList<CategoryItem> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryItem category = categories.get(position);
        holder.tvCategory.setText(category.getCategoryName());
        holder.ivNext.setVisibility(View.GONE);
        if (!category.getSubCategory().isEmpty()) {
            holder.ivNext.setVisibility(View.VISIBLE);
        }
        holder.clMain.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category); // Or pass the data object
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        ImageView ivNext;
        ConstraintLayout clMain;

        ViewHolder(View view) {
            super(view);
            tvCategory = view.findViewById(R.id.tvCategory);
            ivNext = view.findViewById(R.id.ivNext);
            clMain = view.findViewById(R.id.clMain);
        }
    }
}

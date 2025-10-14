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

    public CategoryRecyclerAdapter(List<CategoryItem> categories) {
        this.categories = (categories != null) ? categories : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem category = categories.get(position);

        String name = (category.getCategoryName() != null) ? category.getCategoryName() : "";
        holder.tvCategory.setText(name);

        holder.ivNext.setVisibility(name.isEmpty() ? View.GONE : View.VISIBLE);

        holder.clMain.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (categories != null) ? categories.size() : 0;
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

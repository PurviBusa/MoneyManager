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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private CategoryItem categories;
    private OnSubCategoryClickListener listener;

    public interface OnSubCategoryClickListener {
        void onSubCategoryClick(CategoryItem category,String subCategory);
    }

    public void setOnSubCategoryClickListener(OnSubCategoryClickListener listener) {
        this.listener = listener;
    }

    public SubCategoryAdapter(CategoryItem categories) {
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
        String subCategory = categories.getSubCategory().get(position);
        holder.tvCategory.setText(subCategory);
        holder.ivNext.setVisibility(View.GONE);
        holder.clMain.setOnClickListener(v -> {
            if (listener != null) {

                listener.onSubCategoryClick(categories,subCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.getSubCategory().size();
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


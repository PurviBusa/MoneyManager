package com.example.moneymanager.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.CategoryRecyclerAdapter;
import com.example.moneymanager.adapter.SubCategoryAdapter;
import com.example.moneymanager.models.CategoryItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CategorySelectionDialog extends BottomSheetDialogFragment implements CategoryRecyclerAdapter.OnCategoryClickListener, SubCategoryAdapter.OnSubCategoryClickListener {
    RecyclerView category_recycler,rcvSubCategory;
    private ArrayList<CategoryItem> categoryList = new ArrayList<>();
    private CategoryRecyclerAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;

    private OnSelectCategoryClickListener   onSelectCategoryListener;

    public void setOnSelectCategoryClickListener(OnSelectCategoryClickListener listener) {
        this.onSelectCategoryListener = listener;
    }

    public interface OnSelectCategoryClickListener {
        void onSelectCategory(CategoryItem category);
    }

    public CategorySelectionDialog(ArrayList<CategoryItem> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_category_selection, container, false);
        category_recycler = view.findViewById(R.id.category_recycler);
        rcvSubCategory = view.findViewById(R.id.rcvSubCategory);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryAdapter = new CategoryRecyclerAdapter(categoryList);
        category_recycler.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryClickListener(this);
    }

    public void updateCategorylist(ArrayList<CategoryItem> categoryList){
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCategoryClick(CategoryItem category) {
        subCategoryAdapter = new SubCategoryAdapter(category);
        rcvSubCategory.setAdapter(subCategoryAdapter);
        subCategoryAdapter.setOnSubCategoryClickListener(this);

        if (category.getSubCategory().isEmpty()){
            onSelectCategoryListener.onSelectCategory(category);
            dismiss();
        }

    }

    @Override
    public void onSubCategoryClick(CategoryItem category,String subCategory) {
        ArrayList<String> selectedSubCategory = new ArrayList<>();
        selectedSubCategory.add(subCategory);
        CategoryItem selected = new CategoryItem(category.getCategoryName(),selectedSubCategory);
        onSelectCategoryListener.onSelectCategory(selected);
        dismiss();
    }
}

package com.example.moneymanager.models;

import java.util.ArrayList;

public class CategoryItem {
    private  String categoryName;
    private ArrayList<String> subCategory;

    public CategoryItem(String categoryName, ArrayList<String> subCategory) {
        this.categoryName = categoryName;
        this.subCategory = subCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<String> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<String> subCategory) {
        this.subCategory = subCategory;
    }
}

package com.example.moneymanager.models;

public class CategoryItem {
    private String categoryName;

    public CategoryItem(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

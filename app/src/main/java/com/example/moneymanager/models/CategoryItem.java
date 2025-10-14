package com.example.moneymanager.models;

import java.util.List;

public class CategoryItem {
    private String categoryName;
    private List<String> subCategories;

    public CategoryItem(String categoryName, List<String> subCategories) {
        this.categoryName = categoryName;
        this.subCategories = subCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }
}

package com.gexton.appsxonedemo.models;

public class CategoryModel {
    public String CategoryId;
    public String CategoryName;

    public CategoryModel(String categoryId, String categoryName) {
        CategoryId = categoryId;
        CategoryName = categoryName;
    }

    public CategoryModel() {
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                '}';
    }
}

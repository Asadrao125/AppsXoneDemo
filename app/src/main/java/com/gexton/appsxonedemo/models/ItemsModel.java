package com.gexton.appsxonedemo.models;

public class ItemsModel {
    public String category;
    public String description;
    public String icp;
    public String itemId;
    public String imagePath;
    public String itemName;
    public String mcp;
    public String qoh;
    public String qoo;

    public ItemsModel(String category, String description, String icp, String itemId, String imagePath, String itemName, String mcp, String qoh, String qoo) {
        this.category = category;
        this.description = description;
        this.icp = icp;
        this.itemId = itemId;
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.mcp = mcp;
        this.qoh = qoh;
        this.qoo = qoo;
    }

    public ItemsModel() {
    }
}

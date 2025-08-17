package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class Category {
    private int categoryId;
    private String name;
    private String description;

    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


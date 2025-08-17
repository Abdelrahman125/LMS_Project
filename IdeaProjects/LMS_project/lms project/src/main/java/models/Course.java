package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class Course {
    private int courseId;
    private String name;
    private String description;
    private double price;
    private String instructorId;
    private Category category;
    private int maxStudents;
    private boolean isAvailable;

    public Course(int courseId, String name, String description, double price,
                  String instructorId, Category category, int maxStudents) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.instructorId = instructorId;
        this.category = category;
        this.maxStudents = maxStudents;
        this.isAvailable = true;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getMaxStudents() {
        return maxStudents;
    }
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", instructorId='" + instructorId + '\'' +
                ", category=" + category.getName() +
                ", maxStudents=" + maxStudents +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
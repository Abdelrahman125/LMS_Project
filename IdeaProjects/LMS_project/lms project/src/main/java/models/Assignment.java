package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class Assignment {
    private int assignmentId;
    private String title;
    private String description;
    private int courseId;
    private String deadline;
    private double maxGrade;

    public Assignment(int assignmentId, String title, String description,
                      int courseId, String deadline, double maxGrade) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.deadline = deadline;
        this.maxGrade = maxGrade;
    }

    // Getters and Setters
    public int getAssignmentId() { return assignmentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getCourseId() { return courseId; }
    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public double getMaxGrade() { return maxGrade; }
    public void setMaxGrade(double maxGrade) { this.maxGrade = maxGrade; }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", courseId=" + courseId +
                ", deadline='" + deadline + '\'' +
                ", maxGrade=" + maxGrade +
                '}';
    }
}
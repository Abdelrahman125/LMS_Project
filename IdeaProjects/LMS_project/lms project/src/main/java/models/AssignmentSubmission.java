package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class AssignmentSubmission {
    private int submissionId;
    private int assignmentId;
    private String studentId;
    private String content;
    private String submissionDate;
    private double grade;
    private boolean isGraded;

    public AssignmentSubmission(int submissionId, int assignmentId, String studentId,
                                String content, String submissionDate) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.content = content;
        this.submissionDate = submissionDate;
        this.grade = 0.0;
        this.isGraded = false;
    }

    // Getters and Setters
    public int getSubmissionId() { return submissionId; }
    public int getAssignmentId() { return assignmentId; }
    public String getStudentId() { return studentId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSubmissionDate() { return submissionDate; }
    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; this.isGraded = true; }
    public boolean isGraded() { return isGraded; }
}
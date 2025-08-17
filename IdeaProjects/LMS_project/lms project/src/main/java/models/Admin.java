package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;






public class Admin extends User {
    private int workingHours;

    public Admin(String username, String password, String dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.ADMIN);
        this.workingHours = workingHours;
    }

    public void viewAllCourses() {
        System.out.println("\n=== ALL COURSES ===");
        ArrayList<Course> courses = Database.getAllCourses();
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void viewAllInstructors() {
        System.out.println("\n=== ALL INSTRUCTORS ===");
        ArrayList<Instructor> instructors = Database.getAllInstructors();
        for (Instructor instructor : instructors) {
            System.out.println(instructor.getProfileInfo());
        }
    }

    public void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        ArrayList<Student> students = Database.getAllStudents();
        for (Student student : students) {
            System.out.println(student.getProfileInfo());
        }
    }

    public boolean deleteCourse(int courseId) {
        Course course = Database.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        Database.removeCourse(courseId);
        System.out.println("Course deleted successfully: " + course.getName());
        return true;
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("Welcome, Admin " + username);
        System.out.println("Working Hours: " + workingHours);
        System.out.println("\nSystem Statistics:");
        System.out.println("Total Courses: " + Database.getAllCourses().size());
        System.out.println("Total Instructors: " + Database.getAllInstructors().size());
        System.out.println("Total Students: " + Database.getAllStudents().size());
    }

    @Override
    public String getProfileInfo() {
        return "Admin Profile: " + username + " | Working Hours: " + workingHours;
    }

    // Getters and Setters
    public int getWorkingHours() { return workingHours; }
    public void setWorkingHours(int workingHours) { this.workingHours = workingHours; }
}

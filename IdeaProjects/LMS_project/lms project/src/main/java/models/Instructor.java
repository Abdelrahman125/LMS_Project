package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class Instructor extends User {
    private String specialization;
    private Wallet wallet;
    private ArrayList<Integer> teachingCourses;

    public Instructor(String username, String password, String dateOfBirth, String specialization) {
        super(username, password, dateOfBirth, Role.INSTRUCTOR);
        this.specialization = specialization;
        this.wallet = new Wallet(username, 0.0);
        this.teachingCourses = new ArrayList<>();
    }

    public boolean createCourse(String name, String description, double price,
                                Category category, int maxStudents) {
        // Additional validation in Instructor class
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Course name cannot be empty.");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Course description cannot be empty.");
            return false;
        }

        if (price <= 0) {
            System.out.println("Course price must be positive.");
            return false;
        }

        if (category == null) {
            System.out.println("Course must have a valid category.");
            return false;
        }

        if (maxStudents < 1) {
            System.out.println("Maximum students must be at least 1.");
            return false;
        }

        // Check if instructor already has a course with the same name
        for (Integer courseId : teachingCourses) {
            Course existingCourse = Database.getCourseById(courseId);
            if (existingCourse != null && existingCourse.getName().equalsIgnoreCase(name)) {
                System.out.println("You already have a course with this name.");
                return false;
            }
        }

        int courseId = Database.getNextCourseId();
        Course course = new Course(courseId, name, description, price,
                this.username, category, maxStudents);

        Database.addCourse(course);
        teachingCourses.add(courseId);
        System.out.println("Course created successfully: " + name);
        return true;
    }

    public boolean createAssignment(int courseId, String title, String description,
                                    String deadline, double maxGrade) {
        // Validation: Check if instructor teaches this course
        if (!teachingCourses.contains(courseId)) {
            System.out.println("You are not teaching this course.");
            return false;
        }

        // Additional validations
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Assignment title cannot be empty.");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Assignment description cannot be empty.");
            return false;
        }

        if (maxGrade <= 0) {
            System.out.println("Maximum grade must be positive.");
            return false;
        }

        // Check if assignment with same title exists for this course
        ArrayList<Assignment> assignments = Database.getAllAssignments();
        for (Assignment assignment : assignments) {
            if (assignment.getCourseId() == courseId &&
                    assignment.getTitle().equalsIgnoreCase(title)) {
                System.out.println("An assignment with this title already exists for this course.");
                return false;
            }
        }

        int assignmentId = Database.getNextAssignmentId();
        Assignment assignment = new Assignment(assignmentId, title, description,
                courseId, deadline, maxGrade);

        Database.addAssignment(assignment);
        System.out.println("Assignment created successfully: " + title);
        return true;
    }

    public void viewStudentsInCourse(int courseId) {
        if (!teachingCourses.contains(courseId)) {
            System.out.println("You are not teaching this course.");
            return;
        }

        System.out.println("\nStudents enrolled in course " + courseId + ":");
        ArrayList<String> students = Database.getStudentsInCourse(courseId);
        for (String studentId : students) {
            Student student = Database.getStudentById(studentId);
            if (student != null) {
                System.out.println("- " + student.getUsername());
            }
        }
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n=== INSTRUCTOR DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Specialization: " + specialization);
        System.out.println("Earnings: $" + wallet.getBalance());
        System.out.println("Teaching Courses: " + teachingCourses.size());

        // Show teaching courses
        System.out.println("\nYour Courses:");
        for (Integer courseId : teachingCourses) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                System.out.println("- " + course.getName() + " (Students: " +
                        Database.getStudentsInCourse(courseId).size() + ")");
            }
        }
    }

    @Override
    public String getProfileInfo() {
        return "Instructor Profile: " + username + " | Specialization: " + specialization +
                " | Earnings: $" + wallet.getBalance();
    }

    // Getters and Setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public Wallet getWallet() { return wallet; }
    public ArrayList<Integer> getTeachingCourses() { return teachingCourses; }
}
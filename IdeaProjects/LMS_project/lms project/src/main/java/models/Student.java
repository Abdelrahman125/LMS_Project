package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




public class Student extends User {
    private String address;
    private Gender gender;
    private Wallet wallet;
    private ArrayList<Integer> enrolledCourses;

    public Student(String username, String password, String dateOfBirth,
                   String address, Gender gender, double initialBalance) {
        super(username, password, dateOfBirth, Role.STUDENT);
        this.address = address;
        this.gender = gender;
        this.wallet = new Wallet(username, initialBalance);
        this.enrolledCourses = new ArrayList<>();
    }

    public boolean enrollInCourse(Course course) {
        // Validation: Check if course exists
        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        // Validation: Check if course is available
        if (!course.isAvailable()) {
            System.out.println("Course is not available for enrollment.");
            return false;
        }

        // Validation: Check if already enrolled
        if (enrolledCourses.contains(course.getCourseId())) {
            System.out.println("Already enrolled in this course.");
            return false;
        }

        // Validation: Check course capacity
        ArrayList<String> enrolledStudents = Database.getStudentsInCourse(course.getCourseId());
        if (enrolledStudents.size() >= course.getMaxStudents()) {
            System.out.println("Course is full! Maximum capacity reached.");
            return false;
        }

        // Validation: Check if student has sufficient balance
        if (!wallet.deduct(course.getPrice())) {
            System.out.println("Insufficient balance to enroll in course. Required: $" +
                    course.getPrice() + ", Available: $" + wallet.getBalance());
            return false;
        }

        // Enroll student and update instructor's balance
        enrolledCourses.add(course.getCourseId());
        Database.addStudentToCourse(this.username, course.getCourseId());

        // Add money to instructor's wallet
        Instructor instructor = Database.getInstructorById(course.getInstructorId());
        if (instructor != null) {
            instructor.getWallet().add(course.getPrice());
        }

        System.out.println("Successfully enrolled in course: " + course.getName());
        return true;
    }

    public boolean submitAssignment(int assignmentId, String content) {
        Assignment assignment = Database.getAssignmentById(assignmentId);
        if (assignment == null) {
            System.out.println("Assignment not found.");
            return false;
        }

        // Check if student is enrolled in the course
        if (!enrolledCourses.contains(assignment.getCourseId())) {
            System.out.println("You are not enrolled in this course.");
            return false;
        }

        // Validate content
        if (content == null || content.trim().isEmpty()) {
            System.out.println("Submission content cannot be empty.");
            return false;
        }

        if (content.trim().length() < 10) {
            System.out.println("Submission content is too short. Minimum 10 characters required.");
            return false;
        }

        // Check if already submitted
        ArrayList<AssignmentSubmission> submissions = Database.getAllSubmissions();
        for (AssignmentSubmission submission : submissions) {
            if (submission.getAssignmentId() == assignmentId &&
                    submission.getStudentId().equals(this.username)) {
                System.out.println("You have already submitted this assignment.");
                return false;
            }
        }

        // Create submission
        int submissionId = Database.getNextSubmissionId();
        AssignmentSubmission submission = new AssignmentSubmission(
                submissionId, assignmentId, this.username, content.trim(),
                java.time.LocalDate.now().toString()
        );

        Database.addSubmission(submission);
        System.out.println("Assignment submitted successfully.");
        return true;
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n=== STUDENT DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Balance: $" + wallet.getBalance());
        System.out.println("Enrolled Courses: " + enrolledCourses.size());

        // Show enrolled courses
        System.out.println("\nYour Enrolled Courses:");
        for (Integer courseId : enrolledCourses) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                System.out.println("- " + course.getName());
            }
        }
    }

    @Override
    public String getProfileInfo() {
        return "Student Profile: " + username + " | Address: " + address +
                " | Gender: " + gender + " | Balance: $" + wallet.getBalance();
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Wallet getWallet() {
        return wallet;
    }
    public ArrayList<Integer> getEnrolledCourses() {
        return enrolledCourses;
    }
}
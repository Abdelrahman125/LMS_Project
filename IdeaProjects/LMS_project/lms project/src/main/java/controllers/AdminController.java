// AdminController.java
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML private Label welcomeLabel;
    @FXML private Label workingHoursLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private Label totalInstructorsLabel;
    @FXML private Label totalStudentsLabel;
    @FXML private TableView<Course> allCoursesTable;
    @FXML private TableView<Instructor> instructorsTable;
    @FXML private TableView<Student> studentsTable;
    @FXML private Button deleteCourseButton;
    @FXML private Button viewCourseDetailsButton;
    @FXML private Button viewInstructorDetailsButton;
    @FXML private Button viewStudentDetailsButton;
    @FXML private Button logoutButton;
    @FXML private TextArea profileArea;

    private Admin currentAdmin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTables();
    }

    public void setCurrentUser(Admin admin) {
        this.currentAdmin = admin;
        updateDisplay();
        loadData();
    }

    private void setupTables() {
        // Setup All Courses Table
        if (allCoursesTable != null) {
            TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameCol.setPrefWidth(200);

            TableColumn<Course, String> instructorCol = new TableColumn<>("Instructor");
            instructorCol.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
            instructorCol.setPrefWidth(150);

            TableColumn<Course, Double> priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            priceCol.setPrefWidth(100);

            TableColumn<Course, String> categoryCol = new TableColumn<>("Category");
            categoryCol.setCellValueFactory(cellData -> {
                Category category = cellData.getValue().getCategory();
                return new javafx.beans.property.SimpleStringProperty(
                        category != null ? category.getName() : "Unknown"
                );
            });
            categoryCol.setPrefWidth(150);

            TableColumn<Course, Integer> studentsCol = new TableColumn<>("Students");
            studentsCol.setCellValueFactory(cellData -> {
                int studentCount = Database.getStudentsInCourse(cellData.getValue().getCourseId()).size();
                return new javafx.beans.property.SimpleIntegerProperty(studentCount).asObject();
            });
            studentsCol.setPrefWidth(100);

            TableColumn<Course, String> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(cellData -> {
                return new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().isAvailable() ? "Available" : "Unavailable"
                );
            });
            statusCol.setPrefWidth(100);

            allCoursesTable.getColumns().clear();
            allCoursesTable.getColumns().addAll(nameCol, instructorCol, priceCol, categoryCol, studentsCol, statusCol);
        }

        // Setup Instructors Table
        if (instructorsTable != null) {
            TableColumn<Instructor, String> nameCol = new TableColumn<>("Username");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            nameCol.setPrefWidth(150);

            TableColumn<Instructor, String> specializationCol = new TableColumn<>("Specialization");
            specializationCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
            specializationCol.setPrefWidth(200);

            TableColumn<Instructor, String> earningsCol = new TableColumn<>("Earnings");
            earningsCol.setCellValueFactory(cellData -> {
                return new javafx.beans.property.SimpleStringProperty(
                        "$" + String.format("%.2f", cellData.getValue().getWallet().getBalance())
                );
            });
            earningsCol.setPrefWidth(100);

            TableColumn<Instructor, Integer> coursesCol = new TableColumn<>("Courses");
            coursesCol.setCellValueFactory(cellData -> {
                int courseCount = cellData.getValue().getTeachingCourses().size();
                return new javafx.beans.property.SimpleIntegerProperty(courseCount).asObject();
            });
            coursesCol.setPrefWidth(100);

            instructorsTable.getColumns().clear();
            instructorsTable.getColumns().addAll(nameCol, specializationCol, earningsCol, coursesCol);
        }

        // Setup Students Table
        if (studentsTable != null) {
            TableColumn<Student, String> nameCol = new TableColumn<>("Username");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            nameCol.setPrefWidth(150);

            TableColumn<Student, String> addressCol = new TableColumn<>("Address");
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            addressCol.setPrefWidth(200);

            TableColumn<Student, String> genderCol = new TableColumn<>("Gender");
            genderCol.setCellValueFactory(cellData -> {
                Gender gender = cellData.getValue().getGender();
                return new javafx.beans.property.SimpleStringProperty(
                        gender != null ? gender.toString() : "Not specified"
                );
            });
            genderCol.setPrefWidth(100);

            TableColumn<Student, String> balanceCol = new TableColumn<>("Balance");
            balanceCol.setCellValueFactory(cellData -> {
                return new javafx.beans.property.SimpleStringProperty(
                        "$" + String.format("%.2f", cellData.getValue().getWallet().getBalance())
                );
            });
            balanceCol.setPrefWidth(100);

            TableColumn<Student, Integer> enrolledCol = new TableColumn<>("Enrolled Courses");
            enrolledCol.setCellValueFactory(cellData -> {
                int enrolledCount = cellData.getValue().getEnrolledCourses().size();
                return new javafx.beans.property.SimpleIntegerProperty(enrolledCount).asObject();
            });
            enrolledCol.setPrefWidth(120);

            studentsTable.getColumns().clear();
            studentsTable.getColumns().addAll(nameCol, addressCol, genderCol, balanceCol, enrolledCol);
        }
    }

    private void updateDisplay() {
        if (currentAdmin != null) {
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, Admin " + currentAdmin.getUsername());
            }
            if (workingHoursLabel != null) {
                workingHoursLabel.setText("Working Hours: " + currentAdmin.getWorkingHours());
            }
            if (totalCoursesLabel != null) {
                totalCoursesLabel.setText("Total Courses: " + Database.getAllCourses().size());
            }
            if (totalInstructorsLabel != null) {
                totalInstructorsLabel.setText("Total Instructors: " + Database.getAllInstructors().size());
            }
            if (totalStudentsLabel != null) {
                totalStudentsLabel.setText("Total Students: " + Database.getAllStudents().size());
            }
            updateProfileArea();
        }
    }

    private void updateProfileArea() {
        if (profileArea != null && currentAdmin != null) {
            StringBuilder profile = new StringBuilder();
            profile.append("Username: ").append(currentAdmin.getUsername()).append("\n");
            profile.append("Role: Administrator\n");
            profile.append("Date of Birth: ").append(currentAdmin.getDateOfBirth()).append("\n");
            profile.append("Working Hours: ").append(currentAdmin.getWorkingHours()).append(" hours/week\n\n");
            profile.append("System Statistics:\n");
            profile.append("- Total Courses: ").append(Database.getAllCourses().size()).append("\n");
            profile.append("- Total Instructors: ").append(Database.getAllInstructors().size()).append("\n");
            profile.append("- Total Students: ").append(Database.getAllStudents().size()).append("\n");
            profile.append("- Total Assignments: ").append(Database.getAllAssignments().size()).append("\n");
            profile.append("- Total Submissions: ").append(Database.getAllSubmissions().size());
            profileArea.setText(profile.toString());
        }
    }

    private void loadData() {
        loadAllCourses();
        loadInstructors();
        loadStudents();
    }

    private void loadAllCourses() {
        if (allCoursesTable != null) {
            ArrayList<Course> courses = Database.getAllCourses();
            ObservableList<Course> courseData = FXCollections.observableArrayList(courses);
            allCoursesTable.setItems(courseData);
        }
    }

    private void loadInstructors() {
        if (instructorsTable != null) {
            ArrayList<Instructor> instructors = Database.getAllInstructors();
            ObservableList<Instructor> instructorData = FXCollections.observableArrayList(instructors);
            instructorsTable.setItems(instructorData);
        }
    }

    private void loadStudents() {
        if (studentsTable != null) {
            ArrayList<Student> students = Database.getAllStudents();
            ObservableList<Student> studentData = FXCollections.observableArrayList(students);
            studentsTable.setItems(studentData);
        }
    }

    @FXML
    private void handleDeleteCourse() {
        Course selectedCourse = allCoursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("Selection Error", "Please select a course to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Course");
        confirmAlert.setHeaderText("Are you sure you want to delete this course?");
        confirmAlert.setContentText("Course: " + selectedCourse.getName() +
                "\nThis action cannot be undone and will affect enrolled students.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (currentAdmin.deleteCourse(selectedCourse.getCourseId())) {
                loadData(); // Refresh all tables
                updateDisplay();
                showAlert("Success", "Course deleted successfully!");
            } else {
                showAlert("Error", "Failed to delete course. Please try again.");
            }
        }
    }

    @FXML
    private void handleViewCourseDetails() {
        Course selectedCourse = allCoursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("Selection Error", "Please select a course to view details.");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Course Details:\n\n");
        details.append("ID: ").append(selectedCourse.getCourseId()).append("\n");
        details.append("Name: ").append(selectedCourse.getName()).append("\n");
        details.append("Description: ").append(selectedCourse.getDescription()).append("\n");
        details.append("Instructor: ").append(selectedCourse.getInstructorId()).append("\n");
        details.append("Price: $").append(String.format("%.2f", selectedCourse.getPrice())).append("\n");
        details.append("Category: ").append(selectedCourse.getCategory().getName()).append("\n");
        details.append("Max Students: ").append(selectedCourse.getMaxStudents()).append("\n");
        details.append("Current Students: ").append(Database.getStudentsInCourse(selectedCourse.getCourseId()).size()).append("\n");
        details.append("Status: ").append(selectedCourse.isAvailable() ? "Available" : "Unavailable");

        // Get assignments for this course
        ArrayList<Assignment> courseAssignments = new ArrayList<>();
        for (Assignment assignment : Database.getAllAssignments()) {
            if (assignment.getCourseId() == selectedCourse.getCourseId()) {
                courseAssignments.add(assignment);
            }
        }
        details.append("\nAssignments: ").append(courseAssignments.size());

        Alert detailAlert = new Alert(Alert.AlertType.INFORMATION);
        detailAlert.setTitle("Course Details");
        detailAlert.setHeaderText(null);
        detailAlert.setContentText(details.toString());
        detailAlert.getDialogPane().setPrefWidth(400);
        detailAlert.showAndWait();
    }

    @FXML
    private void handleViewInstructorDetails() {
        Instructor selectedInstructor = instructorsTable.getSelectionModel().getSelectedItem();
        if (selectedInstructor == null) {
            showAlert("Selection Error", "Please select an instructor to view details.");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Instructor Details:\n\n");
        details.append("Username: ").append(selectedInstructor.getUsername()).append("\n");
        details.append("Date of Birth: ").append(selectedInstructor.getDateOfBirth()).append("\n");
        details.append("Specialization: ").append(selectedInstructor.getSpecialization()).append("\n");
        details.append("Total Earnings: $").append(String.format("%.2f", selectedInstructor.getWallet().getBalance())).append("\n");
        details.append("Teaching Courses: ").append(selectedInstructor.getTeachingCourses().size()).append("\n\n");

        details.append("Courses Teaching:\n");
        for (Integer courseId : selectedInstructor.getTeachingCourses()) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                details.append("- ").append(course.getName()).append(" (").append(Database.getStudentsInCourse(courseId).size()).append(" students)\n");
            }
        }

        Alert detailAlert = new Alert(Alert.AlertType.INFORMATION);
        detailAlert.setTitle("Instructor Details");
        detailAlert.setHeaderText(null);
        detailAlert.setContentText(details.toString());
        detailAlert.getDialogPane().setPrefWidth(400);
        detailAlert.showAndWait();
    }

    @FXML
    private void handleViewStudentDetails() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("Selection Error", "Please select a student to view details.");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Student Details:\n\n");
        details.append("Username: ").append(selectedStudent.getUsername()).append("\n");
        details.append("Date of Birth: ").append(selectedStudent.getDateOfBirth()).append("\n");
        details.append("Address: ").append(selectedStudent.getAddress()).append("\n");
        details.append("Gender: ").append(selectedStudent.getGender()).append("\n");
        details.append("Balance: $").append(String.format("%.2f", selectedStudent.getWallet().getBalance())).append("\n");
        details.append("Enrolled Courses: ").append(selectedStudent.getEnrolledCourses().size()).append("\n\n");

        details.append("Enrolled in:\n");
        for (Integer courseId : selectedStudent.getEnrolledCourses()) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                details.append("- ").append(course.getName()).append(" ($").append(String.format("%.2f", course.getPrice())).append(")\n");
            }
        }

        // Calculate total spent
        double totalSpent = 0;
        for (Integer courseId : selectedStudent.getEnrolledCourses()) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                totalSpent += course.getPrice();
            }
        }
        details.append("\nTotal Amount Spent: $").append(String.format("%.2f", totalSpent));

        Alert detailAlert = new Alert(Alert.AlertType.INFORMATION);
        detailAlert.setTitle("Student Details");
        detailAlert.setHeaderText(null);
        detailAlert.setContentText(details.toString());
        detailAlert.getDialogPane().setPrefWidth(400);
        detailAlert.showAndWait();
    }

    @FXML
    private void handleRefreshData() {
        loadData();
        updateDisplay();
        showAlert("Success", "Data refreshed successfully!");
    }

    @FXML
    private void handleSystemReport() {
        StringBuilder report = new StringBuilder();
        report.append("LEARNING MANAGEMENT SYSTEM - DETAILED REPORT\n");
        report.append("=".repeat(50)).append("\n\n");

        // System Overview
        report.append("SYSTEM OVERVIEW:\n");
        report.append("- Total Users: ").append(Database.getAllStudents().size() + Database.getAllInstructors().size() + Database.getAllAdmins().size()).append("\n");
        report.append("- Total Courses: ").append(Database.getAllCourses().size()).append("\n");
        report.append("- Total Assignments: ").append(Database.getAllAssignments().size()).append("\n");
        report.append("- Total Submissions: ").append(Database.getAllSubmissions().size()).append("\n\n");

        // Course Statistics
        report.append("COURSE STATISTICS:\n");
        int totalEnrollments = 0;
        double totalRevenue = 0;
        for (Course course : Database.getAllCourses()) {
            int enrollments = Database.getStudentsInCourse(course.getCourseId()).size();
            totalEnrollments += enrollments;
            totalRevenue += course.getPrice() * enrollments;
        }
        report.append("- Total Enrollments: ").append(totalEnrollments).append("\n");
        report.append("- Total Revenue Generated: $").append(String.format("%.2f", totalRevenue)).append("\n");
        report.append("- Average Students per Course: ").append(Database.getAllCourses().size() > 0 ? String.format("%.1f", (double)totalEnrollments / Database.getAllCourses().size()) : "0").append("\n\n");

        // User Statistics
        report.append("USER STATISTICS:\n");
        report.append("- Students: ").append(Database.getAllStudents().size()).append("\n");
        report.append("- Instructors: ").append(Database.getAllInstructors().size()).append("\n");
        report.append("- Administrators: ").append(Database.getAllAdmins().size()).append("\n\n");

        // Top Courses
        report.append("TOP COURSES BY ENROLLMENT:\n");
        ArrayList<Course> courses = Database.getAllCourses();
        courses.sort((c1, c2) -> Integer.compare(
                Database.getStudentsInCourse(c2.getCourseId()).size(),
                Database.getStudentsInCourse(c1.getCourseId()).size()
        ));

        for (int i = 0; i < Math.min(5, courses.size()); i++) {
            Course course = courses.get(i);
            report.append((i + 1)).append(". ").append(course.getName())
                    .append(" - ").append(Database.getStudentsInCourse(course.getCourseId()).size())
                    .append(" students\n");
        }

        Alert reportAlert = new Alert(Alert.AlertType.INFORMATION);
        reportAlert.setTitle("System Report");
        reportAlert.setHeaderText("Learning Management System Report");
        reportAlert.setContentText(report.toString());
        reportAlert.getDialogPane().setPrefWidth(500);
        reportAlert.getDialogPane().setPrefHeight(600);
        reportAlert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene loginScene = new Scene(loader.load(), 800, 600);

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Learning Management System - Login");

        } catch (Exception e) {
            showAlert("Error", "Could not return to login screen: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

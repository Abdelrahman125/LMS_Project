// StudentController.java (Debug Version)
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
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    @FXML private Label welcomeLabel;
    @FXML private Label balanceLabel;
    @FXML private Label enrolledCoursesLabel;
    @FXML private TableView<Course> availableCoursesTable;
    @FXML private TableView<Course> myCoursesTable;
    @FXML private TableView<Assignment> assignmentsTable;
    @FXML private Button enrollButton;
    @FXML private Button submitAssignmentButton;
    @FXML private Button logoutButton;
    @FXML private TextArea profileArea;

    private Student currentStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("StudentController: initialize() called"); // Debug
        try {
            setupTables();
            System.out.println("StudentController: Tables setup completed"); // Debug
        } catch (Exception e) {
            System.err.println("StudentController: Error in initialize(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setCurrentUser(Student student) {
        System.out.println("StudentController: setCurrentUser() called with: " + student.getUsername()); // Debug
        try {
            this.currentStudent = student;
            updateDisplay();
            loadData();
            System.out.println("StudentController: setCurrentUser() completed successfully"); // Debug
        } catch (Exception e) {
            System.err.println("StudentController: Error in setCurrentUser(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTables() {
        System.out.println("StudentController: Setting up tables..."); // Debug

        // Setup Available Courses Table
        if (availableCoursesTable != null) {
            System.out.println("StudentController: Setting up available courses table"); // Debug
            TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameCol.setPrefWidth(200);

            TableColumn<Course, String> descCol = new TableColumn<>("Description");
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            descCol.setPrefWidth(250);

            TableColumn<Course, String> instructorCol = new TableColumn<>("Instructor");
            instructorCol.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
            instructorCol.setPrefWidth(150);

            TableColumn<Course, Double> priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            priceCol.setPrefWidth(100);

            TableColumn<Course, String> categoryCol = new TableColumn<>("Category");
            categoryCol.setCellValueFactory(cellData -> {
                try {
                    Category category = cellData.getValue().getCategory();
                    return new javafx.beans.property.SimpleStringProperty(
                            category != null ? category.getName() : "Unknown"
                    );
                } catch (Exception e) {
                    System.err.println("Error in category column: " + e.getMessage());
                    return new javafx.beans.property.SimpleStringProperty("Error");
                }
            });
            categoryCol.setPrefWidth(150);

            availableCoursesTable.getColumns().clear();
            availableCoursesTable.getColumns().addAll(nameCol, descCol, instructorCol, priceCol, categoryCol);
        } else {
            System.err.println("StudentController: availableCoursesTable is null!");
        }

        // Setup My Courses Table
        if (myCoursesTable != null) {
            System.out.println("StudentController: Setting up my courses table"); // Debug
            TableColumn<Course, String> myNameCol = new TableColumn<>("Course Name");
            myNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            myNameCol.setPrefWidth(200);

            TableColumn<Course, String> myInstructorCol = new TableColumn<>("Instructor");
            myInstructorCol.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
            myInstructorCol.setPrefWidth(150);

            TableColumn<Course, String> myProgressCol = new TableColumn<>("Status");
            myProgressCol.setCellValueFactory(cellData -> {
                return new javafx.beans.property.SimpleStringProperty("Enrolled");
            });
            myProgressCol.setPrefWidth(100);

            myCoursesTable.getColumns().clear();
            myCoursesTable.getColumns().addAll(myNameCol, myInstructorCol, myProgressCol);
        } else {
            System.err.println("StudentController: myCoursesTable is null!");
        }

        // Setup Assignments Table
        if (assignmentsTable != null) {
            System.out.println("StudentController: Setting up assignments table"); // Debug
            TableColumn<Assignment, String> titleCol = new TableColumn<>("Assignment Title");
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            titleCol.setPrefWidth(200);

            TableColumn<Assignment, String> courseCol = new TableColumn<>("Course");
            courseCol.setCellValueFactory(cellData -> {
                try {
                    Course course = Database.getCourseById(cellData.getValue().getCourseId());
                    return new javafx.beans.property.SimpleStringProperty(
                            course != null ? course.getName() : "Unknown"
                    );
                } catch (Exception e) {
                    System.err.println("Error in course column: " + e.getMessage());
                    return new javafx.beans.property.SimpleStringProperty("Error");
                }
            });
            courseCol.setPrefWidth(150);

            TableColumn<Assignment, String> deadlineCol = new TableColumn<>("Deadline");
            deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            deadlineCol.setPrefWidth(100);

            TableColumn<Assignment, Double> maxGradeCol = new TableColumn<>("Max Grade");
            maxGradeCol.setCellValueFactory(new PropertyValueFactory<>("maxGrade"));
            maxGradeCol.setPrefWidth(100);

            assignmentsTable.getColumns().clear();
            assignmentsTable.getColumns().addAll(titleCol, courseCol, deadlineCol, maxGradeCol);
        } else {
            System.err.println("StudentController: assignmentsTable is null!");
        }

        System.out.println("StudentController: Tables setup completed"); // Debug
    }

    private void updateDisplay() {
        System.out.println("StudentController: updateDisplay() called"); // Debug
        if (currentStudent != null) {
            try {
                if (welcomeLabel != null) {
                    welcomeLabel.setText("Welcome, " + currentStudent.getUsername());
                }
                if (balanceLabel != null) {
                    balanceLabel.setText("Balance: $" + String.format("%.2f", currentStudent.getWallet().getBalance()));
                }
                if (enrolledCoursesLabel != null) {
                    enrolledCoursesLabel.setText("Enrolled Courses: " + currentStudent.getEnrolledCourses().size());
                }
                updateProfileArea();
                System.out.println("StudentController: Display updated successfully"); // Debug
            } catch (Exception e) {
                System.err.println("StudentController: Error updating display: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("StudentController: currentStudent is null in updateDisplay()");
        }
    }

    private void updateProfileArea() {
        if (profileArea != null && currentStudent != null) {
            try {
                StringBuilder profile = new StringBuilder();
                profile.append("Username: ").append(currentStudent.getUsername()).append("\n");
                profile.append("Role: Student\n");
                profile.append("Date of Birth: ").append(currentStudent.getDateOfBirth()).append("\n");
                profile.append("Address: ").append(currentStudent.getAddress()).append("\n");
                profile.append("Gender: ").append(currentStudent.getGender()).append("\n");
                profile.append("Balance: $").append(String.format("%.2f", currentStudent.getWallet().getBalance()));
                profileArea.setText(profile.toString());
            } catch (Exception e) {
                System.err.println("StudentController: Error updating profile area: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadData() {
        System.out.println("StudentController: loadData() called"); // Debug
        try {
            loadAvailableCourses();
            loadMyCourses();
            loadAssignments();
            System.out.println("StudentController: Data loaded successfully"); // Debug
        } catch (Exception e) {
            System.err.println("StudentController: Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAvailableCourses() {
        if (availableCoursesTable != null && currentStudent != null) {
            try {
                ArrayList<Course> allCourses = Database.getAllCourses();
                ArrayList<Course> availableCourses = new ArrayList<>();

                for (Course course : allCourses) {
                    if (course.isAvailable() && !currentStudent.getEnrolledCourses().contains(course.getCourseId())) {
                        availableCourses.add(course);
                    }
                }

                ObservableList<Course> courseData = FXCollections.observableArrayList(availableCourses);
                availableCoursesTable.setItems(courseData);
                System.out.println("StudentController: Loaded " + availableCourses.size() + " available courses");
            } catch (Exception e) {
                System.err.println("StudentController: Error loading available courses: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadMyCourses() {
        if (myCoursesTable != null && currentStudent != null) {
            try {
                ArrayList<Course> enrolledCourses = new ArrayList<>();

                for (Integer courseId : currentStudent.getEnrolledCourses()) {
                    Course course = Database.getCourseById(courseId);
                    if (course != null) {
                        enrolledCourses.add(course);
                    }
                }

                ObservableList<Course> courseData = FXCollections.observableArrayList(enrolledCourses);
                myCoursesTable.setItems(courseData);
                System.out.println("StudentController: Loaded " + enrolledCourses.size() + " enrolled courses");
            } catch (Exception e) {
                System.err.println("StudentController: Error loading my courses: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadAssignments() {
        if (assignmentsTable != null && currentStudent != null) {
            try {
                ArrayList<Assignment> availableAssignments = new ArrayList<>();

                for (Integer courseId : currentStudent.getEnrolledCourses()) {
                    ArrayList<Assignment> allAssignments = Database.getAllAssignments();
                    for (Assignment assignment : allAssignments) {
                        if (assignment.getCourseId() == courseId) {
                            availableAssignments.add(assignment);
                        }
                    }
                }

                ObservableList<Assignment> assignmentData = FXCollections.observableArrayList(availableAssignments);
                assignmentsTable.setItems(assignmentData);
                System.out.println("StudentController: Loaded " + availableAssignments.size() + " assignments");
            } catch (Exception e) {
                System.err.println("StudentController: Error loading assignments: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEnroll() {
        Course selectedCourse = availableCoursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("Selection Error", "Please select a course to enroll in.");
            return;
        }

        if (currentStudent.enrollInCourse(selectedCourse)) {
            showAlert("Success", "Successfully enrolled in " + selectedCourse.getName());
            updateDisplay();
            loadData(); // Refresh all tables
        } else {
            showAlert("Enrollment Failed", "Failed to enroll. Check your balance or enrollment status.");
        }
    }

    @FXML
    private void handleSubmitAssignment() {
        Assignment selectedAssignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAssignment == null) {
            showAlert("Selection Error", "Please select an assignment to submit.");
            return;
        }

        // Create a dialog for assignment submission
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Submit Assignment");
        dialog.setHeaderText("Submit Assignment: " + selectedAssignment.getTitle());
        dialog.setContentText("Enter your submission content:");

        dialog.showAndWait().ifPresent(content -> {
            if (currentStudent.submitAssignment(selectedAssignment.getAssignmentId(), content)) {
                showAlert("Success", "Assignment submitted successfully!");
            } else {
                showAlert("Submission Failed", "Failed to submit assignment. Please try again.");
            }
        });
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

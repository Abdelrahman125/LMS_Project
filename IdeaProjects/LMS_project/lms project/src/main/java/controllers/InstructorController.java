// InstructorController.java
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {
    @FXML private Label welcomeLabel;
    @FXML private Label specializationLabel;
    @FXML private Label earningsLabel;
    @FXML private Label teachingCoursesLabel;
    @FXML private TableView<Course> myCoursesTable;
    @FXML private TableView<Student> studentsTable;
    @FXML private TableView<Assignment> assignmentsTable;
    @FXML private Button createCourseButton;
    @FXML private Button createAssignmentButton;
    @FXML private Button logoutButton;
    @FXML private TextArea profileArea;

    private Instructor currentInstructor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTables();
    }

    public void setCurrentUser(Instructor instructor) {
        this.currentInstructor = instructor;
        updateDisplay();
        loadData();
    }

    private void setupTables() {
        // Setup My Courses Table
        if (myCoursesTable != null) {
            TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameCol.setPrefWidth(200);

            TableColumn<Course, String> descCol = new TableColumn<>("Description");
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            descCol.setPrefWidth(250);

            TableColumn<Course, Double> priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            priceCol.setPrefWidth(100);

            TableColumn<Course, Integer> studentsCol = new TableColumn<>("Students");
            studentsCol.setCellValueFactory(cellData -> {
                int studentCount = Database.getStudentsInCourse(cellData.getValue().getCourseId()).size();
                return new javafx.beans.property.SimpleIntegerProperty(studentCount).asObject();
            });
            studentsCol.setPrefWidth(100);

            myCoursesTable.getColumns().clear();
            myCoursesTable.getColumns().addAll(nameCol, descCol, priceCol, studentsCol);
        }

        // Setup Students Table
        if (studentsTable != null) {
            TableColumn<Student, String> studentNameCol = new TableColumn<>("Student Name");
            studentNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            studentNameCol.setPrefWidth(150);

            TableColumn<Student, String> addressCol = new TableColumn<>("Address");
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            addressCol.setPrefWidth(200);

            // Fixed gender column with null check
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

            studentsTable.getColumns().clear();
            studentsTable.getColumns().addAll(studentNameCol, addressCol, genderCol, balanceCol);
        }

        // Setup Assignments Table
        if (assignmentsTable != null) {
            TableColumn<Assignment, String> titleCol = new TableColumn<>("Title");
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            titleCol.setPrefWidth(200);

            TableColumn<Assignment, String> courseCol = new TableColumn<>("Course");
            courseCol.setCellValueFactory(cellData -> {
                Course course = Database.getCourseById(cellData.getValue().getCourseId());
                return new javafx.beans.property.SimpleStringProperty(
                        course != null ? course.getName() : "Unknown"
                );
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
        }
    }

    private void updateDisplay() {
        if (currentInstructor != null) {
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, " + currentInstructor.getUsername());
            }
            if (specializationLabel != null) {
                specializationLabel.setText("Specialization: " + currentInstructor.getSpecialization());
            }
            if (earningsLabel != null) {
                earningsLabel.setText("Earnings: $" + String.format("%.2f", currentInstructor.getWallet().getBalance()));
            }
            if (teachingCoursesLabel != null) {
                teachingCoursesLabel.setText("Teaching Courses: " + currentInstructor.getTeachingCourses().size());
            }
            updateProfileArea();
        }
    }

    private void updateProfileArea() {
        if (profileArea != null && currentInstructor != null) {
            StringBuilder profile = new StringBuilder();
            profile.append("Username: ").append(currentInstructor.getUsername()).append("\n");
            profile.append("Role: Instructor\n");
            profile.append("Date of Birth: ").append(currentInstructor.getDateOfBirth()).append("\n");
            profile.append("Specialization: ").append(currentInstructor.getSpecialization()).append("\n");
            profile.append("Total Earnings: $").append(String.format("%.2f", currentInstructor.getWallet().getBalance()));
            profileArea.setText(profile.toString());
        }
    }

    private void loadData() {
        loadMyCourses();
        loadMyStudents();
        loadMyAssignments();
    }

    private void loadMyCourses() {
        if (myCoursesTable != null && currentInstructor != null) {
            ArrayList<Course> myCourses = new ArrayList<>();
            for (Integer courseId : currentInstructor.getTeachingCourses()) {
                Course course = Database.getCourseById(courseId);
                if (course != null) {
                    myCourses.add(course);
                }
            }
            ObservableList<Course> courseData = FXCollections.observableArrayList(myCourses);
            myCoursesTable.setItems(courseData);
        }
    }

    private void loadMyStudents() {
        if (studentsTable != null && currentInstructor != null) {
            ArrayList<Student> allStudents = new ArrayList<>();
            for (Integer courseId : currentInstructor.getTeachingCourses()) {
                ArrayList<String> studentIds = Database.getStudentsInCourse(courseId);
                for (String studentId : studentIds) {
                    Student student = Database.getStudentById(studentId);
                    if (student != null && !allStudents.contains(student)) {
                        allStudents.add(student);
                    }
                }
            }
            ObservableList<Student> studentData = FXCollections.observableArrayList(allStudents);
            studentsTable.setItems(studentData);
        }
    }

    private void loadMyAssignments() {
        if (assignmentsTable != null && currentInstructor != null) {
            ArrayList<Assignment> myAssignments = new ArrayList<>();
            for (Integer courseId : currentInstructor.getTeachingCourses()) {
                ArrayList<Assignment> allAssignments = Database.getAllAssignments();
                for (Assignment assignment : allAssignments) {
                    if (assignment.getCourseId() == courseId) {
                        myAssignments.add(assignment);
                    }
                }
            }
            ObservableList<Assignment> assignmentData = FXCollections.observableArrayList(myAssignments);
            assignmentsTable.setItems(assignmentData);
        }
    }

    @FXML
    private void handleCreateCourse() {
        // Create a simple dialog for course creation
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Create New Course");
        dialog.setHeaderText("Enter course details:");

        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Course Name");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Course Description");
        descArea.setPrefRowCount(3);
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.setItems(FXCollections.observableArrayList(Database.getAllCategories()));
        TextField maxStudentsField = new TextField();
        maxStudentsField.setPromptText("Maximum Students");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descArea, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryBox, 1, 3);
        grid.add(new Label("Max Students:"), 0, 4);
        grid.add(maxStudentsField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                try {
                    String name = nameField.getText().trim();
                    String description = descArea.getText().trim();
                    double price = Double.parseDouble(priceField.getText());
                    Category category = categoryBox.getValue();
                    int maxStudents = Integer.parseInt(maxStudentsField.getText());

                    if (currentInstructor.createCourse(name, description, price, category, maxStudents)) {
                        return new Course(0, name, description, price, currentInstructor.getUsername(), category, maxStudents);
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for price and max students.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(course -> {
            loadData();
            updateDisplay();
            showAlert("Success", "Course created successfully!");
        });
    }

    @FXML
    private void handleCreateAssignment() {
        if (currentInstructor.getTeachingCourses().isEmpty()) {
            showAlert("No Courses", "You must create at least one course before creating assignments.");
            return;
        }

        Dialog<Assignment> dialog = new Dialog<>();
        dialog.setTitle("Create New Assignment");
        dialog.setHeaderText("Enter assignment details:");

        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Course> courseBox = new ComboBox<>();
        ArrayList<Course> teachingCourses = new ArrayList<>();
        for (Integer courseId : currentInstructor.getTeachingCourses()) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                teachingCourses.add(course);
            }
        }
        courseBox.setItems(FXCollections.observableArrayList(teachingCourses));

        TextField titleField = new TextField();
        titleField.setPromptText("Assignment Title");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Assignment Description");
        descArea.setPrefRowCount(3);
        TextField deadlineField = new TextField();
        deadlineField.setPromptText("Deadline (YYYY-MM-DD)");
        TextField maxGradeField = new TextField();
        maxGradeField.setPromptText("Maximum Grade");

        grid.add(new Label("Course:"), 0, 0);
        grid.add(courseBox, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descArea, 1, 2);
        grid.add(new Label("Deadline:"), 0, 3);
        grid.add(deadlineField, 1, 3);
        grid.add(new Label("Max Grade:"), 0, 4);
        grid.add(maxGradeField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                try {
                    Course selectedCourse = courseBox.getValue();
                    String title = titleField.getText().trim();
                    String description = descArea.getText().trim();
                    String deadline = deadlineField.getText().trim();
                    double maxGrade = Double.parseDouble(maxGradeField.getText());

                    if (selectedCourse != null && currentInstructor.createAssignment(
                            selectedCourse.getCourseId(), title, description, deadline, maxGrade)) {
                        return new Assignment(0, title, description, selectedCourse.getCourseId(), deadline, maxGrade);
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter a valid number for maximum grade.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(assignment -> {
            loadData();
            showAlert("Success", "Assignment created successfully!");
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

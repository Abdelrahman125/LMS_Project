// LoginController.java
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }

        User user = Database.authenticate(username, password);
        if (user != null) {
            openDashboard(user);
        } else {
            showError("Invalid username or password");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red;");
    }

    private void openDashboard(User user) {
        try {
            FXMLLoader loader = null;
            String title = "";

            if (user instanceof Student) {
                loader = new FXMLLoader(getClass().getResource("/fxml/student-dashboard.fxml"));
                title = "Student Dashboard - LMS";
            } else if (user instanceof Instructor) {
                loader = new FXMLLoader(getClass().getResource("/fxml/instructor-dashboard.fxml"));
                title = "Instructor Dashboard - LMS";
            } else if (user instanceof Admin) {
                loader = new FXMLLoader(getClass().getResource("/fxml/admin-dashboard.fxml"));
                title = "Admin Dashboard - LMS";
            }

            if (loader != null) {
                Scene scene = new Scene(loader.load(), 1200, 800);

                // Pass user data to the appropriate controller
                if (user instanceof Student) {
                    StudentController controller = loader.getController();
                    controller.setCurrentUser((Student) user);
                } else if (user instanceof Instructor) {
                    InstructorController controller = loader.getController();
                    controller.setCurrentUser((Instructor) user);
                } else if (user instanceof Admin) {
                    AdminController controller = loader.getController();
                    controller.setCurrentUser((Admin) user);
                }

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading dashboard");
        }
    }
}

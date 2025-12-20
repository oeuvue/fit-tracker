package com.controller;

import com.model.User;
import com.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * [CONTROLLER]
 * This handles the logic for the login.fxml screen.
 * It listens for button clicks and user input.
 */
public class LoginController {

    @FXML
    private TextField usernameField; // Links to the text box in FXML

    @FXML
    private PasswordField passwordField; // Links to the password box in FXML

    // This method runs when the user clicks the "Login" button
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 1. Validate credentials against users.csv
        boolean isValid = com.service.DataStore.validateLogin(username, password);

        if (isValid) {
            // 2. Create the User object
            com.model.User user = new com.model.User(username);

            // 3. [PERSISTENCE FIX] Load the saved profile (Weight, Goal, Age)
            // We do this BEFORE switching screens so the data is ready immediately
            com.service.DataStore.loadUserProfile(user);

            // 4. Save fully loaded user to the Singleton
            com.service.UserSession.getInstance().setUser(user);

            // 5. Switch to Homepage
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } else {
            System.out.println("Invalid credentials!");
            // Optional: Add a label in your FXML to show "Wrong Password" on screen
        }
    }

    // This method runs when the user clicks "Sign Up"
    @FXML
    public void goToSignup(ActionEvent event) throws IOException {
        System.out.println("Switching to Signup screen...");

        // Load the Signup FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/signup.fxml"));

        // Get the current window (Stage)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
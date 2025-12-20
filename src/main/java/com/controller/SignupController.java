package com.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

/**
 * [CONTROLLER]
 * Handles the logic for registering a new user.
 */
public class SignupController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    // Triggered when "Create Account" is clicked

    @FXML
    public void handleSignup(ActionEvent event) {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Fields cannot be empty!");
            return;
        }

        // [SERVICE CALL] Try to save the user
        boolean success = com.service.DataStore.registerUser(username, password);

        if (success) {
            System.out.println("User registered successfully!");
            goToLogin(event); // Auto-redirect to login
        } else {
            System.out.println("Registration failed! Username might be taken.");
            // Ideally, show a label on the screen saying "Username taken"
        }
    }

    // Triggered when "Back" is clicked
    @FXML
    public void handleBack(ActionEvent event) {
        goToLogin(event);
    }

    // Helper method to switch scenes
    private void goToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.controller;

import com.model.User;
import com.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {

    @FXML
    private Label welcomeLabel;

    private User currentUser; // Store the user who is logged in

    // [DATA PASSING] This method is called by the LoginController
    public void initData(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        // Go back to Login Screen
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void goToLibrary(ActionEvent event) throws IOException {
        // Check if a session exists in the [SINGLETON]
        if (com.model.WorkoutSession.getInstance().getCurrentExercise() != null) {
            // Redirect straight to the active workout
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/active_workout.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            // Otherwise, go to the library to pick an exercise
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/workoutlibrary.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    public void goToCalories(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/calories.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void goToProfile(ActionEvent event) throws IOException {
        // 1. Load the Profile FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/profile.fxml"));
        Parent root = loader.load();

        // 2. Get the current Stage (window)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // 3. Set and show the new Scene
        stage.setScene(new Scene(root));
        stage.show();
    }



    public void initialize() {
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername());
        } else {
            welcomeLabel.setText("Welcome, Guest");
        }
    }

    @FXML
    public void goToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/history.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
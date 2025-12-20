package com.controller;

import com.model.User;
import com.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class ProfileController {

    @FXML private Label usernameLabel;
    @FXML private TextField weightField, heightField, ageField;
    @FXML private ChoiceBox<String> activityChoiceBox;
    @FXML private ChoiceBox<String> goalChoiceBox;

    @FXML
    public void initialize() {
        // Setup Dropdowns
        activityChoiceBox.getItems().addAll("Sedentary", "Lightly Active", "Moderately Active", "Very Active");
        goalChoiceBox.getItems().addAll("Lose Weight", "Maintain", "Gain Weight");

        // Load User Data
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            usernameLabel.setText(user.getUsername());
            weightField.setText(String.valueOf(user.getWeight()));
            heightField.setText(String.valueOf(user.getHeight()));
            ageField.setText(String.valueOf(user.getAge()));

            // Set defaults if null
            activityChoiceBox.setValue(user.getActivityLevel() != null ? user.getActivityLevel() : "Sedentary");
            goalChoiceBox.setValue(user.getGoal() != null ? user.getGoal() : "Maintain");
        }
    }

    @FXML
    public void handleSaveProfile() {
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            try {
                // 1. Update the Memory (Singleton)
                user.setWeight(Double.parseDouble(weightField.getText()));
                user.setHeight(Double.parseDouble(heightField.getText()));
                user.setAge(Integer.parseInt(ageField.getText()));
                user.setActivityLevel(activityChoiceBox.getValue());
                user.setGoal(goalChoiceBox.getValue());

                // 2. [FIX] Update the File (Persistence)
                com.service.DataStore.saveUserProfile(user);

                System.out.println("Profile Saved & Persisted!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid numbers!");
            }
        }
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }


}
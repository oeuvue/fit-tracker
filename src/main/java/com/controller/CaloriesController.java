package com.controller;

import com.model.User;
import com.service.CalorieService;
import com.service.DataStore; // Don't forget this import!
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

public class CaloriesController {

    @FXML private ProgressBar calorieProgressBar;
    @FXML private Label progressLabel;
    @FXML private Label targetLabel;
    @FXML private TextField foodInput;

    private double currentCalories = 0;
    private double dailyTarget = 2000;

    @FXML
    public void initialize() {
        User user = UserSession.getInstance().getUser();

        if (user != null) {
            if (user.getWeight() > 0) {
                this.dailyTarget = CalorieService.calculateTarget(
                        user.getWeight(), user.getHeight(), user.getAge(),
                        user.getActivityLevel(), user.getGoal()
                );
                targetLabel.setText(String.format("%.0f kcal (%s)", dailyTarget, user.getGoal()));
            }

            this.currentCalories = DataStore.loadDailyCalories(user.getUsername());
        }

        updateProgressUI();
    }

    @FXML
    public void handleAddFood() {
        try {
            double calories = Double.parseDouble(foodInput.getText());
            currentCalories += calories;

            User user = UserSession.getInstance().getUser();
            if (user != null) {
                DataStore.saveDailyCalories(user.getUsername(), currentCalories);
            }

            foodInput.clear();
            updateProgressUI();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    private void updateProgressUI() {
        double progress = currentCalories / dailyTarget;
        calorieProgressBar.setProgress(progress);
        progressLabel.setText(String.format("%.0f / %.0f kcal", currentCalories, dailyTarget));

        if (progress > 1.0) calorieProgressBar.setStyle("-fx-accent: #e74c3c;");
        else calorieProgressBar.setStyle("-fx-accent: #2ecc71;");
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
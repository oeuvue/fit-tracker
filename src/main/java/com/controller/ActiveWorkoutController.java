package com.controller;

import com.model.*;
import com.service.DataStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


import java.io.IOException;

public class ActiveWorkoutController {

    @FXML private Label exerciseNameLabel;
    @FXML private Label setNumberLabel;
    @FXML private TextField repsField;
    @FXML private TextField weightField;
    @FXML private HBox weightInputSection;
    @FXML private ListView<String> logListView;

    private Exercise currentExercise;
    private int setCounter = 1;
    private ObservableList<String> setLogs = FXCollections.observableArrayList();

    public void initData(Exercise exercise) {
        this.currentExercise = exercise;
        exerciseNameLabel.setText(exercise.getName());
        logListView.setItems(setLogs);

        if (exercise instanceof BodyweightExercise) {
            weightInputSection.setVisible(false);
            weightInputSection.setManaged(false);
        }
    }



    @FXML
    public void handleFinishWorkout(ActionEvent event) {
        WorkoutSession session = WorkoutSession.getInstance();

        // 1. Get the current user
        com.model.User user = com.service.UserSession.getInstance().getUser();
        String username = (user != null) ? user.getUsername() : "Guest";

        // 2. Save Data (Existing Logic)
        java.util.List<String> allLogs = new java.util.ArrayList<>();
        for(Exercise e : session.getExerciseQueue()) {
            allLogs.addAll(session.getLogsForExercise(e.getName()));
        }
        DataStore.saveWorkoutHistory(username, allLogs);

        // 3. [OBSERVER PATTERN] Trigger the notification!
        if (user != null) {
            session.notifyWorkoutFinished(user);
        }

        // 4. Cleanup and Exit
        session.reset();
        handleReturnToMenu(event);
    }

    // Inside ActiveWorkoutController.java

    @FXML
    public void initialize() {
        WorkoutSession session = WorkoutSession.getInstance();

        // [OBSERVER PATTERN]
        // 1. Keep the Smart Progress Tracker (Calculates percentages)
        session.addObserver(new com.service.observer.ProgressObserver());

        // 2. Keep the Console Logger (For debugging)
        session.addObserver(new com.service.observer.ConsoleLogger());

        // REMOVED: session.addObserver(new AchievementSystem()); <--- DELETED

        if (!session.getExerciseQueue().isEmpty()) {
            loadCurrentExercise();
        }
    }

    private void updateUIVisibility() {
        if (currentExercise instanceof BodyweightExercise) {
            // Hide it
            weightInputSection.setVisible(false);
            weightInputSection.setManaged(false);
        } else {
            // Show it again!
            weightInputSection.setVisible(true);
            weightInputSection.setManaged(true);
        }
    }

    @FXML
    public void handleReturnToMenu(ActionEvent event) {
        try {
            // [NAVIGATION] Switching back to the Homepage
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Returning to menu. Progress is cached in Singleton.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error returning to menu.");
        }
    }

    // Inside ActiveWorkoutController

    @FXML
    public void handleNextExercise() {
        WorkoutSession session = WorkoutSession.getInstance();
        if (session.getCurrentExerciseIndex() < session.getExerciseQueue().size() - 1) {
            session.setCurrentExerciseIndex(session.getCurrentExerciseIndex() + 1);
            loadCurrentExercise();
        }
    }

    @FXML
    public void handlePreviousExercise() {
        WorkoutSession session = WorkoutSession.getInstance();
        if (session.getCurrentExerciseIndex() > 0) {
            session.setCurrentExerciseIndex(session.getCurrentExerciseIndex() - 1);
            loadCurrentExercise();
        }
    }

    private void loadCurrentExercise() {
        WorkoutSession session = WorkoutSession.getInstance();
        this.currentExercise = session.getExerciseQueue().get(session.getCurrentExerciseIndex());

        exerciseNameLabel.setText(currentExercise.getName());

        // Load logs specific to THIS exercise
        ObservableList<String> logs = FXCollections.observableArrayList(
                session.getLogsForExercise(currentExercise.getName())
        );
        setLogs.setAll(logs);
        logListView.setItems(setLogs);

        setCounter = logs.size() + 1;
        setNumberLabel.setText("Set #" + setCounter);

        updateUIVisibility();
    }

    @FXML
    public void handleLogSet(ActionEvent event) {
        // ... (Your validation code) ...
        String logEntry = "Set " + setCounter + ": " + repsField.getText() + " reps";
        if (currentExercise instanceof WeightedExercise) {
            logEntry += " @ " + weightField.getText() + "kg";
        }

        // Save to Singleton indexed by Exercise Name
        WorkoutSession.getInstance().addLogForExercise(currentExercise.getName(), logEntry);

        setLogs.add(logEntry);
        setCounter++;
        setNumberLabel.setText("Set #" + setCounter);
        repsField.clear();
        weightField.clear();
    }
}
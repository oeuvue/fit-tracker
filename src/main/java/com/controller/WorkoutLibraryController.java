package com.controller;

import com.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

public class WorkoutLibraryController {

    @FXML private ListView<Exercise> exerciseListView;
    @FXML private ListView<Exercise> routineListView;

    private ObservableList<Exercise> routineItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ObservableList<Exercise> allExercises = FXCollections.observableArrayList();

        // --- CHEST ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Barbell Bench Press", "Chest"));
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Incline Dumbbell Press", "Chest"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Push Ups", "Chest"));

        // --- BACK ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Deadlift", "Back"));
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Lat Pulldown", "Back"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Pull Ups", "Back"));

        // --- LEGS ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Barbell Squat", "Legs"));
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Leg Press", "Legs"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Air Squats", "Legs"));

        // --- SHOULDERS ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Overhead Press (OHP)", "Shoulders"));
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Lateral Raises", "Shoulders"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Pike Push Ups", "Shoulders"));

        // --- ARMS ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Barbell Bicep Curl", "Arms"));
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Tricep Rope Pushdown", "Arms"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Bench Dips", "Arms"));

        // --- CORE ---
        allExercises.add(ExerciseFactory.createExercise("WEIGHTED", "Cable Crunch", "Core"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Plank", "Core"));
        allExercises.add(ExerciseFactory.createExercise("BODYWEIGHT", "Hanging Leg Raises", "Core"));

        // [SORTING] Alphabetical order
        FXCollections.sort(allExercises, (e1, e2) -> e1.getName().compareTo(e2.getName()));

        // Bind to UI
        exerciseListView.setItems(allExercises);
        routineListView.setItems(routineItems);
    }

    @FXML
    public void handleAddToRoutine(ActionEvent event) {
        Exercise selected = exerciseListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            routineItems.add(selected);
            System.out.println("Added to routine: " + selected.getName());
        }
    }

    @FXML
    public void handleStartExercise(ActionEvent event) throws IOException {
        if (routineItems.isEmpty()) return;

        // [CRITICAL STEP]: Give the full list to the Singleton so Next/Prev work
        WorkoutSession session = WorkoutSession.getInstance();
        session.setExerciseQueue(new java.util.ArrayList<>(routineItems));
        session.setCurrentExerciseIndex(0); // Start at the first one

        // Standard navigation code
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/active_workout.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
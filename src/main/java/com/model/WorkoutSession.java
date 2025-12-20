package com.model;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSession {
    // 1. Static instance (The Singleton)
    private static WorkoutSession instance;

    private Exercise currentExercise;
    private List<String> currentLogs;

    // 2. Private constructor (prevents other classes from creating new ones)
    private WorkoutSession() {
        currentLogs = new ArrayList<>();
    }

    // 3. Global access point
    public static WorkoutSession getInstance() {
        if (instance == null) {
            instance = new WorkoutSession();
        }
        return instance;
    }

    // Getters and Setters
    public Exercise getCurrentExercise() { return currentExercise; }
    public void setCurrentExercise(Exercise exercise) { this.currentExercise = exercise; }

    public List<String> getCurrentLogs() { return currentLogs; }
    public void addLog(String log) { this.currentLogs.add(log); }

    // Method to clear when the workout is actually finished
    public void reset() {
        currentExercise = null;
        currentLogs.clear();
    }

    private List<Exercise> exerciseQueue = new ArrayList<>();

    private int currentExerciseIndex = 0;

    // We need a map to store logs for EACH exercise separately
    private java.util.Map<String, List<String>> allLogs = new java.util.HashMap<>();

    public void setExerciseQueue(List<Exercise> list) {
        this.exerciseQueue = new ArrayList<>(list);
    }

    public List<Exercise> getExerciseQueue() { return exerciseQueue; }

    public int getCurrentExerciseIndex() { return currentExerciseIndex; }
    public void setCurrentExerciseIndex(int index) { this.currentExerciseIndex = index; }

    public void addLogForExercise(String exerciseName, String log) {
        allLogs.computeIfAbsent(exerciseName, k -> new ArrayList<>()).add(log);
    }

    public List<String> getLogsForExercise(String exerciseName) {
        return allLogs.getOrDefault(exerciseName, new ArrayList<>());
    }

    // --- OBSERVER PATTERN IMPLEMENTATION ---

    // 1. A list to hold all the listeners
    private List<com.service.observer.WorkoutObserver> observers = new ArrayList<>();

    // 2. Method to attach a new listener
    public void addObserver(com.service.observer.WorkoutObserver observer) {
        observers.add(observer);
    }

    // 3. Method to remove a listener (good practice)
    public void removeObserver(com.service.observer.WorkoutObserver observer) {
        observers.remove(observer);
    }

    // 4. The Trigger: This loops through the list and calls the method on everyone
    public void notifyWorkoutFinished(User user) {
        for (com.service.observer.WorkoutObserver observer : observers) {
            observer.onWorkoutFinished(user, exerciseQueue.size());
        }
    }
}
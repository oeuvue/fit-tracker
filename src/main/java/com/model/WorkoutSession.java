package com.model;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSession {

    private static WorkoutSession instance;

    private Exercise currentExercise;
    private List<String> currentLogs;


    private WorkoutSession() {
        currentLogs = new ArrayList<>();
    }


    public static WorkoutSession getInstance() {
        if (instance == null) {
            instance = new WorkoutSession();
        }
        return instance;
    }


    public Exercise getCurrentExercise() { return currentExercise; }
    public void setCurrentExercise(Exercise exercise) { this.currentExercise = exercise; }

    public List<String> getCurrentLogs() { return currentLogs; }
    public void addLog(String log) { this.currentLogs.add(log); }


    public void reset() {
        currentExercise = null;
        currentLogs.clear();
    }

    private List<Exercise> exerciseQueue = new ArrayList<>();

    private int currentExerciseIndex = 0;


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




    private List<com.service.observer.WorkoutObserver> observers = new ArrayList<>();


    public void addObserver(com.service.observer.WorkoutObserver observer) {
        observers.add(observer);
    }


    public void removeObserver(com.service.observer.WorkoutObserver observer) {
        observers.remove(observer);
    }


    public void notifyWorkoutFinished(User user) {
        for (com.service.observer.WorkoutObserver observer : observers) {
            observer.onWorkoutFinished(user, exerciseQueue.size());
        }
    }
}
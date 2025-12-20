package com.service.observer;

import com.model.User;

public class ConsoleLogger implements WorkoutObserver {
    @Override
    public void onWorkoutFinished(User user, int numberOfExercises) {
        System.out.println(">> [OBSERVER LOG] User " + user.getUsername() +
                " completed a session with " + numberOfExercises + " exercises.");
    }
}
package com.service.observer;

import com.model.User;

public interface WorkoutObserver {
    // This method is called automatically when the subject triggers an event
    void onWorkoutFinished(User user, int numberOfExercises);
}
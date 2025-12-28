package com.service.observer;

import com.model.User;

public interface WorkoutObserver {

    void onWorkoutFinished(User user, int numberOfExercises);
}
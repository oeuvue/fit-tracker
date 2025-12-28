package com.model;

public class BodyweightExercise extends Exercise {
    public BodyweightExercise(String name, String muscleGroup) {
        super(name, muscleGroup);
    }

    @Override
    public String getLogType() {
        return "BODYWEIGHT";
    }
}
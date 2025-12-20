package com.model;

public class WeightedExercise extends Exercise {
    public WeightedExercise(String name, String muscleGroup) {
        super(name, muscleGroup);
    }

    @Override
    public String getLogType() {
        return "WEIGHTED"; // This tells the Controller to show the Weight input
    }
}
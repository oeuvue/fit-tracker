package com.model;

public class ExerciseFactory {
    public static Exercise createExercise(String type, String name, String muscle) {
        if (type.equalsIgnoreCase("WEIGHTED")) {
            return new WeightedExercise(name, muscle);
        } else if (type.equalsIgnoreCase("BODYWEIGHT")) {
            return new BodyweightExercise(name, muscle);
        }
        return null;
    }
}
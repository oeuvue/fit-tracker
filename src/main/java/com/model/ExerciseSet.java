package com.model;

/**
 * [MODEL]
 * Stores the result of one single set.
 */
public class ExerciseSet {
    private int reps;
    private double weight;

    public ExerciseSet(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }

    // Getters
    public int getReps() { return reps; }
    public double getWeight() { return weight; }
}
package com.service;

public class WeightLossStrategy implements GoalStrategy {
    @Override
    public double calculate(double tdee) {
        return tdee - 500; // Deficit
    }
}
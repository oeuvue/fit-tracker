package com.service;

public class WeightGainStrategy implements GoalStrategy {
    @Override
    public double calculate(double tdee) {
        return tdee + 500; // Surplus
    }
}
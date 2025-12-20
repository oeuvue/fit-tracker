package com.service;

public class MaintenanceStrategy implements GoalStrategy {
    @Override
    public double calculate(double tdee) {
        return tdee; // No change
    }
}
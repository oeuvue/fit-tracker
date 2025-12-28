package com.service;

public class CalorieService {

    public static double calculateTarget(double weight, double height, int age, String activityLevel, String goal) {

        double bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;

        double multiplier = switch (activityLevel) {
            case "Sedentary" -> 1.2;
            case "Lightly Active" -> 1.375;
            case "Moderately Active" -> 1.55;
            case "Very Active" -> 1.725;
            default -> 1.2;
        };

        double tdee = bmr * multiplier;


        GoalStrategy strategy;

        switch (goal) {
            case "Lose Weight":
                strategy = new WeightLossStrategy();
                break;
            case "Gain Weight":
                strategy = new WeightGainStrategy();
                break;
            default:
                strategy = new MaintenanceStrategy();
                break;
        }


        return strategy.calculate(tdee);
    }
}
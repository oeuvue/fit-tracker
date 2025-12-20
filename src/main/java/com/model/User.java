package com.model;

public class User {
    private String username;
    private double weight = 0.0;
    private double height = 0.0;
    private int age = 0;

    // NEW FIELDS
    private String activityLevel = "Sedentary";
    private String goal = "Maintain";

    public User(String username) {
        this.username = username;
    }

    // Getters and Setters
    public String getUsername() { return username; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
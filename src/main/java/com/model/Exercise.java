package com.model;

public abstract class Exercise {
    private String name;
    private String muscleGroup;

    public Exercise(String name, String muscleGroup) {
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public String getName() { return name; }
    public String getMuscleGroup() { return muscleGroup; }

    // Abstract method: Every exercise type handles its log string differently
    public abstract String getLogType();

    @Override
    public String toString() {
        return name + " (" + muscleGroup + ")";
    }
}
package com.service.observer;

import com.model.Exercise;
import com.model.User;
import com.model.WorkoutSession;
import com.service.DataStore;

import java.util.List;
import java.util.Map;

public class ProgressObserver implements WorkoutObserver {

    @Override
    public void onWorkoutFinished(User user, int numberOfExercises) {
        System.out.println("--------------------------------------------------");
        System.out.println(">> ANALYZING PROGRESS FOR " + user.getUsername().toUpperCase() + "...");

        // 1. Load old records
        Map<String, Double> personalBests = DataStore.loadPersonalBests(user.getUsername());
        boolean newRecordSet = false;

        WorkoutSession session = WorkoutSession.getInstance();

        // 2. Loop through every exercise done today
        for (Exercise exercise : session.getExerciseQueue()) {
            List<String> logs = session.getLogsForExercise(exercise.getName());

            // Calculate best performance in THIS session
            double todaysBest = extractBestValue(logs, exercise.getLogType());

            if (todaysBest > 0) {
                // Check against old record
                double oldBest = personalBests.getOrDefault(exercise.getName(), 0.0);

                if (todaysBest > oldBest) {
                    // --- NEW RECORD LOGIC ---
                    double improvement = 0;
                    if (oldBest > 0) {
                        improvement = ((todaysBest - oldBest) / oldBest) * 100;
                    } else {
                        improvement = 100; // First time doing it = 100% improvement logic
                    }

                    System.out.println("🔥 NEW PR! " + exercise.getName());
                    System.out.println("   Old Best: " + oldBest + " -> New Best: " + todaysBest);
                    System.out.printf("   Growth: +%.1f%%\n", improvement);

                    // Update the map
                    personalBests.put(exercise.getName(), todaysBest);
                    newRecordSet = true;
                }
            }
        }

        // 3. Save if we broke any records
        if (newRecordSet) {
            DataStore.savePersonalBests(user.getUsername(), personalBests);
            System.out.println(">> Records updated successfully.");
        } else {
            System.out.println(">> No new records this time. Keep pushing!");
        }
        System.out.println("--------------------------------------------------");
    }

    // Helper to extract numbers from strings like "Set 1: 10 reps @ 50kg"
    private double extractBestValue(List<String> logs, String type) {
        double maxVal = 0;

        for (String log : logs) {
            try {
                if (type.equals("WEIGHTED")) {
                    // Format: "... @ 50kg" -> Split by '@' then remove 'kg'
                    if (log.contains("@")) {
                        String part = log.split("@")[1].trim().replace("kg", "");
                        double weight = Double.parseDouble(part);
                        if (weight > maxVal) maxVal = weight;
                    }
                } else {
                    // BODYWEIGHT: Format: "... : 20 reps"
                    if (log.contains(":")) {
                        String part = log.split(":")[1].trim().split(" ")[0]; // Get the number before 'reps'
                        double reps = Double.parseDouble(part);
                        if (reps > maxVal) maxVal = reps;
                    }
                }
            } catch (Exception e) {
                continue; // Skip bad lines
            }
        }
        return maxVal;
    }
}
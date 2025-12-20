package com.service;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class DataStore {
    private static final String USER_FILE = "users.csv";

    // ==========================================
    // 1. AUTHENTICATION (Login & Signup)
    // ==========================================

    public static boolean validateLogin(String username, String password) {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        if (validateLogin(username, password) || userExists(username)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(USER_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(username + "," + password);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean userExists(String username) {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ==========================================
    // 2. WORKOUT HISTORY (Repository Pattern)
    // ==========================================

    public static void saveWorkoutHistory(String username, List<String> logs) {
        String filename = username + "_history.txt";

        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("--- Workout Session: " + java.time.LocalDate.now() + " ---");
            for (String log : logs) {
                out.println(log);
            }
            out.println("------------------------------------------");
            System.out.println("History saved to " + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // [THIS WAS MISSING] - This fixes the History Page
    public static String loadHistoryFile(String username) {
        String filename = username + "_history.txt";
        File file = new File(filename);

        System.out.println("Reading history from: " + file.getAbsolutePath()); // Debugging

        if (!file.exists()) {
            return "No history found. Complete a workout first!";
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error loading history.";
        }
        return content.toString();
    }

    // ==========================================
    // 3. USER PROFILE PERSISTENCE
    // ==========================================

    public static void saveUserProfile(com.model.User user) {
        String filename = user.getUsername() + "_profile.txt";

        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(user.getWeight());
            out.println(user.getHeight());
            out.println(user.getAge());
            out.println(user.getActivityLevel());
            out.println(user.getGoal());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserProfile(com.model.User user) {
        String filename = user.getUsername() + "_profile.txt";
        File file = new File(filename);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String weightStr = br.readLine();
            String heightStr = br.readLine();
            String ageStr = br.readLine();
            String activity = br.readLine();
            String goal = br.readLine();

            if (weightStr != null) user.setWeight(Double.parseDouble(weightStr));
            if (heightStr != null) user.setHeight(Double.parseDouble(heightStr));
            if (ageStr != null) user.setAge(Integer.parseInt(ageStr));
            if (activity != null) user.setActivityLevel(activity);
            if (goal != null) user.setGoal(goal);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading profile.");
        }
    }

    // ==========================================
    // 4. CALORIE TRACKER PERSISTENCE
    // ==========================================

    public static void saveDailyCalories(String username, double calories) {
        String filename = username + "_daily_log.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(java.time.LocalDate.now());
            out.println(calories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double loadDailyCalories(String username) {
        String filename = username + "_daily_log.txt";
        File file = new File(filename);
        if (!file.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String savedDate = br.readLine();
            String savedCalories = br.readLine();

            if (savedDate != null && savedDate.equals(java.time.LocalDate.now().toString())) {
                return Double.parseDouble(savedCalories);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading daily log.");
        }
        return 0;
    }

    // ==========================================
    // 5. PROGRESS OBSERVER RECORDS
    // ==========================================

    public static java.util.Map<String, Double> loadPersonalBests(String username) {
        String filename = username + "_records.txt";
        java.util.Map<String, Double> records = new java.util.HashMap<>();
        File file = new File(filename);

        if (!file.exists()) return records;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    records.put(parts[0], Double.parseDouble(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static void savePersonalBests(String username, java.util.Map<String, Double> records) {
        String filename = username + "_records.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (java.util.Map.Entry<String, Double> entry : records.entrySet()) {
                out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
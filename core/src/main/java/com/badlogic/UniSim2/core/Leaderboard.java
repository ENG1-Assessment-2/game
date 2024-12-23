package com.badlogic.UniSim2.core;

import com.google.gson.*;

import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    private static final ArrayList<Map.Entry<String, Integer>> SORTED_LEADERBOARD;
    private static final int MAX_ENTRIES = 10;
    private static final String JSON_FILE_PATH = "leaderboard.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Static Initializer
    static {
        SORTED_LEADERBOARD = new ArrayList<>();
        loadData();
    }

    // Private Constructor
    private Leaderboard() {
        // Prevent instantiation
    }

    // Load/Save Data
    private static void loadData() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
            if (jsonObject == null) {
                System.out.println("Leaderboard file was empty. Starting fresh...");
                return;
            }

            JsonArray leaderboardArray = jsonObject.getAsJsonArray("leaderboard");
            if (leaderboardArray != null) {
                for (JsonElement elem : leaderboardArray) {
                    JsonObject obj = elem.getAsJsonObject();
                    String playerName = obj.get("playerName").getAsString();
                    int score = obj.get("score").getAsInt();
                    SORTED_LEADERBOARD.add(new AbstractMap.SimpleEntry<>(playerName, score));
                }
                sort();
            } else {
                System.out.println("Leaderboard file was empty or otherwise malformed. Starting fresh...");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found. Initializing new leaderboard...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveData() {
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            JsonObject jsonObject = new JsonObject();
            JsonArray leaderboardArray = new JsonArray();

            for (Map.Entry<String, Integer> entry : SORTED_LEADERBOARD) {
                JsonObject obj = new JsonObject();
                obj.addProperty("playerName", entry.getKey());
                obj.addProperty("score", entry.getValue());
                leaderboardArray.add(obj);
            }

            jsonObject.add("leaderboard", leaderboardArray);
            GSON.toJson(jsonObject, writer);
            writer.flush();
            System.out.println("Leaderboard successfully saved.");
        } catch (IOException e) {
            System.err.println("Error writing to leaderboard file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Public Methods
    public static void updateScore(String playerName, int score) {
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative!");
        }

        Map.Entry<String, Integer> newEntry = Map.entry(playerName, score);
        SORTED_LEADERBOARD.add(newEntry);

        sort();

        if (SORTED_LEADERBOARD.size() > MAX_ENTRIES) {
            SORTED_LEADERBOARD.remove(SORTED_LEADERBOARD.size() - 1);
        }

        saveData();
    }

    public static void removePlayer(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }

        // Remove all entries with the given name
        SORTED_LEADERBOARD.removeIf(entry -> entry.getKey().equals(playerName));

        saveData(); // Persist changes
    }

    public static List<Integer> getPlayerRanks(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }

        List<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < SORTED_LEADERBOARD.size(); i++) {
            if (SORTED_LEADERBOARD.get(i).getKey().equals(playerName)) {
                ranks.add(i + 1); // 1-based rank
            }
        }
        return ranks;
    }

    public static List<Map.Entry<String, Integer>> getSortedLeaderboard() {
        return new ArrayList<>(SORTED_LEADERBOARD);
    }

    // Private Helpers
    private static void sort() {
        SORTED_LEADERBOARD.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));
    }
}

package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a leaderboard that keeps track of the top scores.
 */
public class Leaderboard {

    /**
     * Represents an entry in the leaderboard.
     */
    private static class Entry {

        String name;
        int score;

        /**
         * Constructs a new Entry with the given name and score.
         *
         * @param name The name of the player.
         * @param score The score of the entry.
         */
        Entry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    private final List<Entry> entries;

    public Leaderboard() {
        entries = new ArrayList<>();
    }

    /**
     * Adds a new entry to the leaderboard.
     *
     * @param name The name of the player.
     * @param score The score of the player.
     */
    public void addEntry(String name, int score) {
        entries.add(new Entry(name, score));
        // Sort entries in descending order of score
        Collections.sort(entries, Comparator.comparingInt(e -> -e.score));
        // Keep only the top 5 entries
        if (entries.size() > 5) {
            entries.remove(entries.size() - 1);
        }
    }

    /**
     * Returns a list of the top entries in the leaderboard.
     *
     * @return A list of strings representing the top entries.
     */
    public List<String> getEntries() {
        List<String> topEntries = new ArrayList<>();
        for (Entry entry : entries) {
            topEntries.add(entry.name + ": " + entry.score);
        }
        return topEntries;
    }
}

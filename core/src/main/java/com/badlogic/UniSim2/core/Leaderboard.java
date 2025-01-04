package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {

    private static class Entry {

        String name;
        int score;

        Entry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    private final List<Entry> entries;

    public Leaderboard() {
        entries = new ArrayList<>();
    }

    public void addEntry(String name, int score) {
        entries.add(new Entry(name, score));
        Collections.sort(entries, Comparator.comparingInt(e -> -e.score));
        if (entries.size() > 5) {
            entries.remove(entries.size() - 1);
        }
    }

    public List<String> getEntries() {
        List<String> topEntries = new ArrayList<>();
        for (Entry entry : entries) {
            topEntries.add(entry.name + ": " + entry.score);
        }
        return topEntries;
    }
}

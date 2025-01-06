package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Leaderboard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {

    @Test
    void testAddSingleEntry() {
        Leaderboard leaderboard = new Leaderboard();

        leaderboard.addEntry("A", 100);

        List<String> entries = leaderboard.getEntries();
        assertEquals(1, entries.size(), "Leaderboard should have 1 entry");
        assertEquals("A: 100", entries.get(0),
            "The single entry should be 'A: 100'");
    }

    @Test
    void testAddMultipleEntriesAndSorting() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addEntry("B", 50);
        leaderboard.addEntry("C", 200);
        leaderboard.addEntry("A", 100);

        List<String> entries = leaderboard.getEntries();

        // Expect descending order by score: C (200), A (100), B (50)
        assertEquals(3, entries.size(), "Leaderboard should have 3 entries");
        assertEquals("C: 200", entries.get(0),
            "Highest score should be first");
        assertEquals("A: 100", entries.get(1));
        assertEquals("B: 50", entries.get(2));
    }

    @Test
    void testTopFiveOnly() {
        Leaderboard leaderboard = new Leaderboard();

        // Add six entries; the sixth has a lower score and should be removed
        leaderboard.addEntry("A", 100);
        leaderboard.addEntry("B", 90);
        leaderboard.addEntry("C", 80);
        leaderboard.addEntry("D", 70);
        leaderboard.addEntry("E", 60);
        leaderboard.addEntry("F", 50); // Should end up removed if it's the lowest

        List<String> entries = leaderboard.getEntries();
        assertEquals(5, entries.size(), "Leaderboard should only keep top 5 entries");

        // Ensure 'F' didn't make it
        for (String entry : entries) {
            assertFalse(entry.startsWith("F:"),
                "F: 50 should not be in the top 5");
        }
    }

    @Test
    void testEmptyLeaderboard() {
        Leaderboard leaderboard = new Leaderboard();

        List<String> entries = leaderboard.getEntries();
        assertTrue(entries.isEmpty(), "New leaderboard should be empty");
    }
}

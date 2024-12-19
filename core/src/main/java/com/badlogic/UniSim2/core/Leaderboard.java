package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    private static ArrayList<Map.Entry<String, Integer>> sortedLeaderboard;
    final static int MAX_ENTRIES = 10;

    public Leaderboard(){
        sortedLeaderboard = new ArrayList<>();
    }

    public void updateScore(String playerName, Integer score){
        if (playerName == null || playerName.isEmpty()){
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }
        if (score < 0){
            throw new IllegalArgumentException("Score cannot be negative!");
        }

        Map.Entry<String, Integer> newEntry = Map.entry(playerName, score);

        if (sortedLeaderboard.size() < MAX_ENTRIES){
            sortedLeaderboard.add(newEntry);
            sort();
        } else {
            Integer lowestScore = sortedLeaderboard.get(sortedLeaderboard.size() - 1).getValue();
            if (score > lowestScore){
                sortedLeaderboard.remove(sortedLeaderboard.size() - 1);
                sortedLeaderboard.add(newEntry);
                sort();
            }
        }

    }

    private void sort(){
        sortedLeaderboard.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));
    }

    public static void removePlayer(String playerName){
        if (playerName == null || playerName.isEmpty()){
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }

        sortedLeaderboard.removeIf(entry -> entry.getKey().equals(playerName));
    }

    public static List<Integer> getPlayerRanks(String playerName){
        if (playerName == null || playerName.isEmpty()){
            throw new IllegalArgumentException("Player's name cannot be null or empty!");
        }

        List<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < sortedLeaderboard.size(); i++){
            if (sortedLeaderboard.get(i).getKey().equals(playerName)) {
                ranks.add(i + 1); // 1-based index
            }
        }
        return ranks;
    }

    public static ArrayList<Map.Entry<String, Integer>> getSortedLeaderboard() {
        return sortedLeaderboard;
    }

}

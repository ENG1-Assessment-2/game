package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

/**
 * Represents the First Building achievement. This achievement is awarded when
 * the player places their first building.
 */
public class FirstBuilding extends Achievement {

    private static FirstBuilding instance;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private FirstBuilding() {
    }

    /**
     * Returns the singleton instance of the FirstBuilding achievement.
     *
     * @return The singleton instance.
     */
    public static FirstBuilding getInstance() {
        if (instance == null) {
            instance = new FirstBuilding();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "First Building!";
    }

    @Override
    public String getDescription() {
        return "Place one building.";
    }

    @Override
    public boolean getCompleted(Round round) {
        return !round.getPlacedBuildings().isEmpty();
    }
}

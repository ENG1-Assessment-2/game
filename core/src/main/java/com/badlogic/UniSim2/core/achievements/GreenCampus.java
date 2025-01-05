package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.BuildingType;

/**
 * Represents the Green Campus achievement. This achievement is awarded when the
 * player places five nature plots.
 */
public class GreenCampus extends Achievement {

    private static GreenCampus instance;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private GreenCampus() {
    }

    /**
     * Returns the singleton instance of the GreenCampus achievement.
     *
     * @return The singleton instance.
     */
    public static GreenCampus getInstance() {
        if (instance == null) {
            instance = new GreenCampus();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "Green Campus!";
    }

    @Override
    public String getDescription() {
        return "Place five nature plots.";
    }

    @Override
    public boolean getCompleted(Round round) {
        return round.getBuildingCount(BuildingType.NATURE) >= 5;
    }
}

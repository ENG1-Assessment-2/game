package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

/**
 * Represents the Academic Architect achievement. This achievement is awarded
 * when the player places 25 buildings.
 */
public class AcademicArchitect extends Achievement {

    private static AcademicArchitect instance;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private AcademicArchitect() {
    }

    /**
     * Returns the singleton instance of the AcademicArchitect achievement.
     *
     * @return The singleton instance.
     */
    public static AcademicArchitect getInstance() {
        if (instance == null) {
            instance = new AcademicArchitect();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "Academic Architect!";
    }

    @Override
    public String getDescription() {
        return "Place twenty-five buildings.";
    }

    @Override
    public boolean getCompleted(Round round) {
        return round.getPlacedBuildings().size() >= 25;
    }
}

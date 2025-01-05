package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

/**
 * Represents an abstract achievement in the game. Subclasses should implement
 * the getName, getDescription, and getCompleted methods.
 */
public abstract class Achievement {

    public abstract String getName();

    public abstract String getDescription();

    /**
     * Checks if the achievement has been completed.
     *
     * @param round The round to check for the achievement.
     * @return True if the achievement is completed, false otherwise.
     */
    public abstract boolean getCompleted(Round round);
}

package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

public class FirstBuilding extends Achievement {

    private static FirstBuilding instance;

    private FirstBuilding() {
    }

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

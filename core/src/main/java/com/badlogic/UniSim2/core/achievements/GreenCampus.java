package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.BuildingType;

public class GreenCampus extends Achievement {

    private static GreenCampus instance;

    private GreenCampus() {
    }

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

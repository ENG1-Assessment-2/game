package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

public class AcademicArchitect extends Achievement {

    private static AcademicArchitect instance;

    private AcademicArchitect() {
    }

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

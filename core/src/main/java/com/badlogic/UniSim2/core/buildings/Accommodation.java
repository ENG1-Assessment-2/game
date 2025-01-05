package com.badlogic.UniSim2.core.buildings;

/**
 * Represents an accommodation building in the game.
 */
class Accommodation extends Building {

    public Accommodation(int row, int col) {
        super(
                BuildingType.ACCOMMODATION.getWidth(),
                BuildingType.ACCOMMODATION.getHeight(),
                row, col,
                BuildingType.ACCOMMODATION
        );
    }

    @Override
    public String getName() {
        return "Accommodation";
    }

    @Override
    public int getCost() {
        return 240 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Accommodation for students and staff.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 40";
        d += "\n <=5 tiles from bar: -100";
        return d;
    }
}

package com.badlogic.UniSim2.core.buildings;

/**
 * Represents a food zone building in the game.
 */
class FoodZone extends Building {

    public FoodZone(int row, int col) {
        super(
                BuildingType.FOODZONE.getWidth(),
                BuildingType.FOODZONE.getHeight(),
                row, col,
                BuildingType.FOODZONE
        );
    }

    @Override
    public String getName() {
        return "Food Zone";
    }

    @Override
    public int getCost() {
        return 200 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Foodzone for students to eat in.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 50";
        return d;
    }
}

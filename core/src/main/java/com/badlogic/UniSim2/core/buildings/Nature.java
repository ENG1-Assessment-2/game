package com.badlogic.UniSim2.core.buildings;

/**
 * Represents a nature plot in the game.
 */
class Nature extends Building {

    public Nature(int row, int col) {
        super(
                BuildingType.NATURE.getWidth(),
                BuildingType.NATURE.getHeight(),
                row, col,
                BuildingType.NATURE
        );
    }

    @Override
    public String getName() {
        return "Nature";
    }

    @Override
    public int getCost() {
        return 100 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Nature for students to wander amongst.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 10";
        d += "\n next to accommodation: +30";
        return d;
    }
}

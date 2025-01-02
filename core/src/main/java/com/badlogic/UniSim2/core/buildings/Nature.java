package com.badlogic.UniSim2.core.buildings;

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
        return 50 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Nature for students to wander amongst.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 10";
        return d;
    }
}

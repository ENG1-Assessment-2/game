package com.badlogic.UniSim2.core.buildings;

class Bar extends Building {

    public Bar(int row, int col) {
        super(
                BuildingType.BAR.getWidth(),
                BuildingType.BAR.getHeight(),
                row, col,
                BuildingType.BAR
        );
    }

    @Override
    public String getName() {
        return "Bar";
    }

    @Override
    public int getCost() {
        return 300 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Bar for students to drink at.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 60";
        return d;
    }
}

package com.badlogic.UniSim2.core.buildings;

public class Area extends Building {

    public Area(int width, int height, int row, int col) {
        super(width, height, row, col, null);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

}

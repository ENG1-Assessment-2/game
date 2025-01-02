package com.badlogic.UniSim2.core.buildings;

public class Path extends Building {

    public Path(int width, int height, int row, int col) {
        super(width, height, row, col, null);
    }

    @Override
    public String getName() {
        return "Path";
    }

    @Override
    public int getCost() {
        return 0;
    }
}

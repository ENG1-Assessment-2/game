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
}

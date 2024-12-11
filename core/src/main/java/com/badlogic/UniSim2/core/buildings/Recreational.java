package com.badlogic.UniSim2.core.buildings;

class Recreational extends Building {

    public Recreational(int row, int col) {
        super(
                BuildingType.RECREATIONAL.getWidth(),
                BuildingType.RECREATIONAL.getHeight(),
                row, col,
                BuildingType.RECREATIONAL
        );
    }
}

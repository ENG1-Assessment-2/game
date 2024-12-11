package com.badlogic.UniSim2.core.buildings;

class Library extends Building {

    public Library(int row, int col) {
        super(
                BuildingType.LIBRARY.getWidth(),
                BuildingType.LIBRARY.getHeight(),
                row, col,
                BuildingType.LIBRARY
        );
    }
}

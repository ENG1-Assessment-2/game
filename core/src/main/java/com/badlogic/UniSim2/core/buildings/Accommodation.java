package com.badlogic.UniSim2.core.buildings;

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
    public int getCost() {
        return 200 * 1000;
    }
}

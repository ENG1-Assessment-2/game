package com.badlogic.UniSim2.core.buildings;

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
    public int getCost() {
        return 250 * 1000;
    }
}

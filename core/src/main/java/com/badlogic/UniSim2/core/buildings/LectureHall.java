package com.badlogic.UniSim2.core.buildings;

class LectureHall extends Building {

    public LectureHall(int row, int col) {
        super(
                BuildingType.LECTUREHALL.getWidth(),
                BuildingType.LECTUREHALL.getHeight(),
                row, col,
                BuildingType.LECTUREHALL
        );
    }

    @Override
    public String getName() {
        return "Lecture Hall";
    }

    @Override
    public int getCost() {
        return 500 * 1000;
    }

    @Override
    public String getDescription() {
        String d = "Lecture Hall for students to learn.";
        d += "\nCost: " + getCost();
        d += "\nDemolish: " + getDemolishCost();
        d += "\nMove: " + getMoveCost();
        d += "\nScore: 100";
        return d;
    }
}

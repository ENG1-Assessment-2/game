package com.badlogic.UniSim2.core.buildings;

class Course extends Building {

    public Course(int row, int col) {
        super(
                BuildingType.COURSE.getWidth(),
                BuildingType.COURSE.getHeight(),
                row, col,
                BuildingType.COURSE
        );
    }
}

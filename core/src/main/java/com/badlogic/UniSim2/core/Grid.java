package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.core.buildings.Path;

public class Grid {

    private final int rows;
    private final int cols;
    private final List<Building> buildings;
    private final List<Path> paths;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.buildings = new ArrayList<>();
        this.paths = new ArrayList<>();
    }

    public List<Building> getPlacedBuildings() {
        return buildings;
    }

    public void placeBuilding(BuildingType type, int row, int col) throws BuildingPlacementException {
        if (!getCanPlace(type, row, col)) {
            throw new BuildingPlacementException("Cannot place building at row " + row + " and col " + col);
        }
        Building building = type.create(row, col);
        buildings.add(building);
    }

    public int getBuildingCount(BuildingType type) {
        int count = 0;
        for (Building building : buildings) {
            if (building.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    public boolean getCanPlace(BuildingType type, int row, int col) {
        if (row - type.getHeight() < 0
                || row > rows
                || col < 0
                || col + type.getWidth() > cols) {
            return false;
        }

        for (Building building : buildings) {
            if (building.overlaps(type, row, col)) {
                return false;
            }
        }

        for (Path path : paths) {
            if (path.overlaps(type, row, col)) {
                return false;
            }
        }

        return true;
    }

    public void placePath(int row, int col, int width, int height) {
        paths.add(new Path(width, height, row, col));
    }
}

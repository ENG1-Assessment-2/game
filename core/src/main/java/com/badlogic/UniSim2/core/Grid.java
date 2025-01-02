package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.UniSim2.core.buildings.Area;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
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

    public void moveBuilding(Building building, int row, int col) throws BuildingPlacementException {
        if (!getCanPlace(building.getType(), row, col)) {
            throw new BuildingPlacementException("Cannot place building at row " + row + " and col " + col);
        }

        placeBuilding(building.getType(), row, col);
        buildings.remove(building);
    }

    public List<Building> getPlacedBuildings() {
        return buildings;
    }

    public Building removeBuilding(int row, int col) throws BuildingRemovalException {
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            if (isBuildingAt(building, row, col)) {
                buildings.remove(i);
                return building;
            }
        }
        throw new BuildingRemovalException("There is no building at row " + row + " col " + col);
    }

    public boolean getIsBuildingAt(int row, int col) {
        for (Building building : buildings) {
            if (isBuildingAt(building, row, col)) {
                return true;
            }
        }
        return false;
    }

    public Building getBuildingAt(int row, int col) {
        for (Building building : buildings) {
            if (isBuildingAt(building, row, col)) {
                return building;
            }
        }
        return null;
    }

    private boolean isBuildingAt(Building building, int row, int col) {
        boolean inRow = building.getRow() >= row && building.getRow() - building.getHeight() <= row;
        boolean inCol = building.getCol() <= col && building.getCol() + building.getWidth() >= col;
        return inRow && inCol;
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

    public boolean getBuildingsAreWithinRadius(Building a, Building b, int radius) {
        Building area = new Area(a.getWidth() + (2 * radius), a.getHeight() + (2 * radius), a.getRow() + radius, a.getCol() - radius);
        return area.overlaps(b.getType(), b.getRow(), b.getCol());
    }
}

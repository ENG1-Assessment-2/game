package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.UniSim2.core.buildings.Area;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.core.buildings.Path;

/**
 * Represents the grid on which buildings and paths are placed.
 */
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

    /**
     * Places a building on the grid.
     *
     * @param type The type of building to place.
     * @param row The row position to place the building.
     * @param col The column position to place the building.
     * @throws BuildingPlacementException If the building cannot be placed.
     */
    public void placeBuilding(BuildingType type, int row, int col) throws BuildingPlacementException {
        if (!getCanPlace(type, row, col)) {
            throw new BuildingPlacementException("Cannot place building at row " + row + " and col " + col);
        }
        Building building = type.create(row, col);
        buildings.add(building);
    }

    /**
     * Moves a building to a new position on the grid.
     *
     * @param building The building to move.
     * @param row The new row position.
     * @param col The new column position.
     * @throws BuildingPlacementException If the building cannot be moved.
     */
    public void moveBuilding(Building building, int row, int col) throws BuildingPlacementException {
        if (!getCanPlace(building.getType(), row, col)) {
            throw new BuildingPlacementException("Cannot place building at row " + row + " and col " + col);
        }

        placeBuilding(building.getType(), row, col);
        buildings.remove(building);
    }

    /**
     * Removes a building from the grid.
     *
     * @param row The row position of the building to remove.
     * @param col The column position of the building to remove.
     * @return The removed building.
     * @throws BuildingRemovalException If the building cannot be removed.
     */
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

    /**
     * Returns the count of buildings of a specific type.
     *
     * @param type The type of building.
     * @return The count of buildings of the specified type.
     */
    public int getBuildingCount(BuildingType type) {
        int count = 0;
        for (Building building : buildings) {
            if (building.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Places a path on the grid.
     *
     * @param row The row position of the path.
     * @param col The column position of the path.
     * @param width The width of the path.
     * @param height The height of the path.
     */
    public void placePath(int row, int col, int width, int height) {
        paths.add(new Path(width, height, row, col));
    }

    /**
     * Checks if a building can be placed at the specified position.
     *
     * @param type The type of building.
     * @param row The row position.
     * @param col The column position.
     * @return True if the building can be placed, false otherwise.
     */
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

    /**
     * Checks if two buildings are within a specified radius of each other.
     *
     * @param a The first building.
     * @param b The second building.
     * @param radius The radius to check within.
     * @return True if the buildings are within the radius, false otherwise.
     */
    public boolean getBuildingsAreWithinRadius(Building a, Building b, int radius) {
        Building area = new Area(a.getWidth() + (2 * radius), a.getHeight() + (2 * radius), a.getRow() + radius, a.getCol() - radius);
        return area.overlaps(b.getType(), b.getRow(), b.getCol());
    }

    /**
     * Checks if a building is at the specified position.
     *
     * @param building The building to check.
     * @param row The row position.
     * @param col The column position.
     * @return True if the building is at the position, false otherwise.
     */
    private boolean isBuildingAt(Building building, int row, int col) {
        boolean inRow = building.getRow() >= row && building.getRow() - building.getHeight() <= row;
        boolean inCol = building.getCol() <= col && building.getCol() + building.getWidth() >= col;
        return inRow && inCol;
    }

    public List<Building> getPlacedBuildings() {
        return buildings;
    }

    /**
     * Checks if there is a building at the specified position.
     *
     * @param row The row position.
     * @param col The column position.
     * @return True if there is a building at the position, false otherwise.
     */
    public boolean getIsBuildingAt(int row, int col) {
        for (Building building : buildings) {
            if (isBuildingAt(building, row, col)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the building at the specified position.
     *
     * @param row The row position.
     * @param col The column position.
     * @return The building at the position, or null if there is no building.
     */
    public Building getBuildingAt(int row, int col) {
        for (Building building : buildings) {
            if (isBuildingAt(building, row, col)) {
                return building;
            }
        }
        return null;
    }
}

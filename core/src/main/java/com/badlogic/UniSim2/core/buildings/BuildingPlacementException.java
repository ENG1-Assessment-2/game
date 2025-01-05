package com.badlogic.UniSim2.core.buildings;

/**
 * Exception thrown when a building cannot be placed on the grid.
 */
public class BuildingPlacementException extends Exception {

    public BuildingPlacementException(String message) {
        super(message);
    }
}

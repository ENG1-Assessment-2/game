package com.badlogic.UniSim2.core.buildings;

/**
 * Exception thrown when a building cannot be removed from the grid.
 */
public class BuildingRemovalException extends Exception {

    public BuildingRemovalException(String message) {
        super(message);
    }
}

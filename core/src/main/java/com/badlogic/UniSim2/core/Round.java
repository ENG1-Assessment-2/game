package com.badlogic.UniSim2.core;

import java.util.List;

import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.core.events.EventManager;
import com.badlogic.UniSim2.resources.Consts;

public class Round {

    private final Grid grid;
    private float elapsedTime;
    private boolean isPaused;
    private BuildingType selectedBuildingType;
    private final EventManager eventManager;

    public Round() {
        this.grid = new Grid(38, 66);
        this.elapsedTime = 0;
        this.isPaused = false;
        this.selectedBuildingType = null;
        this.eventManager = new EventManager(this);

        grid.placePath(38, 0, 2, 38);
        grid.placePath(38, 17, 2, 38);
        grid.placePath(38, 51, 2, 38);
        grid.placePath(38, 37, 2, 28);

        grid.placePath(12, 0, 66, 2);
        grid.placePath(24, 0, 66, 2);
    }

    public void update(float delta) {
        if (elapsedTime < Consts.MAX_TIME && !isPaused) {
            elapsedTime += delta;
            if (!eventManager.isFinished()){
                eventManager.eventLauncher(elapsedTime);
            }
        }
    }

    public List<Building> getPlacedBuildings() {
        return grid.getPlacedBuildings();
    }

    public void placeBuilding(BuildingType type, int row, int col) throws BuildingPlacementException {
        grid.placeBuilding(type, row, col);
    }

    public boolean getCanPlace(BuildingType type, int row, int col) {
        return grid.getCanPlace(type, row, col);
    }

    public int getBuildingCount(BuildingType type) {
        return grid.getBuildingCount(type);
    }

    public void selectBuildingType(BuildingType type) {
        this.selectedBuildingType = type;
    }

    public BuildingType getSelectedBuildingType() {
        return selectedBuildingType;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isOver() {
        return elapsedTime >= Consts.MAX_TIME;
    }

}

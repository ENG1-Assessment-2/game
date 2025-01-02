package com.badlogic.UniSim2.core;

import java.util.List;

import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Consts;

public class Round {

    private final Grid grid;
    private float elapsedTime;
    private boolean isPaused;
    private BuildingType selectedBuildingType;
    private int funds;
    private float timeSinceLastPay;

    public Round() {
        this.grid = new Grid(38, 66);
        this.elapsedTime = 0;
        this.isPaused = false;
        this.selectedBuildingType = null;
        this.funds = 500000;

        grid.placePath(38, 0, 2, 38);
        grid.placePath(38, 17, 2, 38);
        grid.placePath(38, 51, 2, 38);
        grid.placePath(38, 37, 2, 28);

        grid.placePath(12, 0, 66, 2);
        grid.placePath(24, 0, 66, 2);
    }

    public void update(float delta) {
        if (isPaused || elapsedTime >= Consts.MAX_TIME) {
            return;
        }

        elapsedTime += delta;

        if (timeSinceLastPay >= 3) {
            funds += 30000;
            timeSinceLastPay = 0;
        } else {
            timeSinceLastPay += delta;
        }
    }

    public List<Building> getPlacedBuildings() {
        return grid.getPlacedBuildings();
    }

    public void placeBuilding(BuildingType type, int row, int col) throws BuildingPlacementException {
        int buildingCost = type.create(0, 0).getCost();
        if (funds < buildingCost) {
            throw new BuildingPlacementException("Not enough funds to place building");
        }
        funds -= buildingCost;
        grid.placeBuilding(type, row, col);
    }

    public Building removeBuilding(int row, int col) throws BuildingRemovalException {
        return grid.removeBuilding(row, col);
    }

    public boolean getIsBuildingAt(int row, int col) {
        return grid.getIsBuildingAt(row, col);
    }

    public boolean getCanPlace(BuildingType type, int row, int col) {
        return grid.getCanPlace(type, row, col);
    }

    public boolean getCanAfford(BuildingType type) {
        return funds >= type.create(0, 0).getCost();
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

    public int getFunds() {
        return funds;
    }
}

package com.badlogic.UniSim2.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.UniSim2.core.achievements.AcademicArchitect;
import com.badlogic.UniSim2.core.achievements.Achievement;
import com.badlogic.UniSim2.core.achievements.FirstBuilding;
import com.badlogic.UniSim2.core.achievements.GreenCampus;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Consts;

public class Round {

    private final Set<Achievement> ALL_ACHIEVEMENTS = new HashSet<>(Arrays.asList(
            FirstBuilding.getInstance(),
            AcademicArchitect.getInstance(),
            GreenCampus.getInstance()
    ));

    private final Grid grid;
    private float elapsedTime;
    private boolean isPaused;
    private BuildingType selectedBuildingType;
    private Building movingBuilding;
    private int funds;
    private float timeSinceLastPay;
    private final Set<Achievement> completedAchievements;

    public Round() {
        this.grid = new Grid(38, 66);
        this.elapsedTime = 0;
        this.isPaused = false;
        this.selectedBuildingType = null;
        this.funds = 5000000;
        this.timeSinceLastPay = 0;
        this.completedAchievements = new HashSet<>();

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

        for (Achievement achievement : ALL_ACHIEVEMENTS) {
            if (!completedAchievements.contains(achievement) && achievement.getCompleted(this)) {
                completedAchievements.add(achievement);
            }
        }
    }

    public int getStudentSatisfaction() {
        return (int) (getStudentSatisfactionFromBuildings() * getDiversityFactor());
    }

    private int getStudentSatisfactionFromBuildings() {
        int s = 0;

        // each lecture hall adds 100
        int numberOfLectureHalls = grid.getBuildingCount(BuildingType.LECTUREHALL);
        s += numberOfLectureHalls * 100;

        // each accommodation adds 40
        int numberOfAccommodations = grid.getBuildingCount(BuildingType.ACCOMMODATION);
        s += numberOfAccommodations * 40;

        // each bar adds 60
        int numberOfBars = grid.getBuildingCount(BuildingType.BAR);
        s += numberOfBars * 60;

        // each nature adds 10
        int numberOfNatures = grid.getBuildingCount(BuildingType.NATURE);
        s += numberOfNatures * 10;

        // each food zone adds 50
        int numberOfFoodZones = grid.getBuildingCount(BuildingType.FOODZONE);
        s += numberOfFoodZones * 50;

        // considering the distance between buildings
        for (Building bA : grid.getPlacedBuildings()) {
            for (Building bB : grid.getPlacedBuildings()) {

                // each bar within 5 tiles of an accommodation removes 100
                if (bA.getType() == BuildingType.BAR && bB.getType() == BuildingType.ACCOMMODATION) {
                    if (grid.getBuildingsAreWithinRadius(bA, bB, 5)) {
                        s -= 100;
                    }
                }

                // each bar within 4 tiles of a lecture hall adds 60
                if (bA.getType() == BuildingType.BAR && bB.getType() == BuildingType.LECTUREHALL) {
                    if (grid.getBuildingsAreWithinRadius(bA, bB, 4)) {
                        s += 60;
                    }
                }

                // each nature adjacent to an accommodation adds 30
                if (bA.getType() == BuildingType.NATURE && bB.getType() == BuildingType.ACCOMMODATION) {
                    if (grid.getBuildingsAreWithinRadius(bA, bB, 1)) {
                        s += 30;
                    }
                }
            }
        }

        return s;
    }

    private double getDiversityFactor() {
        List<Integer> buildingCounts = new ArrayList<>();
        for (BuildingType type : BuildingType.values()) {
            buildingCounts.add(grid.getBuildingCount(type));
        }

        float mean = grid.getPlacedBuildings().size() / BuildingType.values().length;
        double variance = 0.0;
        for (int count : buildingCounts) {
            variance += Math.pow(count - mean, 2);
        }
        variance /= BuildingType.values().length;
        double maxVariance = Math.pow(grid.getPlacedBuildings().size() - mean, 2);
        double normalisedVariance = 1.0 - (variance / maxVariance);
        normalisedVariance = Math.max(0, normalisedVariance);
        return normalisedVariance;
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
        Building building = grid.getBuildingAt(row, col);
        if (building == null) {
            throw new BuildingRemovalException("There is no building at row " + row + " col " + col);
        }
        int demolishCost = building.getDemolishCost();
        if (funds < demolishCost) {
            throw new BuildingRemovalException("Not enough funds to remove building");
        }
        funds -= demolishCost;
        return grid.removeBuilding(row, col);
    }

    public void selectBuildingToMove(int row, int col) {
        if (movingBuilding != null) {
            return;
        }

        try {
            Building building = grid.getBuildingAt(row, col);
            movingBuilding = building;

            grid.removeBuilding(row, col);
        } catch (BuildingRemovalException e) {
        }
    }

    public void cancelMoveBuilding() {
        if (movingBuilding == null) {
            return;
        }

        try {
            grid.placeBuilding(movingBuilding.getType(), movingBuilding.getRow(), movingBuilding.getCol());
            movingBuilding = null;
        } catch (BuildingPlacementException e) {
        }
    }

    public boolean getIsMovingBuilding() {
        return movingBuilding != null;
    }

    public Building getMovingBuilding() {
        return movingBuilding;
    }

    public void moveBuilding(int row, int col) throws BuildingPlacementException {
        if (movingBuilding == null) {
            return;
        }

        int moveCost = movingBuilding.getMoveCost();
        if (funds < moveCost) {
            throw new BuildingPlacementException("Not enough funds to move building");
        }

        funds -= moveCost;
        grid.moveBuilding(movingBuilding, row, col);
        movingBuilding = null;
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

    public Set<Achievement> getCompletedAchievements() {
        return completedAchievements;
    }

    public Set<Achievement> getAllAchievements() {
        return ALL_ACHIEVEMENTS;
    }
}

package com.badlogic.UniSim2.core;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.gui.Textbox;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Round {

    private final Grid grid;
    private float elapsedTime;
    private boolean isPaused;
    private BuildingType selectedBuildingType;
    private Textbox achievementTextbox;
    private boolean showAchievements;
    private List<Achievement> achievements;

    public Round() {
        this.grid = new Grid(38, 66);
        this.elapsedTime = 0;
        this.isPaused = false;
        this.selectedBuildingType = null;
        this.achievementTextbox = new Textbox("", 100, 200);
        this.showAchievements = false;
        this.achievements = new ArrayList<>();

        // Initialize achievements
        achievements.add(new Achievement("First Building", "Place your first building."));
        achievements.add(new Achievement("100 Points", "Score 100 points."));

        grid.placePath(38, 0, 2, 38);
        grid.placePath(38, 17, 2, 38);
        grid.placePath(38, 51, 2, 38);
        grid.placePath(38, 37, 2, 28);

        grid.placePath(12, 0, 66, 2);
        grid.placePath(24, 0, 66, 2);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            togglePause();
            showAchievements = !showAchievements;
            if (showAchievements) {
                StringBuilder achievementText = new StringBuilder("Achievements:\n");
                for (Achievement achievement : achievements) {
                    achievementText.append(achievement.getName()).append(": ").append(achievement.getDescription()).append(achievement.isUnlocked() ? " (Unlocked)\n" : " (Locked)\n");
                }
                achievementTextbox.setText(achievementText.toString());
            }
        }

        if (elapsedTime < Consts.MAX_TIME && !isPaused) {
            elapsedTime += delta;
        }
    }

    public void render(SpriteBatch batch) {
        if (showAchievements) {
            achievementTextbox.render(batch);
        }
    }

    private void unlockAchievement(String achievementName) {
        for (Achievement achievement : achievements) {
            if (achievement.getName().equals(achievementName) && !achievement.isUnlocked()) {
                achievement.unlock();
                break;
            }
        }
    }

    public List<Building> getPlacedBuildings() {
        return grid.getPlacedBuildings();
    }

    public void placeBuilding(BuildingType type, int row, int col) throws BuildingPlacementException {
        grid.placeBuilding(type, row, col);
        unlockAchievement("First Building");
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

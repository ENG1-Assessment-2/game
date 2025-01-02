package com.badlogic.UniSim2.core;

import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.gui.Textbox;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class Achievements {

    private final Map<String, Boolean> achievements;
    private final Textbox textbox;
    private boolean showAchievements;
    private final Round round;

    public Achievements(Round round) {
        this.round = round;
        achievements = new HashMap<>();
        achievements.put("Satisfaction Star! (Reach x student satisfaction)", false);
        achievements.put("Here Comes The Money! (earn £100m)", false);
        achievements.put("Academic Architect! (place 25 buildings)", false);
        achievements.put("Green Campus! (place 5 nature plots)", false);
        achievements.put("First Building! (place 1 building)", false);

        textbox = new Textbox("", 87, 470, 400);
        showAchievements = false;
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Keys.A)) {
            showAchievements = !showAchievements;
            updateTextbox();
        }
        updateAchievements();
    }

    private void updateTextbox() {
        StringBuilder text = new StringBuilder("Achievements:\n");
        for (Map.Entry<String, Boolean> entry : achievements.entrySet()) {
            text.append(entry.getKey()).append(": ").append(entry.getValue() ? "Unlocked" : "Locked").append("\n");
        }
        textbox.setText(text.toString());
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (showAchievements) {
            textbox.render();
        }
    }

    public void unlockAchievement(String achievement) {
        if (achievements.containsKey(achievement)) {
            achievements.put(achievement, true);
        }
    }

    private void updateAchievements() {
        int totalBuildings = round.getPlacedBuildings().size();
        if (totalBuildings >= 1) {
            unlockAchievement("First Building! (place 1 building)");
        }
        if (totalBuildings >= 25) {
            unlockAchievement("Academic Architect! (place 25 buildings)");
        }

        int natureBuildings = round.getBuildingCount(BuildingType.NATURE);
        if (natureBuildings >= 5) {
            unlockAchievement("Green Campus! (place 5 nature plots)");
        }
    }

    public void dispose() {
        textbox.dispose();
    }
}
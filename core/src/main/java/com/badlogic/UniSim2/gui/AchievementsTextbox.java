package com.badlogic.UniSim2.gui;

import java.util.Set;

import com.badlogic.UniSim2.core.achievements.Achievement;
import com.badlogic.UniSim2.resources.Consts;

public class AchievementsTextbox extends TextboxActor {

    public AchievementsTextbox() {
        super("", 30, Consts.WORLD_HEIGHT - 30, Consts.WORLD_WIDTH - 60);
    }

    public void updateText(Set<Achievement> completedAchievements, Set<Achievement> allAchievements) {
        String text = "Completed Achievements:\n";
        for (Achievement achievement : completedAchievements) {
            text += achievement.getName() + ": " + achievement.getDescription() + "\n";
        }
        text += "\n\nIncomplete Achievements:\n";
        for (Achievement achievement : allAchievements) {
            if (!completedAchievements.contains(achievement)) {
                text += achievement.getName() + ": " + achievement.getDescription() + "\n";
            }
        }
        setText(text);
    }
}

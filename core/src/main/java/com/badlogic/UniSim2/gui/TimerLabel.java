package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * A label that displays the elapsed time of the current round in the game.
 */
public class TimerLabel extends Label {

    private final Round round;

    public TimerLabel(Round round, Skin skin) {
        super("0.0 years", skin);
        this.round = round;

        setFontScale(2.5f);
        setAlignment(Align.center);
        setColor(Consts.TIMER_COLOR);
        setPosition(Consts.TIMER_X, Consts.TIMER_Y, Align.center);
    }

    /**
     * Updates the label text based on the round's state.
     */
    public void update() {
        float elapsedTime = round.getElapsedTime();
        boolean isPaused = round.isPaused();
        setText(isPaused ? "PAUSED" : formatTime(elapsedTime));
    }

    /**
     * Formats the elapsed time into a string representing the remaining years.
     *
     * @param time The elapsed time.
     * @return A formatted string representing the remaining years.
     */
    private String formatTime(float time) {
        int maxTime = Consts.MAX_TIME;
        float remainingTime = maxTime - time;
        float yearsRemaining = (remainingTime / maxTime) * 5;
        return String.format("%.1f years", yearsRemaining);
    }
}

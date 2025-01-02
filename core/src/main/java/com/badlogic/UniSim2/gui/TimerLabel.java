package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

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

    public void update() {
        float elapsedTime = round.getElapsedTime();
        boolean isPaused = round.isPaused();
        setText(isPaused ? "PAUSED" : formatTime(elapsedTime));
    }

    private String formatTime(float time) {
        int maxTime = Consts.MAX_TIME;
        float remainingTime = maxTime - time;
        float yearsRemaining = (remainingTime / maxTime) * 5;
        return String.format("%.1f years", yearsRemaining);
    }
}

package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class SatisfactionLabel extends Label {

    private final Round round;

    public SatisfactionLabel(Round round, Skin skin) {
        super("Score:\n0", skin);
        this.round = round;

        setFontScale(2.5f);
        setAlignment(Align.center);
        setColor(Consts.SATISFACTION_COLOR);
        setPosition(Consts.SATISFACTION_X, Consts.SATISFACTION_Y, Align.center);
    }

    public void update() {
        setText("Score:\n" + round.getStudentSatisfaction());
    }
}

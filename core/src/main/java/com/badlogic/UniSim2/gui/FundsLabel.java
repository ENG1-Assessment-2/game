package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * A label that displays the current funds.
 */
public class FundsLabel extends Label {

    private final Round round;

    public FundsLabel(Round round, Skin skin) {
        super("£0", skin);
        this.round = round;

        setFontScale(2.5f);
        setAlignment(Align.center);
        setColor(Consts.FUNDS_COLOR);
        setPosition(Consts.FUNDS_X, Consts.FUNDS_Y, Align.center);
    }

    /**
     * Updates the label text based on the round's current funds.
     */
    public void update() {
        setText("£" + Math.round(round.getFunds() / 1000.0) + "k");
    }
}

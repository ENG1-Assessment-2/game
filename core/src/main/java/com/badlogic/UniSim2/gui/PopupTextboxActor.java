package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.g2d.Batch;

public class PopupTextboxActor extends TextboxActor {

    private float elapsedTime;

    public PopupTextboxActor(String text, float x, float y, float width) {
        super(text, x, y, width);
        this.elapsedTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        if (elapsedTime >= 5) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (elapsedTime < 5) {
            super.draw(batch, parentAlpha);
        }
    }
}

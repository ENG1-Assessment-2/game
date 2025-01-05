package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A text box actor that automatically removes itself after a certain amount of
 * time.
 */
public class PopupTextboxActor extends TextboxActor {

    private float elapsedTime;

    public PopupTextboxActor(String text, float x, float y, float width) {
        super(text, x, y, width);
        this.elapsedTime = 0;
    }

    /**
     * Updates the actor's state in order to track the time elapsed since its
     * creation. Removes itself once 5 seconds have elapsed
     *
     * @param delta The time elapsed since the last update.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        if (elapsedTime >= 5) {
            remove();
        }
    }

    /**
     * Draws the text box.
     *
     * @param batch The batch used for drawing.
     * @param parentAlpha The parent alpha.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (elapsedTime < 5) {
            super.draw(batch, parentAlpha);
        }
    }
}

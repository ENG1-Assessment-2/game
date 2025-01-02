package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextboxActor extends Actor {

    private final Textbox textbox;

    public TextboxActor(Textbox textbox) {
        this.textbox = textbox;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        textbox.render();
    }

    public void setText(String text) {
        textbox.setText(text);
    }

    public void setWidth(float width) {
        textbox.setWidth(width);
    }

    public void setVisible(boolean visible) {
        textbox.setVisible(visible);
    }
}

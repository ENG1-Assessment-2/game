package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * An actor that displays a text box with a background and text.
 */
public class TextboxActor extends Actor {

    private String text;
    private final BitmapFont font;
    private final float x, y;
    private final ShapeRenderer shapeRenderer;
    private final GlyphLayout layout;
    private final float width;

    /**
     * Constructs a new TextboxActor.
     *
     * @param text The text to display.
     * @param x The x position of the text box.
     * @param y The y position of the text box.
     * @param width The width of the text box.
     */
    public TextboxActor(String text, float x, float y, float width) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.shapeRenderer = new ShapeRenderer();
        this.layout = new GlyphLayout();
        font.getData().setScale(2, 1.5f);
    }

    /**
     * Draws the text box and its background.
     *
     * @param batch The batch used for drawing.
     * @param parentAlpha The parent alpha, to be multiplied with this actor's
     * alpha, allowing the parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Calculate the width and height of the text
        layout.setText(font, text, Color.BLACK, width, -1, true);
        float height = layout.height + 20;

        // Draw the background rectangle
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x - 10, y - height + 10, width + 20, height);
        shapeRenderer.end();
        batch.begin();

        // Draw the text
        font.draw(batch, layout, x, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}

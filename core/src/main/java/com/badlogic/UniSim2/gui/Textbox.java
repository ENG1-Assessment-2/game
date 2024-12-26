package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Textbox {
    private String text;
    private BitmapFont font;
    private float x, y;
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;

    public Textbox(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.shapeRenderer = new ShapeRenderer();
        this.layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch) {
        // Calculate the width and height of the text
        layout.setText(font, text);
        float width = layout.width + 20;
        float height = layout.height + 20;

        // Draw the background rectangle
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x - 10, y - height + 10, width, height);
        shapeRenderer.end();
        batch.begin();

        // Draw the text
        font.draw(batch, text, x, y);
    }

    public void setText(String text) {
        this.text = text;
    }
}
package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Textbox {

    private String text;
    private final BitmapFont font;
    private final float x, y;
    private final ShapeRenderer shapeRenderer;
    private final GlyphLayout layout;
    private final SpriteBatch batch;
    private float width;
    private boolean visible;

    public Textbox(String text, float x, float y, float width) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.shapeRenderer = new ShapeRenderer();
        this.layout = new GlyphLayout();
        this.batch = new SpriteBatch();
        this.visible = false;
    }

    public void render() {
        if (!visible) return;
        
        // Calculate the width and height of the text
        layout.setText(font, text, Color.BLACK, width, -1, true);
        float height = layout.height + 20;

        // Draw the background rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x - 10, y - height + 10, width + 20, height);
        shapeRenderer.end();

        // Draw the text
        batch.begin();
        font.draw(batch, layout, x, y);
        batch.end();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        batch.dispose();
    }
}

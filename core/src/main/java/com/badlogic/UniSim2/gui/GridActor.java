package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * An actor that draws the grid on the game map.
 */
public class GridActor extends Actor {

    private final ShapeRenderer shapeRenderer;

    public GridActor() {
        shapeRenderer = new ShapeRenderer();
        setColor(Consts.GRID_COLOR);
    }

    /**
     * Draws the grid.
     *
     * @param batch The batch used for drawing.
     * @param parentAlpha The parent alpha.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Need to end batch before using ShapeRenderer
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(getColor());

        // Draw vertical lines
        for (int x = 0; x <= Consts.GRID_COLS; x++) {
            float xPos = x * Consts.CELL_SIZE;
            shapeRenderer.line(xPos, 0, xPos, Consts.WORLD_HEIGHT);
        }

        // Draw horizontal lines
        for (int y = 0; y <= Consts.GRID_ROWS; y++) {
            float yPos = y * Consts.CELL_SIZE;
            shapeRenderer.line(0, yPos, Consts.WORLD_WIDTH, yPos);
        }

        shapeRenderer.end();

        batch.begin();
    }
    
    public void dispose() {
        shapeRenderer.dispose();
    }
}

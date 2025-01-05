package com.badlogic.UniSim2.gui.screens;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * An abstract base class for all screens in the game. Manages a Stage and a
 * Viewport.
 */
abstract public class GameScreen implements Screen {

    final protected Stage stage;
    final protected StretchViewport viewport;

    public GameScreen() {
        viewport = new StretchViewport(Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage = new Stage(viewport);
    }

    /**
     * Called when this screen becomes the current screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when the screen is resized.
     *
     * @param width The new width.
     * @param height The new height.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Called when the game is paused.
     */
    @Override
    public void pause() {
        // Default empty implementation
    }

    /**
     * Called when the game is resumed from a paused state.
     */
    @Override
    public void resume() {
        // Default empty implementation
    }

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override
    public void hide() {
        // Default empty implementation
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}

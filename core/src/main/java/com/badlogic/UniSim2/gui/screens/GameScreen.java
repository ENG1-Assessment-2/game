package com.badlogic.UniSim2.gui.screens;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

abstract public class GameScreen implements Screen {

    final protected Stage stage;
    final protected StretchViewport viewport;

    public GameScreen() {
        viewport = new StretchViewport(Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Default empty implementation
    }

    @Override
    public void resume() {
        // Default empty implementation
    }

    @Override
    public void hide() {
        // Default empty implementation
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

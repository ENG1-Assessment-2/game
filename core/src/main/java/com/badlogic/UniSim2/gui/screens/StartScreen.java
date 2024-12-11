package com.badlogic.UniSim2.gui.screens;

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen extends GameScreen {

    @FunctionalInterface
    public interface StartScreenCallback {

        void onStartGame();
    }

    private final StartScreenCallback callback;
    private ImageButton startButton;
    private final SpriteBatch spriteBatch;

    public StartScreen(StartScreenCallback callback) {
        this.callback = callback;
        this.spriteBatch = new SpriteBatch();
        addStartButton();
    }

    private void addStartButton() {
        setupStartButton();
        addStartButtonClick();
    }

    private void setupStartButton() {
        Drawable startButtonUpDrawable = new TextureRegionDrawable(Assets.startButtonUpTexture);
        Drawable startButtonDownDrawable = new TextureRegionDrawable(Assets.startButtonDownTexture);

        ImageButton.ImageButtonStyle startButtonStyle = new ImageButton.ImageButtonStyle();
        startButtonStyle.up = startButtonUpDrawable;
        startButtonStyle.down = startButtonDownDrawable;
        startButtonStyle.over = startButtonDownDrawable;

        startButton = new ImageButton(startButtonStyle);
        startButton.setSize(Consts.START_BUTTON_WIDTH, Consts.START_BUTTON_HEIGHT);
        startButton.setPosition(Consts.START_BUTTON_X, Consts.START_BUTTON_Y);
    }

    private void addStartButtonClick() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                callback.onStartGame();
            }
        });
        stage.addActor(startButton);
    }

    private void drawBackground() {
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(Assets.startBackgroundTexture, 0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        drawBackground();
        super.render(delta);
    }

    @Override
    public void dispose() {
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        super.dispose();
    }
}

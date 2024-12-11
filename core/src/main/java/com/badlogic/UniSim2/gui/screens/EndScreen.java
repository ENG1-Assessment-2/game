package com.badlogic.UniSim2.gui.screens;

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Screen displayed when game ends, showing final score
 */
public class EndScreen extends GameScreen {

    private final Label scoreLabel;
    private final Skin skin;
    private final SpriteBatch spriteBatch;

    /**
     * @param score Final game score to display
     */
    public EndScreen(int score) {
        super();
        this.spriteBatch = new SpriteBatch();
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.scoreLabel = createScoreLabel(score);
        stage.addActor(scoreLabel);
        SoundManager.stopMusic();
    }

    private Label createScoreLabel(int score) {
        Label label = new Label("Score : " + score, skin);
        label.setFontScale(3);
        label.setAlignment(Align.center);
        label.setColor(Consts.TIMER_COLOR);
        label.setPosition(Consts.SCORE_LABEL_X, Consts.SCORE_LABEL_Y, Align.center);
        return label;
    }

    private void drawBackground() {
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
        skin.dispose();
        spriteBatch.dispose();
        super.dispose();
    }
}

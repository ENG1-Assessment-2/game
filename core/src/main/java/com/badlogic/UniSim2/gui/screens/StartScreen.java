package com.badlogic.UniSim2.gui.screens;

import java.util.List;

import com.badlogic.UniSim2.gui.TextboxActor;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen extends GameScreen {

    @FunctionalInterface
    public interface StartScreenCallback {

        void onStartGame(String name);
    }

    private final StartScreenCallback callback;
    private ImageButton startButton;
    private final SpriteBatch spriteBatch;
    private TextField nameInput;
    private TextboxActor leaderboard;
    private final List<String> leaderboardEntries;

    public StartScreen(StartScreenCallback callback, List<String> leaderboardEntries) {
        this.callback = callback;
        this.leaderboardEntries = leaderboardEntries;
        this.spriteBatch = new SpriteBatch();
        addStartButton();
        addNameInput();
        SoundManager.getInstance().playMusic();
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

    private void addNameInput() {
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.5f, 2);
        Drawable background = new BaseDrawable();

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = background;
        textFieldStyle.cursor = null;
        textFieldStyle.selection = null;

        nameInput = new TextField("", textFieldStyle);
        nameInput.setMessageText("Enter your name");
        nameInput.setSize(Consts.NAME_INPUT_WIDTH, Consts.NAME_INPUT_HEIGHT);
        nameInput.setPosition(Consts.NAME_INPUT_X, Consts.NAME_INPUT_Y);
        stage.addActor(nameInput);
    }

    private void addStartButtonClick() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (nameInput.getText().length() > 0) {
                    callback.onStartGame(nameInput.getText());
                }
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

        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            if (leaderboard == null) {
                String text = !leaderboardEntries.isEmpty() ? "Leaderboard:\n" + String.join("\n", leaderboardEntries) : "No entries yet";
                leaderboard = new TextboxActor(text, 30, Consts.WORLD_HEIGHT - 30, Consts.WORLD_WIDTH - 60);
                stage.addActor(leaderboard);
            } else {
                leaderboard.remove();
                leaderboard = null;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            SoundManager.getInstance().toggleMute();
        }
    }

    @Override
    public void dispose() {
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        super.dispose();
    }
}

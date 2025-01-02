package com.badlogic.UniSim2.gui.screens;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.gui.Map;
import com.badlogic.UniSim2.gui.Menu;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class RoundScreen extends GameScreen {

    @FunctionalInterface
    public interface PlayScreenCallback {

        void onGameEnd();
    }

    private final PlayScreenCallback callback;
    private final Round round;
    private final Menu menu;
    private final Map map;

    public RoundScreen(Round round, PlayScreenCallback callback) {
        super();
        this.round = round;
        this.callback = callback;
        this.map = new Map(stage, round);
        this.menu = new Menu(stage, round);
        SoundManager.getInstance().playMusic();
    }

    @Override
    public void render(float delta) {
        input();
        update();
        if (round.isOver()) {
            return;
        }

        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        super.render(delta);
    }

    private void input() {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        boolean clicked = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        boolean rightClicked = Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT);
        viewport.unproject(mousePos);
        map.input(mousePos, clicked, rightClicked);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            round.togglePause();
        }
    }

    private void update() {
        round.update(Gdx.graphics.getDeltaTime());
        menu.update();

        if (round.isOver()) {
            callback.onGameEnd();
        }
    }
}

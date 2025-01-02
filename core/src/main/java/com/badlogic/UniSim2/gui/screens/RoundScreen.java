package com.badlogic.UniSim2.gui.screens;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.achievements.Achievement;
import com.badlogic.UniSim2.gui.AchievementsTextbox;
import com.badlogic.UniSim2.gui.Map;
import com.badlogic.UniSim2.gui.Menu;
import com.badlogic.UniSim2.gui.PopupTextboxActor;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class RoundScreen extends GameScreen {

    @FunctionalInterface
    public interface PlayScreenCallback {

        void onGameEnd();
    }

    private final Set<Achievement> announcedAchievements;
    private final Stage popupStage;
    private final PlayScreenCallback callback;
    private final Round round;
    private final Menu menu;
    private final Map map;
    private AchievementsTextbox activeAchievementsTextbox;

    public RoundScreen(Round round, PlayScreenCallback callback) {
        super();
        this.announcedAchievements = new HashSet<>();
        this.popupStage = new Stage(viewport);
        this.round = round;
        this.callback = callback;
        this.map = new Map(stage, round);
        this.menu = new Menu(stage, round);
        this.activeAchievementsTextbox = null;
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

        popupStage.act(delta);
        popupStage.draw();
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            SoundManager.getInstance().toggleMute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            round.togglePause();
            if (activeAchievementsTextbox == null) {
                activeAchievementsTextbox = new AchievementsTextbox();
                activeAchievementsTextbox.updateText(round.getCompletedAchievements(), round.getAllAchievements());
                popupStage.addActor(activeAchievementsTextbox);
            } else {
                activeAchievementsTextbox.remove();
                activeAchievementsTextbox = null;
            }
        }
    }

    private void update() {
        round.update(Gdx.graphics.getDeltaTime());
        menu.update();

        if (round.isOver()) {
            callback.onGameEnd();
            return;
        }

        for (Achievement achievement : round.getCompletedAchievements()) {
            if (!announcedAchievements.contains(achievement)) {
                System.out.println("Achievement Unlocked: " + achievement.getName());
                announcedAchievements.add(achievement);
                PopupTextboxActor popup = new PopupTextboxActor(
                        "Achievement Unlocked: " + achievement.getName() + "\n" + achievement.getDescription(),
                        (Consts.WORLD_WIDTH - 600) / 2, Consts.WORLD_HEIGHT - 30, 700
                );
                popupStage.addActor(popup);
            }
        }
    }
}

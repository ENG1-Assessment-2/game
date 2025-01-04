package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Leaderboard;
import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.gui.screens.GameScreen;
import com.badlogic.UniSim2.gui.screens.RoundScreen;
import com.badlogic.UniSim2.gui.screens.StartScreen;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.gdx.Game;

public class Main extends Game {

    private Leaderboard leaderboard;
    private GameScreen currentScreen;
    private Round currentRound;
    private String currentName;

    @Override
    public void create() {
        leaderboard = new Leaderboard();
        Assets.loadTextures();
        setGameScreen(new StartScreen(this::startNewRound, leaderboard.getEntries()));
    }

    @Override
    public void render() {
        super.render();
    }

    private void setGameScreen(GameScreen screen) {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        currentScreen = screen;
        setScreen(screen);
    }

    private void startNewRound(String name) {
        currentName = name;
        currentRound = new Round();
        setGameScreen(new RoundScreen(currentRound, this::endGame));
    }

    private void endGame() {
        int satisfaction = currentRound.getStudentSatisfaction();
        leaderboard.addEntry(currentName, satisfaction);
        setGameScreen(new StartScreen(this::startNewRound, leaderboard.getEntries()));
    }
}

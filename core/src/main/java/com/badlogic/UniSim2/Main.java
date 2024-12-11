package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.gui.screens.EndScreen;
import com.badlogic.UniSim2.gui.screens.GameScreen;
import com.badlogic.UniSim2.gui.screens.RoundScreen;
import com.badlogic.UniSim2.gui.screens.StartScreen;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.gdx.Game;

public class Main extends Game {

    private GameScreen currentScreen;
    private Round currentRound;

    @Override
    public void create() {
        Assets.loadTextures();
        setGameScreen(new StartScreen(this::startNewRound));
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

    private void startNewRound() {
        currentRound = new Round();
        setGameScreen(new RoundScreen(currentRound, this::endGame));
    }

    private void endGame() {
        setGameScreen(new EndScreen(0));
    }
}

package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Leaderboard;
import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.gui.screens.GameScreen;
import com.badlogic.UniSim2.gui.screens.RoundScreen;
import com.badlogic.UniSim2.gui.screens.StartScreen;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.gdx.Game;

/**
 * The main class of the game, responsible for initializing and managing the
 * game screens, leaderboard, and rounds.
 */
public class Main extends Game {

    private Leaderboard leaderboard;
    private GameScreen currentScreen;
    private Round currentRound;
    private String currentName;

    /**
     * Called when the application is first created. Initializes the
     * leaderboard, loads assets, and sets the initial game screen.
     */
    @Override
    public void create() {
        leaderboard = new Leaderboard();
        Assets.loadTextures();
        setGameScreen(new StartScreen(this::startNewRound, leaderboard.getEntries()));
    }

    /**
     * Sets the current game screen.
     *
     * @param screen The new game screen to set.
     */
    private void setGameScreen(GameScreen screen) {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        currentScreen = screen;
        setScreen(screen);
    }

    /**
     * Starts a new round of the game.
     *
     * @param name The name of the player.
     */
    private void startNewRound(String name) {
        currentName = name;
        currentRound = new Round();
        setGameScreen(new RoundScreen(currentRound, this::endGame));
    }

    /**
     * Ends the current round of the game, adds the player score to the
     * leaderboard and swaps to the start screen.
     */
    private void endGame() {
        int satisfaction = currentRound.getStudentSatisfaction();
        leaderboard.addEntry(currentName, satisfaction);
        setGameScreen(new StartScreen(this::startNewRound, leaderboard.getEntries()));
    }
}

package com.badlogic.UniSim2.core.buildings;

/**
 * Represents a path in the game. Paths are not placed by the player, but are
 * placed by default at the start of the game.
 */
public class Path extends Building {

    public Path(int width, int height, int row, int col) {
        super(width, height, row, col, null);
    }

    @Override
    public String getName() {
        return "Path";
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getDescription() {
        return getName();
    }
}

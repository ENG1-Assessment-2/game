package com.badlogic.UniSim2.core.buildings;

/**
 * Represents an abstract building in the game. Subclasses should implement the
 * getCost, getName, and getDescription methods.
 */
public abstract class Building {

    private final int width;
    private final int height;
    private final int row;
    private final int col;
    private final BuildingType type;

    public Building(int width, int height, int row, int col, BuildingType type) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BuildingType getType() {
        return type;
    }

    /**
     * Checks if this building overlaps with another building of the specified
     * type at the given position.
     *
     * @param type The type of the other building.
     * @param row The row position of the other building.
     * @param col The column position of the other building.
     * @return True if the buildings overlap, false otherwise.
     */
    public boolean overlaps(BuildingType type, int row, int col) {
        boolean rowOverlaps = this.row - this.height < row && this.row > row - type.getHeight();
        boolean colOverlaps = this.col + this.width > col && this.col < col + type.getWidth();

        return rowOverlaps && colOverlaps;
    }

    public abstract int getCost();

    public int getDemolishCost() {
        return getCost() / 4;
    }

    public int getMoveCost() {
        return getCost() / 2;
    }

    public abstract String getName();

    public abstract String getDescription();
}

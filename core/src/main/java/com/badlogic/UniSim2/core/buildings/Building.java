package com.badlogic.UniSim2.core.buildings;

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
}

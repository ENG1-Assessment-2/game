package com.badlogic.UniSim2.core.buildings;

import java.util.function.BiFunction;

import com.badlogic.UniSim2.resources.Consts;

/**
 * Enum representing different types of buildings in the game.
 */
public enum BuildingType {
    ACCOMMODATION(
            Consts.ACCOMMODATION_WIDTH,
            Consts.ACCOMMODATION_HEIGHT,
            Accommodation::new
    ),
    LECTUREHALL(
            Consts.LECTUREHALL_WIDTH,
            Consts.LECTUREHALL_HEIGHT,
            LectureHall::new
    ),
    BAR(
            Consts.BAR_WIDTH,
            Consts.BAR_HEIGHT,
            Bar::new
    ),
    FOODZONE(
            Consts.FOODZONE_WIDTH,
            Consts.FOODZONE_HEIGHT,
            FoodZone::new
    ),
    NATURE(
            Consts.NATURE_WIDTH,
            Consts.NATURE_HEIGHT,
            Nature::new
    );

    private final int width;
    private final int height;
    private final BiFunction<Integer, Integer, Building> factory;

    /**
     * Constructs a new BuildingType.
     *
     * @param width The width of the building type.
     * @param height The height of the building type.
     * @param factory The factory function to create a building of this type.
     */
    BuildingType(int width, int height, BiFunction<Integer, Integer, Building> factory) {
        this.width = width;
        this.height = height;
        this.factory = factory;
    }

    /**
     * Creates a new building of this type at the specified position.
     *
     * @param row The row position.
     * @param col The column position.
     * @return The created building.
     */
    public Building create(int row, int col) {
        return factory.apply(row, col);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

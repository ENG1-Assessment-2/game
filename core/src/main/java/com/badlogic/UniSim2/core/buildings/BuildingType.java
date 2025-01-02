package com.badlogic.UniSim2.core.buildings;

import java.util.function.BiFunction;

import com.badlogic.UniSim2.resources.Consts;

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

    BuildingType(int width, int height, BiFunction<Integer, Integer, Building> factory) {
        this.width = width;
        this.height = height;
        this.factory = factory;
    }

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

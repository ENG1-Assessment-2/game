package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingType;

import java.util.List;
import java.util.Random;

public class EventFire extends AbstractEvent{
    private static final String NAME = "There's Been A Fire!";
    private static final String DESCRIPTION = "One of your buildings has burnt down! Replace it within 15 seconds" +
        "to avoid reducing your students' satisfaction!";
    private int removedRow;
    private int removedCol;
    private BuildingType removedType;
    private final int delay = 15;

    public EventFire(Integer time, Round round) {
        super(NAME, DESCRIPTION, time, round);
    }

    @Override
    public void trigger() {
        Round round = getCurrentRound();
        Random random = new Random();
        List<Building> placedBuildings = round.getPlacedBuildings();
        if (!placedBuildings.isEmpty()) {
            Building buildingToDestroy = placedBuildings.get(random.nextInt(placedBuildings.size()));

            removedRow = buildingToDestroy.getRow();
            removedCol = buildingToDestroy.getCol();
            removedType = buildingToDestroy.getType();
        }

        System.out.println("Fire event 1 triggered successfully");
    }

    public void trigger2(){
        Round round = getCurrentRound();
        List<Building> placedBuildings = round.getPlacedBuildings();

        if (!placedBuildings.isEmpty()) {
            boolean replaced = placedBuildings.stream()
                .anyMatch(b -> b.getRow() == removedRow
                    && b.getCol() == removedCol
                    && b.getType() == removedType);

            // Depending on result of this, deduct satisfaction if necessary
        }

        System.out.println("Fire event 2 triggered successfully");

    }

    public int getDelay() {
        return delay;
    }
}

package com.badlogic.UniSim2.core.events;

import java.util.List;
import java.util.Random;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.resources.Consts;

/**
 * Represents a fire event in the game where a building is destroyed by fire.
 */
public class FireEvent extends Event {

    private Building destroyedBuilding;

    public FireEvent(Round round) {
        super(
                "There's Been A Fire!",
                "One of your buildings has burnt down! Replace it within 30 seconds to avoid losing score.",
                (int) (Consts.MAX_TIME * Math.random()),
                round
        );
        this.destroyedBuilding = null;
    }

    /**
     * Handles the triggering of the fire event. Randomly selects a building to
     * be destroyed by fire.
     */
    @Override
    protected void handleTrigger() {
        Random rand = new Random();
        List<Building> buildings = round.getPlacedBuildings();
        if (!buildings.isEmpty()) {
            destroyedBuilding = buildings.get(rand.nextInt(buildings.size()));
            try {
                round.removeBuilding(destroyedBuilding.getRow(), destroyedBuilding.getCol());
                waitForResponse(30);
            } catch (BuildingRemovalException e) {
                // Handling not needed.
            }
        }
    }

    /**
     * Handles the response to the fire event. If the destroyed building is not
     * replaced within the response time, the satisfaction is reduced by 100.
     */
    @Override
    protected void handleResponse() {
        if (!getIsBuildingReplaced()) {
            round.addToSatisfactionAddend(-100);
        }
    }

    /**
     * Checks if the destroyed building has been replaced.
     *
     * @return True if the building has been replaced, false otherwise.
     */
    private boolean getIsBuildingReplaced() {
        Building replacementBuilding = round.getBuildingAt(destroyedBuilding.getRow(), destroyedBuilding.getCol());

        if (replacementBuilding == null) {
            return false;
        }

        boolean samePosition = replacementBuilding.getRow() == destroyedBuilding.getRow() && replacementBuilding.getCol() == destroyedBuilding.getCol();
        boolean sameType = replacementBuilding.getType() == destroyedBuilding.getType();
        return samePosition && sameType;
    }
}

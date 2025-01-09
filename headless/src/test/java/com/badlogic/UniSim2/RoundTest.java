package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Consts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoundTest extends AbstractHeadlessGdxTest {

    @Test
    void testInitialConditions() {
        Round round = new Round();
        // Verify initial funds
        assertEquals(1000 * 1000, round.getFunds(), "Initial funds should be 1,000,000.");
        // Verify initial events size
        assertEquals(3, round.getEvents().size(), "There should be three events in the initial set.");
        // Verify no achievements have been completed yet
        assertTrue(round.getCompletedAchievements().isEmpty(), "No achievements should be completed initially.");
    }

    @Test
    void testPlaceAndRemoveBuilding() {
        Round round = new Round();
        int fundsBefore = round.getFunds();

        // Attempt to place an ACCOMMODATION building at (10,10)
        try {
            round.placeBuilding(BuildingType.ACCOMMODATION, 10, 10);
        } catch (BuildingPlacementException e) {
            fail("Placing a building at (10,10) should succeed.");
        }

        // Check that funds decreased after placement
        assertTrue(round.getFunds() < fundsBefore, "Funds should decrease after placing a building.");

        // Attempt to remove the building at (10,10)
        try {
            round.removeBuilding(10, 10);
        } catch (BuildingRemovalException e) {
            fail("Removing the building at (10,10 should succeed.");
        }
    }

    @Test
    void testUpdatePauseFunctionality() {
        Round round = new Round();
        round.togglePause();  // Pause the round

        float elapsedBefore = round.getElapsedTime();
        round.update(10f);    // Attempt to update while paused
        assertEquals(elapsedBefore, round.getElapsedTime(), "Elapsed time should not change when paused.");
    }

    @Test
    void testFundsIncreaseOverTime() {
        Round round = new Round();
        int fundsBefore = round.getFunds();

        // Update by slightly more than 5 seconds to trigger fund increase
        round.update(100f);

       System.out.println("Funds before: " + fundsBefore);
       System.out.println("Funds after: " + round.getFunds());
        assertTrue(round.getFunds() > fundsBefore, "Funds should increase after updating past 5 seconds.");
    }

    @Test
    void testAchievementCompletionDuringUpdate() throws BuildingPlacementException {
        Round round = new Round();

        // Place a building to potentially complete an achievement (e.g., FirstBuilding)
        round.placeBuilding(BuildingType.ACCOMMODATION, 10, 10);

        // Perform an update to check achievements
        round.update(1f);

        // Since FirstBuilding requires at least one building, it should complete
        assertFalse(round.getCompletedAchievements().isEmpty(), "At least one achievement should be completed.");
    }
}

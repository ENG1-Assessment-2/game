package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.core.events.FireEvent;
import com.badlogic.UniSim2.core.events.GrantEvent;
import com.badlogic.UniSim2.core.events.RoyalEvent;
import com.badlogic.UniSim2.resources.Consts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventsTest extends AbstractHeadlessGdxTest {

    // ------------------------------------------------
    // FireEvent Tests
    // ------------------------------------------------

    @Test
    void testFireEventNameAndDescription() {
        Round round = new Round();
        FireEvent event = new FireEvent(round);

        assertEquals("There's Been A Fire!", event.getName());
        assertTrue(
            event.getDescription().contains("One of your buildings has burnt down!"),
            "Description should mention building burning down."
        );
    }

    @Test
    void testFireEventTriggerNoBuildings() {
        Round round = new Round();
        FireEvent event = new FireEvent(round);

        // FireEvent triggers at time >= 5 seconds.
        // If we only update 4 seconds, it won't trigger:
        round.update(4f);
        assertFalse(event.attemptTrigger(),
            "Should not trigger yet because elapsedTime < 5");

        // Now pass enough time to exceed 5 seconds:
        round.update(2f); // total = 6s
        assertTrue(event.attemptTrigger(),
            "FireEvent should trigger after 5 seconds have passed");

        // With no buildings, nothing is removed, but no penalty is applied (since waitForResponse(15) only starts if a building was destroyed).
        // Let's update 20 more seconds to see if any penalty is applied:
        float oldSatisfaction = round.getStudentSatisfaction();
        round.update(20f);
        float newSatisfaction = round.getStudentSatisfaction();

        assertEquals(oldSatisfaction, newSatisfaction,
            "No building was destroyed, so there should be no -100 penalty");
    }

    @Test
    void testFireEventTriggerWithBuildingsAndNoReplacement() {
        Round round = new Round();
        FireEvent event = new FireEvent(round);

        // Add one building manually
        Building building = BuildingType.ACCOMMODATION.create(5, 5);
        round.getPlacedBuildings().add(building);

        // Advance time to ensure event triggers
        round.update(6f);  // Now elapsedTime > 5
        assertTrue(event.attemptTrigger(), "Should trigger after 5 seconds");

        // A building should be removed immediately (destroyedBuilding)
        // Because there's at least one building
        assertTrue(round.getPlacedBuildings().isEmpty(),
            "The single building should have been removed by FireEvent");

        // FireEvent sets a 15s response timer. If no replacement is made, -100 satisfaction is applied.
        float oldSatisfaction = round.getStudentSatisfaction();

        // Wait 16 seconds so handleResponse() triggers
        round.update(16f);

        float newSatisfaction = round.getStudentSatisfaction();
        assertEquals(oldSatisfaction - 100, newSatisfaction,
            "Satisfaction should have dropped by 100 because building wasn't replaced");
    }

    @Test
    void testFireEventTriggerWithBuildingsAndReplacement() {
        Round round = new Round();
        FireEvent event = new FireEvent(round);

        // Add one building
        Building oldBuilding = BuildingType.ACCOMMODATION.create(5, 5);
        round.getPlacedBuildings().add(oldBuilding);

        // Trigger event
        round.update(6f);  // Enough time to exceed 5
        assertTrue(event.attemptTrigger(), "Should trigger after 5 seconds");
        assertTrue(round.getPlacedBuildings().isEmpty(),
            "Original building should have been removed by FireEvent");

        // FireEvent is waiting 15 seconds for a replacement building
        // at (row=5, col=5) with the same BuildingType
        float oldSatisfaction = round.getStudentSatisfaction();

        // Replace the destroyed building:
        Building newBuilding = BuildingType.ACCOMMODATION.create(5, 5);
        round.getPlacedBuildings().add(newBuilding);

        // Advance time 16s to finish the 15s timer
        round.update(16f);

        float newSatisfaction = round.getStudentSatisfaction();
        // Because we replaced the building, handleResponse() should NOT apply the -100 penalty
        assertEquals(oldSatisfaction, newSatisfaction,
            "Satisfaction should be unchanged because we replaced the building in time.");
    }


    // ------------------------------------------------
    // GrantEvent Tests
    // ------------------------------------------------

    @Test
    void testGrantEventNameAndDescription() {
        Round round = new Round();
        GrantEvent event = new GrantEvent(round);

        assertEquals("A Nice Bonus!", event.getName());
        assertTrue(event.getDescription().contains("give your university a grant!"),
            "Description should mention receiving a grant");
    }

    @Test
    void testGrantEventTrigger() {
        Round round = new Round();
        GrantEvent event = new GrantEvent(round);

        int oldFunds = round.getFunds();

        // The triggerTime for GrantEvent is set randomly between 0 and MAX_TIME.
        // We'll just blow past MAX_TIME to ensure we exceed the random triggerTime.
        float bigTime = Consts.MAX_TIME + 10f;
        round.update(bigTime);

        assertFalse(event.attemptTrigger(),
            "attemptTrigger() returns false if it doesn't do a second trigger. We'll call it once to see if it triggers.");

        // Actually try triggering it
        assertTrue(event.attemptTrigger(),
            "After enough time passes, the event should trigger exactly once");

        int newFunds = round.getFunds();
        assertEquals(oldFunds + 500_000, newFunds,
            "GrantEvent should add £500k to the Round's funds");
    }


    // ------------------------------------------------
    // RoyalEvent Tests
    // ------------------------------------------------

    @Test
    void testRoyalEventNameAndDescription() {
        Round round = new Round();
        RoyalEvent event = new RoyalEvent(round);

        assertEquals("A Royal Visit?", event.getName());
        assertTrue(event.getDescription().contains("A member of the royal family"),
            "Description should mention a royal visit");
    }

    @Test
    void testRoyalEventTrigger() {
        Round round = new Round();
        RoyalEvent event = new RoyalEvent(round);

        // RoyalEvent triggers at elapsedTime >= Consts.MAX_TIME / 2
        float halfTime = Consts.MAX_TIME / 2;

        round.update(halfTime - 1);
        assertFalse(event.attemptTrigger(),
            "Should not trigger before half of max time has elapsed");

        // Now surpass that time
        round.update(2f); // This should put us > halfTime
        assertTrue(event.attemptTrigger(),
            "Should trigger once we've passed half of max time");

        // RoyalEvent's handleTrigger() is empty, so we just check triggered = true
        // (which attemptTrigger returning true confirms).
    }
}

package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Grid;
import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        // Creates a Round (which internally creates a Grid of size 38x66).
        // <-- We'll use Round now
        Round round = new Round();
        // Retrieve the grid from the round
        grid = round.getGrid();
    }

    // placeBuilding(...) tests

    @Test
    void testPlaceBuildingSuccess() throws BuildingPlacementException {
        // Attempt to place a building of type ACCOMMODATION at (10,10)
        grid.placeBuilding(BuildingType.ACCOMMODATION, 10, 10);

        // Verify it was placed
        assertTrue(grid.getIsBuildingAt(10, 10),
            "Expected a building at (10,10) after placing ACCOMMODATION");

        Building placed = grid.getBuildingAt(10, 10);
        assertNotNull(placed, "Should retrieve the placed building via getBuildingAt()");
        assertEquals(BuildingType.ACCOMMODATION, placed.getType(),
            "The placed building should be ACCOMMODATION type");
    }

    @Test
    void testPlaceBuildingOutOfBounds() {
        // Attempt to place a building outside the grid
        // row=50 is out of a 38-row grid
        assertThrows(BuildingPlacementException.class, () ->
                grid.placeBuilding(BuildingType.BAR, 50, 5),
            "Placing a building at row=50 should fail with BuildingPlacementException");
    }

    @Test
    void testPlaceBuildingOverlapsExisting() throws BuildingPlacementException {
        // Place a building first
        grid.placeBuilding(BuildingType.ACCOMMODATION, 10, 10);

        // Attempt to place another building that overlaps the first
        assertThrows(BuildingPlacementException.class, () ->
                grid.placeBuilding(BuildingType.ACCOMMODATION, 10, 10),
            "Placing a second building on the same spot should throw an exception");
    }

    // moveBuilding(...) tests

    @Test
    void testMoveBuildingSuccess() throws BuildingPlacementException {
        // Place a building at (2,2)
        grid.placeBuilding(BuildingType.BAR, 10, 10);
        Building original = grid.getBuildingAt(10, 10);

        // Move it to (11,11)
        grid.moveBuilding(original, 5, 10);

        // Verify old position is empty
        assertFalse(grid.getIsBuildingAt(10, 10),
            "Old position (10,10) should be empty after moving the building");

        // Verify new position has the building
        assertTrue(grid.getIsBuildingAt(5, 10),
            "Expected building at (5,10) after moving");

        Building moved = grid.getBuildingAt(5, 10);
        assertNotNull(moved);
        assertEquals(BuildingType.BAR, moved.getType(),
            "The moved building retains its type (BAR)");
    }

    @Test
    void testMoveBuildingInvalidTarget() throws BuildingPlacementException {
        // Place a building within the grid
        grid.placeBuilding(BuildingType.ACCOMMODATION, 10, 10);
        Building building = grid.getBuildingAt(10, 10);

        // Try to move it out of bounds
        assertThrows(BuildingPlacementException.class, () ->
                grid.moveBuilding(building, 100, 100),
            "Moving building out of grid bounds should throw exception");
        }
    }

package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.achievements.AcademicArchitect;
import com.badlogic.UniSim2.core.achievements.FirstBuilding;
import com.badlogic.UniSim2.core.achievements.GreenCampus;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementsTest extends AbstractHeadlessGdxTest {

    // ------------------------------------------------
    // AcademicArchitect tests
    // ------------------------------------------------

    @Test
    void testAcademicArchitectSingleton() {
        AcademicArchitect instance1 = AcademicArchitect.getInstance();
        AcademicArchitect instance2 = AcademicArchitect.getInstance();
        assertSame(instance1, instance2, "AcademicArchitect should be a singleton");
    }

    @Test
    void testAcademicArchitectNameAndDescription() {
        AcademicArchitect architect = AcademicArchitect.getInstance();
        assertEquals("Academic Architect!", architect.getName());
        assertEquals("Place twenty-five buildings.", architect.getDescription());
    }

    @Test
    void testAcademicArchitectCompletion() throws BuildingPlacementException {
        Round round = new Round();
        AcademicArchitect architect = AcademicArchitect.getInstance();

        // Manually add 24 accommodation buildings
        for (int i = 0; i < 24; i++) {
            Building building = BuildingType.ACCOMMODATION.create(10, 10);
            round.getPlacedBuildings().add(building);
        }

        // Should NOT be complete yet
        assertFalse(
            architect.getCompleted(round),
            "Should NOT be completed with fewer than 25 buildings"
        );

        // Add 1 more accommodation building -> total 25
        Building building = BuildingType.ACCOMMODATION.create(10, 10);
        round.getPlacedBuildings().add(building);

        // Should now be complete
        assertTrue(
            architect.getCompleted(round),
            "Should be completed once there are 25 (or more) buildings"
        );
    }

    // ------------------------------------------------
    // FirstBuilding tests
    // ------------------------------------------------

    @Test
    void testFirstBuildingSingleton() {
        FirstBuilding instance1 = FirstBuilding.getInstance();
        FirstBuilding instance2 = FirstBuilding.getInstance();
        assertSame(instance1, instance2, "FirstBuilding should be a singleton");
    }

    @Test
    void testFirstBuildingNameAndDescription() {
        FirstBuilding firstBuilding = FirstBuilding.getInstance();
        assertEquals("First Building!", firstBuilding.getName());
        assertEquals("Place one building.", firstBuilding.getDescription());
    }

    @Test
    void testFirstBuildingCompletion() throws BuildingPlacementException {
        Round round = new Round();
        FirstBuilding firstBuilding = FirstBuilding.getInstance();

        // Initially 0 buildings -> not completed
        assertFalse(
            firstBuilding.getCompleted(round),
            "Should NOT be completed with zero buildings"
        );

        // Manually add a single accommodation building
        round.getPlacedBuildings().add(BuildingType.ACCOMMODATION.create(10, 10));

        // Should now be completed
        assertTrue(
            firstBuilding.getCompleted(round),
            "Should be completed after placing a single building"
        );
    }

    // ------------------------------------------------
    // GreenCampus tests
    // ------------------------------------------------

    @Test
    void testGreenCampusSingleton() {
        GreenCampus instance1 = GreenCampus.getInstance();
        GreenCampus instance2 = GreenCampus.getInstance();
        assertSame(instance1, instance2, "GreenCampus should be a singleton");
    }

    @Test
    void testGreenCampusNameAndDescription() {
        GreenCampus greenCampus = GreenCampus.getInstance();
        assertEquals("Green Campus!", greenCampus.getName());
        assertEquals("Place five nature plots.", greenCampus.getDescription());
    }

    @Test
    void testGreenCampusCompletion() throws BuildingPlacementException {
        Round round = new Round();
        GreenCampus greenCampus = GreenCampus.getInstance();

        // Manually add 4 NATURE buildings
        for (int i = 0; i < 4; i++) {
            round.getPlacedBuildings().add(BuildingType.NATURE.create(10, 10));
        }

        // Should NOT be complete yet
        assertFalse(
            greenCampus.getCompleted(round),
            "Should NOT be completed with fewer than 5 nature plots"
        );

        // Add the 5th NATURE building
        round.getPlacedBuildings().add(BuildingType.NATURE.create(10, 10));

        // Should now be complete
        assertTrue(
            greenCampus.getCompleted(round),
            "Should be completed once there are 5 or more NATURE buildings"
        );
    }
}

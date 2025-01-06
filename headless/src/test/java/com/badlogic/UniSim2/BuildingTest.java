package com.badlogic.UniSim2;

import com.badlogic.UniSim2.core.buildings.Accommodation;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest extends AbstractHeadlessGdxTest {

    @Test
    void testConstructorAndProperties() {
        // Create Accommodation at (row=5, col=5)
        Building accommodation = new Accommodation(5, 5);

        // Verify dimensions come from BuildingType.ACCOMMODATION
        // ACCOMMODATION_WIDTH=4, ACCOMMODATION_HEIGHT=6
        assertEquals(BuildingType.ACCOMMODATION.getWidth(), accommodation.getWidth(),
            "Accommodation width should match BuildingType.ACCOMMODATION");
        assertEquals(BuildingType.ACCOMMODATION.getHeight(), accommodation.getHeight(),
            "Accommodation height should match BuildingType.ACCOMMODATION");

        // Check row/col assignment
        assertEquals(5, accommodation.getRow(),
            "Row should match constructor argument");
        assertEquals(5, accommodation.getCol(),
            "Column should match constructor argument");

        // Check building type
        assertEquals(BuildingType.ACCOMMODATION, accommodation.getType(),
            "Should be ACCOMMODATION type");
    }

    @Test
    void testGetCostAndDerivedCosts() {
        Building accommodation = new Accommodation(0, 0);

        // cost = 240,000
        assertEquals(240_000, accommodation.getCost(),
            "Cost should be 240,000 for Accommodation");

        // Derived costs
        assertEquals(240_000 / 4, accommodation.getDemolishCost(),
            "Demolish cost is 1/4 of getCost()");
        assertEquals(240_000 / 2, accommodation.getMoveCost(),
            "Move cost is 1/2 of getCost()");
    }

    @Test
    void testNameAndDescription() {
        Building accommodation = new Accommodation(0, 0);

        assertEquals("Accommodation", accommodation.getName(),
            "Should have name 'Accommodation'");

        // Check that the description contains all expected info
        String desc = accommodation.getDescription();
        assertTrue(desc.contains("Accommodation for students and staff."),
            "Description should mention usage");
        assertTrue(desc.contains("Cost: 240000"),
            "Description should list cost");
        assertTrue(desc.contains("Demolish: " + (240_000 / 4)),
            "Description should list demolish cost");
        assertTrue(desc.contains("Move: " + (240_000 / 2)),
            "Description should list move cost");
        assertTrue(desc.contains("<=5 tiles from bar: -100"),
            "Description should mention penalty near bar");
    }

    @Test
    void testOverlapsNoOverlap() {
        // Create two accommodations sufficiently far apart, so they don't overlap
        Building a1 = new Accommodation(10, 10);
        Building a2 = new Accommodation(20, 20);

        // Because they are separate, a1 should NOT overlap a2
        boolean overlapA1WithA2 = a1.overlaps(a2.getType(), a2.getRow(), a2.getCol());
        boolean overlapA2WithA1 = a2.overlaps(a1.getType(), a1.getRow(), a1.getCol());

        assertFalse(overlapA1WithA2,
            "Buildings at (10,10) and (20,20) should not overlap");
        assertFalse(overlapA2WithA1,
            "Overlap is symmetric; no overlap in either direction");
    }

    @Test
    void testOverlapsYesOverlap() {
        // Create two accommodations that will overlap vertically or horizontally
        // width=4, height=6
        // a1 is at (10, 10); a2 is at (14, 10),
        // a2 starts in the row range a1 occupies, meaning some overlap
        Building a1 = new Accommodation(10, 10);
        Building a2 = new Accommodation(14, 10);

        // Check overlap
        boolean overlapA1WithA2 = a1.overlaps(a2.getType(), a2.getRow(), a2.getCol());
        boolean overlapA2WithA1 = a2.overlaps(a1.getType(), a1.getRow(), a1.getCol());

        assertTrue(overlapA1WithA2,
            "Second building at (14,10) should overlap the first at (10,10)");
        assertTrue(overlapA2WithA1,
            "Overlap should be symmetric in this implementation");
    }

    @Test
    void testEdgeOverlapJustTouching() {
        Building a1 = new Accommodation(10, 10);
        Building a2 = new Accommodation(10, 15);

        boolean overlapA1WithA2 = a1.overlaps(a2.getType(), a2.getRow(), a2.getCol());
        boolean overlapA2WithA1 = a2.overlaps(a1.getType(), a1.getRow(), a1.getCol());

        // Just touching horizontally
        assertFalse(overlapA1WithA2,
            "Buildings that only touch edges should not overlap");
        assertFalse(overlapA2WithA1,
            "Overlap is symmetric");
    }
}

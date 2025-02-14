package com.badlogic.UniSim2.gui;

import java.util.ArrayList;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.Building;
import com.badlogic.UniSim2.core.buildings.BuildingPlacementException;
import com.badlogic.UniSim2.core.buildings.BuildingRemovalException;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents the map in the game, including all UI related to the buildings and
 * the grid.
 */
public class Map {

    private final Stage stage;
    private final Round round;

    private final ArrayList<BuildingImage> placedBuildingImages;
    private BuildingImage placingBuildingImage;
    private BuildingImage movingBuildingImage;

    public Map(Stage stage, Round round) {
        this.stage = stage;
        this.round = round;

        this.placingBuildingImage = null;
        this.movingBuildingImage = null;
        this.placedBuildingImages = new ArrayList<>();

        addBackground();
        stage.addActor(new GridActor());
    }

    /**
     * Handles user input.
     *
     * @param mousePos The current mouse position.
     * @param clicked Whether the mouse was clicked.
     * @param rightClicked Whether the right mouse button was clicked.
     */
    public void input(Vector2 mousePos, boolean clicked, boolean rightClicked) {
        if (mousePos.x < Consts.MENU_BAR_WIDTH) {
            return;
        }

        BuildingType placingType = round.getSelectedBuildingType();
        Vector2 placingBuildingPosition = getBuildingPosition(mousePos);
        Vector2 placingBuildingGridPosition = getGridPosition(placingBuildingPosition);
        updatePlacingBuildingImage(placingType, placingBuildingPosition, placingBuildingGridPosition);

        if (placingType == null) {
            // not placing building
            if (clicked) {
                handleBuildingMovement(placingBuildingGridPosition);
            }
            if (rightClicked) {
                if (round.getIsMovingBuilding()) {
                    round.cancelMoveBuilding();
                } else {
                    removeBuilding(placingBuildingGridPosition);
                }
            }
        } else {
            // placing building
            if (rightClicked) {
                round.selectBuildingType(null);
            }
            if (clicked) {
                placeBuilding(placingType, placingBuildingGridPosition);
            }
            round.cancelMoveBuilding();
        }

        updatePlacedBuildingImages();
        updateMovingBuildingImage(placingBuildingGridPosition);
    }

    /**
     * Handles the movement of buildings.
     *
     * @param gridPosition The grid position to move the building to.
     */
    private void handleBuildingMovement(Vector2 gridPosition) {
        if (round.getIsMovingBuilding()) {
            try {
                round.moveBuilding(
                        Math.round(gridPosition.y),
                        Math.round(gridPosition.x)
                );
            } catch (BuildingPlacementException e) {
                // No handling needed.
            }

        } else {
            round.selectBuildingToMove(
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );
        }
    }

    /**
     * Places a building on the grid.
     *
     * @param type The type of building to place.
     * @param gridPosition The grid position to place the building at.
     */
    private void placeBuilding(BuildingType type, Vector2 gridPosition) {
        try {
            round.placeBuilding(
                    type,
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );
            SoundManager.getInstance().playClick();
            round.selectBuildingType(null);
        } catch (BuildingPlacementException e) {
            // No handling needed.
        }
    }

    /**
     * Removes a building from the grid.
     *
     * @param gridPosition The grid position of the building to remove.
     */
    private void removeBuilding(Vector2 gridPosition) {
        try {
            round.removeBuilding(
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );
        } catch (BuildingRemovalException e) {
            // No handling needed.
        }
    }

    /**
     * Updates the image of the building being placed.
     *
     * @param type The type of building being placed.
     * @param position The screen position of the building.
     * @param gridPosition The grid position of the building.
     */
    private void updatePlacingBuildingImage(BuildingType type, Vector2 position, Vector2 gridPosition) {
        if (type == null) {
            // remove placing building image
            if (placingBuildingImage != null) {
                placingBuildingImage.remove();
                placingBuildingImage = null;
            }
            return;
        }

        boolean wrongPlacingImage = placingBuildingImage == null || !type.equals(placingBuildingImage.getType());
        if (wrongPlacingImage) {
            // remove old placing building image
            if (placingBuildingImage != null) {
                placingBuildingImage.remove();
            }
            // add new placing building image
            placingBuildingImage = new BuildingImage(type);
            stage.addActor(placingBuildingImage);
        }

        if (placingBuildingImage != null) {
            // update placing building image
            boolean canPlacePlacingBuilding = round.getCanPlace(
                    type,
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );
            placingBuildingImage.setDragging(!canPlacePlacingBuilding);
            placingBuildingImage.updatePosition(position);
        }
    }

    /**
     * Converts the mouse position to the quantized position of a building at
     * that position.
     *
     * @param mousePos The current mouse position.
     * @return The building position.
     */
    private Vector2 getBuildingPosition(Vector2 mousePos) {
        Vector2 gridPosition = new Vector2(
                (mousePos.x - Consts.MENU_BAR_WIDTH) / Consts.CELL_SIZE,
                (Consts.WORLD_HEIGHT - mousePos.y) / Consts.CELL_SIZE
        );
        Vector2 snappedGridPosition = new Vector2(
                (float) Math.floor(gridPosition.x),
                (float) Math.floor(gridPosition.y) + 2
        );
        return new Vector2(
                (snappedGridPosition.x * Consts.CELL_SIZE) + Consts.MENU_BAR_WIDTH,
                Consts.WORLD_HEIGHT - (snappedGridPosition.y * Consts.CELL_SIZE)
        );
    }

    /**
     * Converts the screen position to the grid position, relative to the
     * grid-size.
     *
     * @param position The screen position.
     * @return The grid position.
     */
    private Vector2 getGridPosition(Vector2 position) {
        return new Vector2(
                ((position.x - Consts.MENU_BAR_WIDTH) / Consts.CELL_SIZE),
                (Consts.WORLD_HEIGHT - position.y) / Consts.CELL_SIZE
        );
    }

    /**
     * Adds the background to the stage.
     */
    private void addBackground() {
        // Draw background
        Image background = new Image(Assets.backgroundTexture);
        background.setBounds(0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage.addActor(background);

        // Draw paths
        Image paths = new Image(Assets.pathTexture);
        paths.setBounds(0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage.addActor(paths);
    }

    /**
     * Updates the images of the placed buildings.
     */
    private void updatePlacedBuildingImages() {
        for (BuildingImage image : placedBuildingImages) {
            image.remove();
        }
        placedBuildingImages.clear();

        for (Building building : round.getPlacedBuildings()) {
            BuildingImage image = new BuildingImage(building.getType());
            float screenX = (building.getCol() * Consts.CELL_SIZE) + Consts.MENU_BAR_WIDTH;
            float screenY = Consts.WORLD_HEIGHT - (building.getRow() * Consts.CELL_SIZE);
            image.setPlaced();
            image.setPosition(screenX, screenY);
            placedBuildingImages.add(image);
            stage.addActor(image);
        }
    }

    /**
     * Updates the image of the building being moved.
     *
     * @param gridPosition The grid position of the building.
     */
    private void updateMovingBuildingImage(Vector2 gridPosition) {
        if (movingBuildingImage != null) {
            movingBuildingImage.remove();
            movingBuildingImage = null;
        }

        Building movingBuilding = round.getMovingBuilding();
        if (movingBuilding == null) {
            return;
        }

        movingBuildingImage = new BuildingImage(movingBuilding.getType());
        float screenX = (gridPosition.x * Consts.CELL_SIZE) + Consts.MENU_BAR_WIDTH;
        float screenY = Consts.WORLD_HEIGHT - (gridPosition.y * Consts.CELL_SIZE);
        boolean canPlace = round.getCanPlace(
                movingBuilding.getType(),
                Math.round(gridPosition.y),
                Math.round(gridPosition.x)
        );

        movingBuildingImage.setDragging(!canPlace);
        movingBuildingImage.setPosition(screenX, screenY);
        stage.addActor(movingBuildingImage);
    }
}

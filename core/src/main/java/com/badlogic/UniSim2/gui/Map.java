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

public class Map {

    private final Stage stage;
    private final Round round;

    final private ArrayList<BuildingImage> placedBuildingImages;
    private BuildingImage placingBuildingImage;

    public Map(Stage stage, Round round) {
        this.stage = stage;
        this.round = round;

        this.placingBuildingImage = null;
        this.placedBuildingImages = new ArrayList<>();

        addBackground();
        stage.addActor(new GridActor());
    }

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
            if (rightClicked) {
                removeBuilding(placingBuildingGridPosition);
            }
        } else {
            // placing building
            if (clicked) {
                placeBuilding(placingType, placingBuildingGridPosition);
            }
        }

        updatePlacedBuildingImages();
    }

    private void placeBuilding(BuildingType type, Vector2 gridPosition) {
        try {
            round.placeBuilding(
                    type,
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );
            SoundManager.playClick();
            round.selectBuildingType(null);
        } catch (BuildingPlacementException e) {
        }
    }

    private void removeBuilding(Vector2 gridPosition) {
        try {
            round.removeBuilding(
                    Math.round(gridPosition.y),
                    Math.round(gridPosition.x)
            );

        } catch (BuildingRemovalException e) {
        }
    }

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

    private Vector2 getGridPosition(Vector2 position) {
        return new Vector2(
                ((position.x - Consts.MENU_BAR_WIDTH) / Consts.CELL_SIZE),
                (Consts.WORLD_HEIGHT - position.y) / Consts.CELL_SIZE
        );
    }

    private void addBackground() {
        // Draw grass
        Image background = new Image(Assets.backgroundTexture);
        background.setBounds(0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage.addActor(background);

        // Draw paths
        Image paths = new Image(Assets.pathTexture);
        paths.setBounds(0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        stage.addActor(paths);
    }

    private void updatePlacedBuildingImages() {
        for (BuildingImage image : placedBuildingImages) {
            image.remove();
        }
        placedBuildingImages.clear();

        for (Building building : round.getPlacedBuildings()) {
            BuildingImage image = new BuildingImage(building.getType());
            image.setPlaced();

            float screenX = (building.getCol() * Consts.CELL_SIZE) + Consts.MENU_BAR_WIDTH;
            float screenY = Consts.WORLD_HEIGHT - (building.getRow() * Consts.CELL_SIZE);

            image.setPosition(screenX, screenY);
            stage.addActor(image);
            placedBuildingImages.add(image);
        }
    }
}

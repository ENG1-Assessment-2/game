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
    private boolean isMoving;

    public Map(Stage stage, Round round) {
        this.stage = stage;
        this.round = round;

        this.placingBuildingImage = null;
        this.placedBuildingImages = new ArrayList<>();
        this.isMoving = false;

        addBackground();
        stage.addActor(new GridActor());
    }

    public void input(Vector2 mousePos, boolean clicked) {
        BuildingType placingType = round.getSelectedBuildingType();

        if (mousePos.x < Consts.MENU_BAR_WIDTH) {
            return;
        }

        Vector2 placingBuildingPosition = getBuildingPosition(mousePos);
        Vector2 placingBuildingGridPosition = getGridPosition(placingBuildingPosition);
        boolean selectedBuilding = round.isBuildingAt(Math.round(placingBuildingGridPosition.y),
                Math.round(placingBuildingGridPosition.x));

        if (selectedBuilding && !isMoving && clicked) {
            try {
                Building building = round.removeBuilding(Math.round(placingBuildingGridPosition.y),
                        Math.round(placingBuildingGridPosition.x));
                placingBuildingImage = new BuildingImage(building.getType());
                stage.addActor(placingBuildingImage);
                placingType = building.getType();
                isMoving = true;
            } catch (BuildingRemovalException e) {

            }
        }

        if (placingType == null) {
            if (placingBuildingImage != null) {
                placingBuildingImage.remove();
                placingBuildingImage = null;
            }
            return;
        }

        if (placingBuildingImage == null || !placingType.equals(placingBuildingImage.getType())) {
            if (placingBuildingImage != null) {
                placingBuildingImage.remove();
            }
            placingBuildingImage = new BuildingImage(placingType);
            stage.addActor(placingBuildingImage);
        }

        boolean canPlacePlacingBuilding = round.getCanPlace(
                placingType,
                Math.round(placingBuildingGridPosition.y),
                Math.round(placingBuildingGridPosition.x));

        placingBuildingImage.setDragging(!canPlacePlacingBuilding);
        placingBuildingImage.updatePosition(placingBuildingPosition);

        if (clicked) {
            try {
                round.placeBuilding(
                        placingType,
                        Math.round(placingBuildingGridPosition.y),
                        Math.round(placingBuildingGridPosition.x));
                SoundManager.playClick();
                round.selectBuildingType(null);
                isMoving = false;
            } catch (BuildingPlacementException e) {
            }
        }

        updatePlacedBuildingImages();
    }

    private Vector2 getBuildingPosition(Vector2 mousePos) {
        Vector2 gridPosition = new Vector2(
                (mousePos.x - Consts.MENU_BAR_WIDTH) / Consts.CELL_SIZE,
                (Consts.WORLD_HEIGHT - mousePos.y) / Consts.CELL_SIZE);
        Vector2 snappedGridPosition = new Vector2(
                (float) Math.floor(gridPosition.x),
                (float) Math.floor(gridPosition.y) + 2);
        return new Vector2(
                (snappedGridPosition.x * Consts.CELL_SIZE) + Consts.MENU_BAR_WIDTH,
                Consts.WORLD_HEIGHT - (snappedGridPosition.y * Consts.CELL_SIZE));
    }

    private Vector2 getGridPosition(Vector2 position) {
        return new Vector2(
                ((position.x - Consts.MENU_BAR_WIDTH) / Consts.CELL_SIZE),
                (Consts.WORLD_HEIGHT - position.y) / Consts.CELL_SIZE);
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

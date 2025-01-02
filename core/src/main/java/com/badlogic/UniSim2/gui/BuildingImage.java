package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BuildingImage extends Image {

    private BuildingType type;
    private Texture placedTexture;
    private Texture draggingTexture;
    private Texture collisionTexture;

    public BuildingImage(BuildingType type) {
        super(new TextureRegionDrawable());
        this.type = type;
        loadTextures();
        setDrawable(new TextureRegionDrawable(draggingTexture));
        setSize();
    }

    private void loadTextures() {
        switch (type) {
            case ACCOMMODATION:
                placedTexture = Assets.accomodationPlacedTexture;
                draggingTexture = Assets.accomodationDraggingTexture;
                collisionTexture = Assets.accomodationCollisionTexture;
                break;
            case LECTUREHALL:
                placedTexture = Assets.lectureHallPlacedTexture;
                draggingTexture = Assets.lectureHallDraggingTexture;
                collisionTexture = Assets.lectureHallCollisionTexture;
                break;
            case LIBRARY:
                placedTexture = Assets.libraryPlacedTexture;
                draggingTexture = Assets.libraryDraggingTexture;
                collisionTexture = Assets.libraryCollisionTexture;
                break;
            case COURSE:
                placedTexture = Assets.coursePlacedTexture;
                draggingTexture = Assets.courseDraggingTexture;
                collisionTexture = Assets.courseCollisionTexture;
                break;
            case FOODZONE:
                placedTexture = Assets.foodZonePlacedTexture;
                draggingTexture = Assets.foodZoneDraggingTexture;
                collisionTexture = Assets.foodZoneCollisionTexture;
                break;
            case RECREATIONAL:
                placedTexture = Assets.recreationalPlacedTexture;
                draggingTexture = Assets.recreationalDraggingTexture;
                collisionTexture = Assets.recreationalCollisionTexture;
                break;
            case NATURE:
                placedTexture = Assets.naturePlacedTexture;
                draggingTexture = Assets.natureDraggingTexture;
                collisionTexture = Assets.natureCollisionTexture;
                break;
            default:
                throw new IllegalArgumentException("Invalid building type");
        }
    }

    private void setSize() {
        switch (type) {
            case ACCOMMODATION:
                setSize(Consts.ACCOMODATION_WIDTH * Consts.CELL_SIZE, Consts.ACCOMODATION_HEIGHT * Consts.CELL_SIZE);
                break;
            case LECTUREHALL:
                setSize(Consts.LECTUREHALL_WIDTH * Consts.CELL_SIZE, Consts.LECTUREHALL_HEIGHT * Consts.CELL_SIZE);
                break;
            case LIBRARY:
                setSize(Consts.LIBRARY_WIDTH * Consts.CELL_SIZE, Consts.LIBRARY_HEIGHT * Consts.CELL_SIZE);
                break;
            case COURSE:
                setSize(Consts.COURSE_WIDTH * Consts.CELL_SIZE, Consts.COURSE_HEIGHT * Consts.CELL_SIZE);
                break;
            case FOODZONE:
                setSize(Consts.FOODZONE_WIDTH * Consts.CELL_SIZE, Consts.FOODZONE_WIDTH * Consts.CELL_SIZE);
                break;
            case RECREATIONAL:
                setSize(Consts.RECREATIONAL_WIDTH * Consts.CELL_SIZE, Consts.RECREATIONAL_HEIGHT * Consts.CELL_SIZE);
                break;
            case NATURE:
                setSize(Consts.NATURE_WIDTH * Consts.CELL_SIZE, Consts.NATURE_HEIGHT * Consts.CELL_SIZE);
                break;
            default:
                throw new IllegalArgumentException("Invalid building type");
        }
    }

    public void setPlaced() {
        setDrawable(new TextureRegionDrawable(placedTexture));
    }

    public void setDragging(boolean isColliding) {
        setDrawable(new TextureRegionDrawable(
                isColliding ? collisionTexture : draggingTexture
        ));
    }

    public BuildingType getType() {
        return type;
    }

    public void updatePosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public void updateType(BuildingType type) {
        this.type = type;
        loadTextures();
        setDrawable(new TextureRegionDrawable(draggingTexture));
        setSize();
    }
}

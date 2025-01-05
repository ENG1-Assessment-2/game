package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Represents an image of a building in the game.
 */
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

    /**
     * Sets the image to the placed texture.
     */
    public void setPlaced() {
        setDrawable(new TextureRegionDrawable(placedTexture));
    }

    /**
     * Sets the image to the dragging texture.
     *
     * @param isColliding Whether the building is colliding with another object.
     */
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

    /**
     * Updates the type of building and reloads the textures.
     *
     * @param type The new type of building.
     */
    public void updateType(BuildingType type) {
        this.type = type;
        loadTextures();
        setDrawable(new TextureRegionDrawable(draggingTexture));
        setSize();
    }

    /**
     * Loads the textures for the building based on its type.
     */
    private void loadTextures() {
        switch (type) {
            case ACCOMMODATION:
                placedTexture = Assets.accommodationPlacedTexture;
                draggingTexture = Assets.accommodationDraggingTexture;
                collisionTexture = Assets.accommodationCollisionTexture;
                break;
            case LECTUREHALL:
                placedTexture = Assets.lectureHallPlacedTexture;
                draggingTexture = Assets.lectureHallDraggingTexture;
                collisionTexture = Assets.lectureHallCollisionTexture;
                break;
            case BAR:
                placedTexture = Assets.barPlacedTexture;
                draggingTexture = Assets.barDraggingTexture;
                collisionTexture = Assets.barCollisionTexture;
                break;
            case FOODZONE:
                placedTexture = Assets.foodZonePlacedTexture;
                draggingTexture = Assets.foodZoneDraggingTexture;
                collisionTexture = Assets.foodZoneCollisionTexture;
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

    /**
     * Sets the size of the building image based on its type.
     */
    private void setSize() {
        switch (type) {
            case ACCOMMODATION:
                setSize(Consts.ACCOMMODATION_WIDTH * Consts.CELL_SIZE, Consts.ACCOMMODATION_HEIGHT * Consts.CELL_SIZE);
                break;
            case LECTUREHALL:
                setSize(Consts.LECTUREHALL_WIDTH * Consts.CELL_SIZE, Consts.LECTUREHALL_HEIGHT * Consts.CELL_SIZE);
                break;
            case BAR:
                setSize(Consts.BAR_WIDTH * Consts.CELL_SIZE, Consts.BAR_HEIGHT * Consts.CELL_SIZE);
                break;
            case FOODZONE:
                setSize(Consts.FOODZONE_WIDTH * Consts.CELL_SIZE, Consts.FOODZONE_HEIGHT * Consts.CELL_SIZE);
                break;
            case NATURE:
                setSize(Consts.NATURE_WIDTH * Consts.CELL_SIZE, Consts.NATURE_HEIGHT * Consts.CELL_SIZE);
                break;
            default:
                throw new IllegalArgumentException("Invalid building type");
        }
    }
}

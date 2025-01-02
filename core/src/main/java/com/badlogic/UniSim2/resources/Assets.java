package com.badlogic.UniSim2.resources;

import com.badlogic.UniSim2.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class is used to store all textures that will be used in the game. All
 * textures are made public static variable and should not be changed by another
 * other class.
 */
public class Assets {

    public static Music music;
    public static Sound click;

    public static Texture startBackgroundTexture;
    public static Texture startButtonUpTexture;
    public static Texture startButtonDownTexture;

    public static Texture backgroundTexture;
    public static Texture pathTexture;

    public static Texture menuBarTexture;

    public static Texture accommodationButtonUpTexture;
    public static Texture lectureHallButtonUpTexture;
    public static Texture barButtonUpTexture;
    public static Texture foodZoneButtonUpTexture;
    public static Texture natureButtonUpTexture;
    public static Texture[] buttonUpTextures;

    public static Texture accommodationButtonDownTexture;
    public static Texture lectureHallButtonDownTexture;
    public static Texture barButtonDownTexture;
    public static Texture foodZoneButtonDownTexture;
    public static Texture natureButtonDownTexture;
    public static Texture[] buttonDownTextures;

    public static Texture accommodationPlacedTexture;
    public static Texture accommodationCollisionTexture;
    public static Texture accommodationDraggingTexture;

    public static Texture lectureHallPlacedTexture;
    public static Texture lectureHallCollisionTexture;
    public static Texture lectureHallDraggingTexture;

    public static Texture barPlacedTexture;
    public static Texture barCollisionTexture;
    public static Texture barDraggingTexture;

    public static Texture foodZonePlacedTexture;
    public static Texture foodZoneCollisionTexture;
    public static Texture foodZoneDraggingTexture;

    public static Texture naturePlacedTexture;
    public static Texture natureCollisionTexture;
    public static Texture natureDraggingTexture;

    private Assets() {
    }

    /**
     * This method loads all the textures that might be used. Note that this
     * method should not be called before libgdx has called the
     * {@link Main#create()} method.
     */
    public static void loadTextures() {

        // =======================================
        // SOUND EFFECTS AND MUSIC.
        // =======================================
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));

        // Start menu textures
        startBackgroundTexture = new Texture("startBackground.png");
        startButtonUpTexture = new Texture("startButtonUp.png");
        startButtonDownTexture = new Texture("startButtonDown.png");

        // Background texture
        backgroundTexture = new Texture("background.png");
        pathTexture = new Texture("path.png");

        menuBarTexture = new Texture("menuBar.png");

        // =======================================
        // BUTTON TEXTURES
        // =======================================
        // Building button textures when not hovering over
        accommodationButtonUpTexture = new Texture("textures/buttons/accommodationButtonUp.png");
        lectureHallButtonUpTexture = new Texture("textures/buttons/lectureHallButtonUp.png");
        barButtonUpTexture = new Texture("textures/buttons/barButtonUp.png");
        foodZoneButtonUpTexture = new Texture("textures/buttons/foodZoneButtonUp.png");
        natureButtonUpTexture = new Texture("textures/buttons/natureButtonUp.png");
        buttonUpTextures = new Texture[]{
            accommodationButtonUpTexture,
            lectureHallButtonUpTexture,
            barButtonUpTexture,
            foodZoneButtonUpTexture,
            natureButtonUpTexture
        };

        // Building button textures when hovering over
        accommodationButtonDownTexture = new Texture("textures/buttons/accommodationButtonDown.png");
        lectureHallButtonDownTexture = new Texture("textures/buttons/lectureHallButtonDown.png");
        barButtonDownTexture = new Texture("textures/buttons/barButtonDown.png");
        foodZoneButtonDownTexture = new Texture("textures/buttons/foodZoneButtonDown.png");
        natureButtonDownTexture = new Texture("textures/buttons/natureButtonDown.png");
        buttonDownTextures = new Texture[]{
            accommodationButtonDownTexture,
            lectureHallButtonDownTexture,
            barButtonDownTexture,
            foodZoneButtonDownTexture,
            natureButtonDownTexture
        };

        // =======================================
        // BUILDING SPRITE TEXTURES
        // =======================================
        accommodationPlacedTexture = new Texture("textures/buildings/accommodationPlaced.png");
        accommodationCollisionTexture = new Texture("textures/buildings/accommodationCollision.png");
        accommodationDraggingTexture = new Texture("textures/buildings/accommodationDragging.png");

        lectureHallPlacedTexture = new Texture("textures/buildings/lectureHallPlaced.png");
        lectureHallCollisionTexture = new Texture("textures/buildings/lectureHallCollision.png");
        lectureHallDraggingTexture = new Texture("textures/buildings/lectureHallDragging.png");

        barPlacedTexture = new Texture("textures/buildings/barPlaced.png");
        barCollisionTexture = new Texture("textures/buildings/barCollision.png");
        barDraggingTexture = new Texture("textures/buildings/barDragging.png");

        foodZonePlacedTexture = new Texture("textures/buildings/foodZonePlaced.png");
        foodZoneCollisionTexture = new Texture("textures/buildings/foodZoneCollision.png");
        foodZoneDraggingTexture = new Texture("textures/buildings/foodZoneDragging.png");

        naturePlacedTexture = new Texture("textures/buildings/naturePlaced.png");
        natureCollisionTexture = new Texture("textures/buildings/natureCollision.png");
        natureDraggingTexture = new Texture("textures/buildings/natureDragging.png");
    }
}

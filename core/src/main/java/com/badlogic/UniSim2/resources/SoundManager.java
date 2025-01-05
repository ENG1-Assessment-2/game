package com.badlogic.UniSim2.resources;

/**
 * This class manages the sound effects and music in the game. It provides
 * methods to play, stop, and toggle mute for sounds and music.
 */
public class SoundManager {

    private static SoundManager instance;
    private boolean muted;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private SoundManager() {
        this.muted = false;
    }

    /**
     * Returns the singleton instance of the SoundManager.
     *
     * @return The singleton instance.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Plays the background music in a loop.
     */
    public void playMusic() {
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    /**
     * Stops the background music.
     */
    public void stopMusic() {
        Assets.music.stop();
    }

    /**
     * Plays the click sound effect if not muted.
     */
    public void playClick() {
        if (muted) {
            return;
        }
        Assets.click.play();
    }

    /**
     * Toggles the mute state of the sound manager.
     */
    public void toggleMute() {
        if (muted) {
            unmute();
        } else {
            mute();
        }
    }

    /**
     * Mutes all sounds and music.
     */
    private void mute() {
        Assets.music.setVolume(0);
        muted = true;
    }

    /**
     * Unmutes all sounds and music.
     */
    private void unmute() {
        Assets.music.setVolume(.5f);
        muted = false;
    }
}

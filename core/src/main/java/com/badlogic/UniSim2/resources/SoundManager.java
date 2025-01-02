package com.badlogic.UniSim2.resources;

public class SoundManager {

    private static SoundManager instance;
    private boolean muted;

    private SoundManager() {
        this.muted = false;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playMusic() {
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    public void stopMusic() {
        Assets.music.stop();
    }

    public void playClick() {
        if (muted) {
            return;
        }
        Assets.click.play();
    }

    public void toggleMute() {
        if (muted) {
            unmute();
        } else {
            mute();
        }
    }

    private void mute() {
        Assets.music.setVolume(0);
        muted = true;
    }

    private void unmute() {
        Assets.music.setVolume(.5f);
        muted = false;
    }
}

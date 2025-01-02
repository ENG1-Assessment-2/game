package com.badlogic.UniSim2.resources;

public class SoundManager {

    private static boolean isMuted = false;

    private SoundManager(){};

    public static void playMusic(){
        Assets.music.setVolume(.5f);
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    public static void stopMusic(){
        Assets.music.stop();
    }

    public static void playClick(){
        Assets.click.play();
    }

    public static void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            Assets.music.setVolume(0.0f);
        } else {
            Assets.music.setVolume(.5f);
        }
    }
}

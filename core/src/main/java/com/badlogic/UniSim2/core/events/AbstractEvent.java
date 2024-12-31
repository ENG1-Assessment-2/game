package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

public abstract class AbstractEvent {
    private final String name;
    private final String description;
    private final int triggerTime;
    private final Round currentRound;

    protected AbstractEvent(String name, String description, int time, Round round){
        this.name = name;
        this.description = description;
        this.triggerTime = time;
        this.currentRound = round;
    }

    public abstract void trigger();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTriggerTime() {
        return triggerTime;
    }

    public Round getCurrentRound() {
        return currentRound;
    }
}

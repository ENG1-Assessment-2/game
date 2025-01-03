package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

public abstract class Event {

    private final String name;
    private final String description;
    private final int triggerTime;
    private boolean triggered = false;
    protected final Round round;
    protected float elapsedTime;
    private float timer;

    public Event(String name, String description, int triggerTime, Round round) {
        this.name = name;
        this.description = description;
        this.triggerTime = triggerTime;
        this.round = round;
        this.elapsedTime = -1;
        this.timer = -1;
    }

    public void update(float delta) {
        this.elapsedTime += delta;

        if (timer > 0) {
            timer -= delta;
            if (timer <= 0) {
                timer = -1;
                handleResponse();
            }
        }
    }

    public boolean attemptTrigger() {
        if (triggered) {
            return false;
        }

        if (elapsedTime < triggerTime) {
            return false;
        }

        handleTrigger();
        triggered = true;
        return true;
    }

    protected void waitForResponse(float timer) {
        this.timer = timer;
    }

    protected abstract void handleTrigger();

    protected abstract void handleResponse();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

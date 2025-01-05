package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

/**
 * Represents an abstract event in the game. Subclasses should implement the
 * handleTrigger and handleResponse methods.
 */
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

    /**
     * Updates the event's state.
     *
     * @param delta The time elapsed since the last update.
     */
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

    /**
     * Attempts to trigger the event.
     *
     * @return True if the event was triggered, false otherwise.
     */
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

    /**
     * Waits for a response for a specified amount of time. When the time is
     * over, the handleResponse method is called.
     *
     * @param timer The time to wait for a response.
     */
    protected void waitForResponse(float timer) {
        this.timer = timer;
    }

    /**
     * Handles the triggering of the event. Subclasses should implement this
     * method to define the event's behavior when triggered.
     */
    protected abstract void handleTrigger();

    /**
     * Handles the response to the event. Subclasses should implement this
     * method to define the event's behavior when the response time elapses.
     */
    protected abstract void handleResponse();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

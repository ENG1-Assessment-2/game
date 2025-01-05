package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;

/**
 * Represents a royal visit event in the game where a member of the royal family
 * visits the university.
 */
public class RoyalEvent extends Event {

    public RoyalEvent(Round round) {
        super("A Royal Visit?",
                "A member of the royal family has decided to pay a visit to your university. How will your students react?",
                Consts.MAX_TIME / 2, round
        );
    }

    /**
     * Handles the triggering of the royal visit event.
     */
    @Override
    protected void handleTrigger() {
        // This event is neutral.
    }

    /**
     * Handles the response to the royal visit event.
     */
    @Override
    protected void handleResponse() {
        // This event is neutral.
    }
}

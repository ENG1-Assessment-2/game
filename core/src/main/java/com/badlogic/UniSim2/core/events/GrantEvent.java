package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;

/**
 * Represents a grant event in the game where the university receives a grant.
 */
public class GrantEvent extends Event {

    public GrantEvent(Round round) {
        super(
                "A Nice Bonus!",
                "The government has decided to give your university a grant! You received £500k",
                (int) (Consts.MAX_TIME * Math.random()),
                round
        );
    }

    /**
     * Handles the triggering of the grant event. Adds £500k to the university's
     * funds.
     */
    @Override
    protected void handleTrigger() {
        round.addToFunds(500 * 1000);
    }

    /**
     * Handles the response to the grant event.
     */
    @Override
    protected void handleResponse() {
        // No response handling needed for this event.
    }
}

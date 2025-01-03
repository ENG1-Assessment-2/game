package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;

public class GrantEvent extends Event {

    public GrantEvent(Round round) {
        super(
                "A Nice Bonus!",
                "The government has decided to give your university a grant! You receieved £500k",
                (int) (Consts.MAX_TIME * Math.random()),
                round
        );
    }

    @Override
    protected void handleTrigger() {
        round.addToFunds(500 * 1000);
    }

    @Override
    protected void handleResponse() {

    }
}

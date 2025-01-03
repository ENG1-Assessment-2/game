package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.resources.Consts;

public class RoyalEvent extends Event {

    public RoyalEvent(Round round) {
        super("A Royal Visit?",
                "A member of the royal family has decided to pay a visit to your univeristy. How will your students react?",
                Consts.MAX_TIME / 2, round
        );
    }

    @Override
    protected void handleTrigger() {
    }

    @Override
    protected void handleResponse() {
    }
}

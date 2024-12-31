package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

public class EventGrant extends AbstractEvent{
    private static final String NAME = "A Nice Bonus!";
    private static final String DESCRIPTION = "The government has decided to give your university a grant! Collect £XXX,XXX.";

    private static final Integer BONUS = 1000;

    public EventGrant(int time, Round round) {
        super(NAME, DESCRIPTION, time, round);
    }

    @Override
    public void trigger() {
        Round round = getCurrentRound();
        // Add bonus using appropriate method here
        System.out.println("Grant event triggered successfully");

    }
}

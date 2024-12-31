package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

public class EventRoyal extends AbstractEvent{

    private static final String NAME = "A Royal Visit?";
    private static final String DESCRIPTION = "A member of the royal family has decided to pay a visit to your" +
        "university! Who knows how your students will react?";

    public EventRoyal(Integer time, Round round) {
        super(NAME, DESCRIPTION, time, round);
    }

    @Override
    public void trigger() {
        System.out.println("Royal event triggered successfully");
    }
}

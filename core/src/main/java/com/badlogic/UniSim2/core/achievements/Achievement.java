package com.badlogic.UniSim2.core.achievements;

import com.badlogic.UniSim2.core.Round;

public abstract class Achievement {

    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean getCompleted(Round round);
}

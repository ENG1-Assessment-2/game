package com.badlogic.UniSim2.core.events;

import com.badlogic.UniSim2.core.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {
    private final Round currentRound;
    private final Random RANDOM = new Random();
    private final List<AbstractEvent> plannedEvents;
    private AbstractEvent nextEvent;
    private EventFire nextActiveEvent;
    public EventManager(Round currentRound) {
        this.currentRound = currentRound;
        this.plannedEvents = generateEvents();
        this.nextEvent = getNextEvent(plannedEvents);
        this.nextActiveEvent = null;
        System.out.println("Event manager instantiated");
    }

    public void eventLauncher(float elapsedTime){
        if (elapsedTime >= nextEvent.getTriggerTime()){
            nextEvent.trigger();
            if (nextEvent instanceof EventFire){
                nextActiveEvent = (EventFire) nextEvent;
            }
            nextEvent = getNextEvent(plannedEvents);
        }

        if (nextActiveEvent != null) {
            if (elapsedTime >= (nextActiveEvent.getTriggerTime() + nextActiveEvent.getDelay())) {
                nextActiveEvent.trigger2();
                nextActiveEvent = null;
            }
        }
    }

    private List<AbstractEvent> generateEvents(){
        List<AbstractEvent> result = new ArrayList<>();
        boolean royalUsed = false;

        int t1 = 30 + RANDOM.nextInt(210 - 30 + 1);
        int t2 = (t1 + 30) + RANDOM.nextInt(240 - (t1 + 30) + 1);
        int t3 = (t2 + 30) + RANDOM.nextInt(270 - (t2 + 30) + 1);

        int[] times = {t1, t2, t3};

        for (int i = 0; i < 3; i++){
            AbstractEvent event = randomEvent(royalUsed, times[i]);
            result.add(event);
            if (event.getClass() == EventRoyal.class){
                royalUsed = true;
            }
        }

        return result;
    }

    private AbstractEvent randomEvent(boolean royalUsed, int time) {
        if (!royalUsed){
            int r = RANDOM.nextInt(3);
            switch (r) {
                case 0: return new EventFire(time, currentRound);
                case 1: return new EventGrant(time, currentRound);
                case 2: return new EventRoyal(time, currentRound);
            }
        } else {
            int r = RANDOM.nextInt(2);
            return (r == 0) ? new EventFire(time, currentRound) : new EventGrant(time, currentRound);
        }

        throw new IllegalStateException("Unexpected event choice!");
    }

    public <T extends AbstractEvent> T getNextEvent(List<T> events) {
        T nextEvent = events.get(0);
        events.remove(nextEvent);
        return nextEvent;
    }

    public boolean isFinished(){
        return plannedEvents.isEmpty() && nextActiveEvent == null;
    }
}

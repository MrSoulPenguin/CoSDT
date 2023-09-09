package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.challenge.event.Event;

public class TimedChallenge extends AbstractChallenge implements TickingChallenge {

    private final long timeToCompleteMillis;
    private long startTime;

    public TimedChallenge(Goal goal, long timeToCompleteMillis, Event rewardEvent, Event punishmentEvent) {
        super(goal, rewardEvent, punishmentEvent);

        this.timeToCompleteMillis = timeToCompleteMillis;
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void tick() {
        if (System.currentTimeMillis() - this.startTime >= this.timeToCompleteMillis) {
            this.onFailure(this.getParticipant());
        }
    }
}

package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;

public class TimedChallenge extends AbstractChallenge implements TickingChallenge {

    private final long timeToCompleteMillis;
    private long startTime;

    public TimedChallenge(PlayerEntity participant, Goal goal, long timeToCompleteMillis, Event rewardEvent, Event punishmentEvent) {
        super(participant, goal, rewardEvent, punishmentEvent);

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

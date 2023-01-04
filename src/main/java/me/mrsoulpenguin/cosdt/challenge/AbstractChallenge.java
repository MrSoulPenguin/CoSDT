package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.AbstractGoal;
import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public abstract class AbstractChallenge {

    private final PlayerEntity participant;
    private final AbstractGoal goal;
    private final Event rewardEvent;
    private final Event punishmentEvent;

    public AbstractChallenge(PlayerEntity participant, AbstractGoal goal, Event rewardEvent, Event punishmentEvent) {
        this.participant = participant;
        this.goal = goal;
        this.rewardEvent = rewardEvent;
        this.punishmentEvent = punishmentEvent;
    }

    public PlayerEntity getParticipant() {
        return this.participant;
    }

    public AbstractGoal getGoal() {
        return goal;
    }

    public abstract void start();

    /**
     * Executes AbstractChallenge#onSuccess() or AbstractChallenge#onFailure() depending on if the result is
     * ActionResult.SUCCESS or ActionResult.FAIL respectively.
     *
     * <p>Any other result doesn't to anything.
     *
     * @param result Result of the challenge.
     * @see AbstractChallenge#onSuccess(PlayerEntity participant)
     * @see AbstractChallenge#onFailure(PlayerEntity participant)
     * @see ActionResult
     */
    public void stop(ActionResult result) {
        switch (result) {
            case SUCCESS -> this.onSuccess(this.participant);
            case FAIL -> this.onFailure(this.participant);
        }

        this.remove();
    }

    protected void onSuccess(PlayerEntity participant) {
        this.rewardEvent.execute(participant);
        this.remove();
    }

    protected void onFailure(PlayerEntity participant) {
        this.punishmentEvent.execute(participant);
        this.remove();
    }

    private void remove() {
        ((ChallengeHolder) this.participant).removeChallenge(this);
    }

}

package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.challenge.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class Challenge {

    private PlayerEntity participant;
    private final Goal goal;
    private final Event rewardEvent;
    private final Event punishmentEvent;

    public Challenge(Goal goal, Event rewardEvent, Event punishmentEvent) {
        this.goal = goal;
        this.rewardEvent = rewardEvent;
        this.punishmentEvent = punishmentEvent;
    }

    public PlayerEntity getParticipant() {
        return this.participant;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setParticipant(PlayerEntity participant) {
        this.participant = participant;
        ((Participant) participant).addChallenge(this);
    }

    public void start() {
        this.goal.notifyParticipant(this.participant);
    };

    /**
     * Executes AbstractChallenge#onSuccess() or AbstractChallenge#onFailure() depending on if the result is
     * ActionResult.SUCCESS or ActionResult.FAIL respectively.
     *
     * <p>Any other result doesn't to anything.
     *
     * @param result Result of the challenge.
     * @see Challenge#onSuccess(PlayerEntity participant)
     * @see Challenge#onFailure(PlayerEntity participant)
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
    }

    protected void onFailure(PlayerEntity participant) {
        this.punishmentEvent.execute(participant);
    }

    private void remove() {
        ((Participant) this.participant).removeChallenge(this);
    }

}

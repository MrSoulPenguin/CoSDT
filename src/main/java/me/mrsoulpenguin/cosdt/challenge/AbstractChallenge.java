package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;

public abstract class AbstractChallenge {

    private PlayerEntity participant;
    private final Goal goal;
    private final Event rewardEvent;
    private final Event punishmentEvent;

    public AbstractChallenge(Goal goal, Event rewardEvent, Event punishmentEvent) {
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
        ((ChallengeHolder) participant).addChallenge(this);
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
    }

    protected void onFailure(PlayerEntity participant) {
        this.punishmentEvent.execute(participant);
    }

    private void remove() {
        ((ChallengeHolder) this.participant).removeChallenge(this);
    }

}

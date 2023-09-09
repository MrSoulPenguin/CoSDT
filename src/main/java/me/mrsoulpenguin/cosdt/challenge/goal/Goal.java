package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.entity.player.PlayerEntity;

public interface Goal {

    /**
     * Notifies the participant of the current goal.
     */
    void notifyParticipant(PlayerEntity participant);

    boolean test(Object object);

}

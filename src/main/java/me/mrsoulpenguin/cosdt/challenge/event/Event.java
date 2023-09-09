package me.mrsoulpenguin.cosdt.challenge.event;

import net.minecraft.entity.player.PlayerEntity;

public interface Event {

    void execute(PlayerEntity recipient);

    void undo(PlayerEntity recipient);

}

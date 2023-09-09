package me.mrsoulpenguin.cosdt.event;

import net.minecraft.entity.player.PlayerEntity;

public interface Event {

    void execute(PlayerEntity recipient);

}

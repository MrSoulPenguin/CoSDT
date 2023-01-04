package me.mrsoulpenguin.cosdt.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface Event {

    ActionResult execute(PlayerEntity recipient);

}

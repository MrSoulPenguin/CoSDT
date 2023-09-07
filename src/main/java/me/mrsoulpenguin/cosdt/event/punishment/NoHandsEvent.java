package me.mrsoulpenguin.cosdt.event.punishment;

import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class NoHandsEvent implements Event {

    @Override
    public ActionResult execute(PlayerEntity recipient) {
        GameRenderer gameRenderer = MinecraftClient.getInstance().gameRenderer;
        gameRenderer.setRenderHand(false);
        return ActionResult.SUCCESS;
    }

}
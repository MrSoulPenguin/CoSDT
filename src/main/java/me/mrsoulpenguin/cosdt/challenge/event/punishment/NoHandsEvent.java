package me.mrsoulpenguin.cosdt.challenge.event.punishment;

import me.mrsoulpenguin.cosdt.challenge.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;

public class NoHandsEvent implements Event {

    @Override
    public void execute(PlayerEntity recipient) {
        GameRenderer gameRenderer = MinecraftClient.getInstance().gameRenderer;
        gameRenderer.setRenderHand(false);
    }

}
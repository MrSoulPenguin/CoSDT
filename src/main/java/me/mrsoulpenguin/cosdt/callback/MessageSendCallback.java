package me.mrsoulpenguin.cosdt.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface MessageSendCallback {

    Event<MessageSendCallback> EVENT = EventFactory.createArrayBacked(MessageSendCallback.class,
            (listeners) -> (player, message) -> {
                for (MessageSendCallback callback : listeners) {
                    ActionResult result = callback.chat(player, message);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult chat(PlayerEntity player, String message);

}

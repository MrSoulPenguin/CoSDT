package me.mrsoulpenguin.cosdt.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;

public interface PickupItemCallback {

    Event<PickupItemCallback> EVENT = EventFactory.createArrayBacked(PickupItemCallback.class,
            (listeners) -> (player, item) -> {
                for (PickupItemCallback callback : listeners) {
                    ActionResult result = callback.pickup(player, item);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult pickup(PlayerEntity player, Item item);

}

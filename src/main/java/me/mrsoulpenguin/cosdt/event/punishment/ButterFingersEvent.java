package me.mrsoulpenguin.cosdt.event.punishment;

import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ButterFingersEvent implements Event {

    @Override
    public void execute(PlayerEntity recipient) {
        PlayerInventory inventory = recipient.getInventory();
        ItemStack mainStack = inventory.getMainHandStack();

        ItemEntity itemEntity = recipient.dropItem(mainStack.copy(), false, false);
        if (itemEntity != null) {
            mainStack.setCount(0);
            itemEntity.setInvulnerable(true);
            itemEntity.setNeverDespawn();
            itemEntity.setPickupDelayInfinite();
        }

    }

}

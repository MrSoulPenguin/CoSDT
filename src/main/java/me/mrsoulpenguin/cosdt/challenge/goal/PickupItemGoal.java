package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PickupItemGoal implements Goal {

    private final Item item;

    public PickupItemGoal(Item item) {
        this.item = item;
    }

    @Override
    public void notifyParticipant(PlayerEntity participant) {
        ((ServerPlayerEntity) participant).networkHandler.sendPacket(new TitleS2CPacket(Text.of("Goal: Pickup a " + this.item.getName())));
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof Item newItem) {
            return newItem.equals(this.item);
        }

        return false;
    }
}

package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class PickupItemGoal implements Goal {

    private final Item item;

    public PickupItemGoal(NbtCompound nbt) {
        this.item = ItemStack.fromNbt(nbt.getCompound("item")).getItem();
    }

    @Override
    public void notifyParticipant(PlayerEntity participant) {
        Text text = MutableText.of(Text.of("Goal: Pickup ").getContent()).append(Text.translatable(this.item.getTranslationKey()));
        ((ServerPlayerEntity) participant).networkHandler.sendPacket(new TitleS2CPacket(text));
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof Item newItem) {
            return newItem.equals(this.item);
        }

        return false;
    }
}

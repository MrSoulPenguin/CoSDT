package me.mrsoulpenguin.cosdt.challenge.event.reward;

import me.mrsoulpenguin.cosdt.challenge.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ItemGiveEvent implements Event {

    private final ItemStack itemStack;

    public ItemGiveEvent(NbtCompound nbt) {
        this.itemStack = ItemStack.fromNbt(nbt.getCompound("item"));
    }

    @Override
    public void execute(PlayerEntity recipient) {
        //Have to save these as the itemstack might not exist ones given to the player due to stacking.
        Text itemName = this.itemStack.getName();
        int itemAmount = this.itemStack.getCount();

        recipient.getInventory().offer(this.itemStack, true);
        recipient.sendMessage(((MutableText) Text.of("Obtained ")).append(itemName).append(Text.of(" x" + itemAmount)));
    }

    @Override
    public void undo(PlayerEntity recipient) {
        // Nothing to undo
    }


}

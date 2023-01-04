package me.mrsoulpenguin.cosdt.event.reward;

import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class ItemGiveEvent implements Event {

    private final ItemStack itemStack;

    public ItemGiveEvent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ActionResult execute(PlayerEntity recipient) {
        //Have to save these as the itemstack might not exist ones given to the player due to stacking.
        Text itemName = this.itemStack.getName();
        int itemAmount = this.itemStack.getCount();

        recipient.getInventory().offer(this.itemStack, true);
        recipient.sendMessage(((MutableText) Text.of("Obtained ")).append(itemName).append(Text.of(" x" + itemAmount)));
        return ActionResult.SUCCESS;
    }
}

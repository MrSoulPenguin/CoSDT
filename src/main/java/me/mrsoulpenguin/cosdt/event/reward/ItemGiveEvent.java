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
        recipient.getInventory().offer(this.itemStack, true);
        return ActionResult.SUCCESS;
    }

    @Override
    public void notify(PlayerEntity player) {
        player.sendMessage(((MutableText) Text.of("Obtained ")).append(this.itemStack.getName()).append(Text.of(" x" + this.itemStack.getCount())));
    }
}

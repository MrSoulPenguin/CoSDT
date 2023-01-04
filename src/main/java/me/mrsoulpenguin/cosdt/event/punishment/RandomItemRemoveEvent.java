package me.mrsoulpenguin.cosdt.event.punishment;

import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RandomItemRemoveEvent implements Event {

    @Override
    public ActionResult execute(PlayerEntity recipient) {
        PlayerInventory inventory = recipient.getInventory();
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.addAll(inventory.main);
        itemStacks.addAll(inventory.armor);
        itemStacks.addAll(inventory.offHand);

        Collections.shuffle(itemStacks);
        Optional<ItemStack> optItemStack = itemStacks.stream()
                .filter(stack -> !stack.isEmpty())
                .findAny();

        if (optItemStack.isPresent()) {
            optItemStack.get().setCount(0);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public void notify(PlayerEntity player) {
        player.sendMessage(Text.of("Something seems to be missing."));
    }

}

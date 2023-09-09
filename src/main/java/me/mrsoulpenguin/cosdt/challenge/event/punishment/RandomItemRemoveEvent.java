package me.mrsoulpenguin.cosdt.challenge.event.punishment;

import me.mrsoulpenguin.cosdt.challenge.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RandomItemRemoveEvent implements Event {

    @Override
    public void execute(PlayerEntity recipient) {
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
            recipient.sendMessage(Text.of("Something seems to be missing."));
        }

    }

}

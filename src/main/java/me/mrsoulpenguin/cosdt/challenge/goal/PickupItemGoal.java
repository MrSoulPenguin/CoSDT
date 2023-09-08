package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.item.Item;

public class PickupItemGoal extends Goal {

    private final Item item;

    public PickupItemGoal(Item item) {
        this.item = item;
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof Item newItem) {
            return newItem.equals(this.item);
        }

        return false;
    }
}

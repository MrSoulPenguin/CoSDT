package me.mrsoulpenguin.cosdt.mixin.entity;

import me.mrsoulpenguin.cosdt.callback.PickupItemCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;sendPickup(Lnet/minecraft/entity/Entity;I)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void invokePickupItemCallback(PlayerEntity player, CallbackInfo ci, ItemStack itemStack, Item item, int i) {
        PickupItemCallback.EVENT.invoker().pickup(player, item);
    }

}

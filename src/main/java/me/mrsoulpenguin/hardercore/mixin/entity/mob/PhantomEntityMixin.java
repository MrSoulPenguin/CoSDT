package me.mrsoulpenguin.hardercore.mixin.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends Entity {

    public PhantomEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract void setPhantomSize(int size);

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;setPhantomSize(I)V"))
    private void setRandomSize(PhantomEntity phantom, int size) {
        this.setPhantomSize(this.random.nextBetween(1, 25));
    }

}

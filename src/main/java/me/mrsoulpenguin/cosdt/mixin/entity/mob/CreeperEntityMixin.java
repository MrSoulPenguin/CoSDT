package me.mrsoulpenguin.cosdt.mixin.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {

    @Shadow private int explosionRadius;

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion alwaysPoweredExplosions(World world, Entity entity, double x, double y, double z, float power, Explosion.DestructionType destructionType) {
        float explosionPower = (power == this.explosionRadius * 2.0F) ? power : this.explosionRadius * 1.5F;
        return world.createExplosion(entity, x, y, z, explosionPower, destructionType);
    }

}

package me.mrsoulpenguin.cosdt.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {

    @Unique
    private static final List<EntityType<?>> mountTypes = List.of(
            EntityType.COW, EntityType.OCELOT, EntityType.CAT, EntityType.WOLF,
            EntityType.MOOSHROOM, EntityType.CHICKEN, EntityType.PIG, EntityType.PANDA,
            EntityType.SHEEP, EntityType.HORSE, EntityType.DONKEY, EntityType.MULE,
            EntityType.ZOMBIE_HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK, EntityType.ZOMBIFIED_PIGLIN, EntityType.SPIDER, EntityType.CAVE_SPIDER,
            EntityType.BAT
    );

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextFloat()F", ordinal = 1))
    private float skipFirstCheck(Random random) {
        return 0.06F;
    }

    @ModifyArg(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieEntity;startRiding(Lnet/minecraft/entity/Entity;)Z", ordinal = 1))
    private Entity tryOtherMounts(Entity entity) {
        EntityType<?> mountType = mountTypes.get(this.random.nextBetween(0, mountTypes.size() - 1));
        Entity mountEntity = mountType.create(this.world);
        assert mountEntity != null;
        mountEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);

        LocalDifficulty localDifficulty = this.world.getLocalDifficulty(this.getBlockPos());
        if (mountEntity instanceof AnimalEntity animalEntity) {
            animalEntity.initialize((ServerWorldAccess) this.world, localDifficulty, SpawnReason.JOCKEY, null, null);
        } else if (mountEntity instanceof MobEntity mobEntity) {
            mobEntity.initialize((ServerWorldAccess) this.world, localDifficulty, SpawnReason.JOCKEY, null, null);
        }

        return mountEntity;
    }

    @ModifyArg(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private Entity spawnTrueMount(Entity entity) {
        return this.getVehicle();
    }

}

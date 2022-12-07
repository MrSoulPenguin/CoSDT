package me.mrsoulpenguin.cosdt.mixin.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow protected abstract boolean addEntity(Entity entity);

    @Inject(method = "spawnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;addEntity(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    private void trySpawnGiant(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ZombieEntity) {
            if (entity.getY() >= this.getSeaLevel()) {
                if (!(this.random.nextFloat() >= 0.1F)) {
                    World world = entity.getWorld();
                    BlockPos pos = entity.getBlockPos();
                    GiantEntity giant = new GiantEntity(EntityType.GIANT, world);
                    giant.refreshPositionAndAngles(pos, 0.0F, 0.0F);

                    cir.setReturnValue(this.addEntity(giant));
                }
            }
        }
    }

}

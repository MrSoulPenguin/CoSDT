package me.mrsoulpenguin.cosdt.mixin.world.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {

    @Shadow private int cooldown;

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    private void reworkSpawning(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
        if (!spawnMonsters) {
            cir.setReturnValue(0);
        } else {
            Random random = world.random;
            this.cooldown--;

            if (this.cooldown > 0) {
                cir.setReturnValue(0);
            } else {
                this.cooldown += (60 + random.nextInt(60)) * 10;
                if (!(world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight())) {
                    int spawnedAmount = 0;
                    Iterator<ServerPlayerEntity> playerIterator = world.getPlayers().iterator();

                    while (true) {
                        LocalDifficulty localDifficulty;
                        BlockPos spawnPos;
                        BlockState blockState;
                        FluidState fluidState;

                        BlockPos playerPos;
                        do {
                            ServerPlayerEntity player;
                            do {
                                do {
                                    do {
                                        if (!playerIterator.hasNext()) {
                                            cir.setReturnValue(spawnedAmount);
                                            return;
                                        }

                                        player = playerIterator.next();
                                    } while (player.isSpectator());

                                    playerPos = player.getBlockPos();
                                } while (world.getDimension().hasSkyLight() && (playerPos.getY() < world.getSeaLevel() || !world.isSkyVisible(playerPos)));

                                localDifficulty = world.getLocalDifficulty(playerPos);
                            } while (!localDifficulty.isHarderThan(random.nextFloat() * 1.5F));

                            spawnPos = playerPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
                            blockState = world.getBlockState(spawnPos);
                            fluidState = world.getFluidState(spawnPos);
                        } while (!SpawnHelper.isClearForSpawn(world, spawnPos, blockState, fluidState, EntityType.PHANTOM));

                        EntityData entityData = null;
                        int amountToSpawn = random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);

                        for (int i = 0; i < amountToSpawn; i++) {
                            PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
                            assert phantomEntity != null;
                            phantomEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
                            entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
                            world.spawnEntityAndPassengers(phantomEntity);
                        }

                        spawnedAmount += spawnedAmount;
                    }
                }
            }
        }
    }

}
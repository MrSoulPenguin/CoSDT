package me.mrsoulpenguin.hardercore.mixin.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreeperIgniteGoal.class)
public class CreeperIgniteGoalMixin {

    @Shadow @Final private CreeperEntity creeper;

    @Shadow private @Nullable LivingEntity target;

    /**
     * @author MrSoulPenguin
     * @reason Easiest way to increase creeper ignite distance.
     */
    @Overwrite
    public boolean canStart() {
        LivingEntity livingEntity = this.creeper.getTarget();
        return this.creeper.getFuseSpeed() > 0 || livingEntity != null && this.creeper.squaredDistanceTo(livingEntity) < 13.0F;
    }

    /**
     * @author MrSoulPenguin
     * @reason Easiest way to increase creeper fuse speed.
     */
    @Overwrite
    public void tick() {
        if (this.target == null) {
            this.creeper.setFuseSpeed(-1);
        } else if (this.creeper.squaredDistanceTo(this.target) > 49.0) {
            this.creeper.setFuseSpeed(-1);
        } else if (!this.creeper.getVisibilityCache().canSee(this.target)) {
            this.creeper.setFuseSpeed(-1);
        } else {
            this.creeper.setFuseSpeed(2);
        }
    }

}

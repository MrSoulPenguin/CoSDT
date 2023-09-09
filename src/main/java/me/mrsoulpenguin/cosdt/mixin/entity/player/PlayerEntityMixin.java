package me.mrsoulpenguin.cosdt.mixin.entity.player;

import com.mojang.datafixers.util.Either;
import me.mrsoulpenguin.cosdt.challenge.Challenge;
import me.mrsoulpenguin.cosdt.challenge.Participant;
import me.mrsoulpenguin.cosdt.challenge.TickingChallenge;
import me.mrsoulpenguin.cosdt.challenge.event.TickingEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Participant {

    private final Set<Challenge> activeChallenges = new HashSet<>();
    private final Set<TickingEvent> activeTickingEvents = new HashSet<>();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public abstract void wakeUp(boolean skipSleepTimer, boolean updateSleepingPlayers);

    @Shadow public abstract void wakeUp();

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Unique
    private long lastTimeSinceSound = System.currentTimeMillis();

    @Unique
    private boolean cantSleep = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tryRandomSound(CallbackInfo ci) {
        long currentTime = System.currentTimeMillis();

        //Every two minutes.
        if (currentTime - this.lastTimeSinceSound >= 120000) {
            this.lastTimeSinceSound = currentTime;

            SoundEvent soundEvent;
            if (!(random.nextFloat() >= 0.5F)) {
                //noinspection OptionalGetWithoutIsPresent
                soundEvent = Registry.SOUND_EVENT.getRandom(this.random).get().value();
            } else {
                soundEvent = SoundEvents.ENTITY_CREEPER_PRIMED;
            }

            this.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void resetCantSleep(CallbackInfo ci) {
        if (this.cantSleep && this.world.isDay()) {
            this.cantSleep = false;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickChallengesAndEvents(CallbackInfo ci) {
        this.activeChallenges.forEach(challenge -> {
            if (challenge instanceof TickingChallenge tickingChallenge) {
                tickingChallenge.tick();
            }
        });

        this.activeTickingEvents.forEach(TickingEvent::tick);
    }

    @Inject(method = "trySleep", at = @At("HEAD"), cancellable = true)
    private void checkCantSleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        if (!(this.random.nextFloat() >= 0.25F) || this.cantSleep) {
            if (!this.cantSleep) this.cantSleep = true;
            this.wakeUp(false, true);
            this.sendMessage(Text.of("The voices keep you awake tonight"), true);
            cir.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));
        }
    }

    @Override
    public void addChallenge(Challenge challenge) {
        this.activeChallenges.add(challenge);
    }

    @Override
    public void addTickingEvent(TickingEvent event) {
        this.activeTickingEvents.add(event);
    }

    @Override
    public void removeChallenge(Challenge challenge) {
        this.activeChallenges.remove(challenge);
    }

    @Override
    public void removeTickingEvent(TickingEvent event) {
        this.activeTickingEvents.remove(event);
    }

    @Override
    public Set<Challenge> getActiveChallenges() {
        return this.activeChallenges;
    }

    @Override
    public Set<TickingEvent> getActiveTickingEvents() {
        return this.activeTickingEvents;
    }

}

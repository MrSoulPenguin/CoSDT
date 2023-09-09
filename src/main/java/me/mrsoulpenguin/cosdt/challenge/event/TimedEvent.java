package me.mrsoulpenguin.cosdt.challenge.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class TimedEvent implements Event, TickingEvent {

    private Event internalEvent;
    private PlayerEntity recipient;
    private final long runtime;
    private long startTime;

    public TimedEvent(NbtCompound nbt) {
        EventType.getEventFromNbt(nbt).ifPresent(event -> this.internalEvent = event);
        this.runtime = nbt.getLong("runtime");
    }

    @Override
    public void execute(PlayerEntity recipient) {
        if (this.internalEvent == null) {
            return;
        }

        this.recipient = recipient;
        this.startTime = System.currentTimeMillis();
        this.internalEvent.execute(recipient);
    }

    @Override
    public void undo(PlayerEntity recipient) {
        this.internalEvent.undo(recipient);
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - this.startTime >= this.runtime) {
            this.undo(this.recipient);
        }
    }

}
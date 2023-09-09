package me.mrsoulpenguin.cosdt.challenge.event;

import me.mrsoulpenguin.cosdt.Cosdt;
import me.mrsoulpenguin.cosdt.Registries;
import me.mrsoulpenguin.cosdt.challenge.event.punishment.NoHandsEvent;
import me.mrsoulpenguin.cosdt.challenge.event.punishment.ButterFingersEvent;
import me.mrsoulpenguin.cosdt.challenge.event.reward.ItemGiveEvent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class EventType<T extends Event> {

    // Punishments
    public static final EventType<NoHandsEvent> NO_HANDS = register("no_hands", Builder.createWithSupplier(NoHandsEvent::new));
    public static final EventType<ButterFingersEvent> BUTTER_FINGERS = register("butter_fingers", Builder.createWithSupplier(ButterFingersEvent::new));

    // Rewards
    public static final EventType<ItemGiveEvent> ITEM_GIVE = register("item_give", Builder.createWithFactory(ItemGiveEvent::new));

    private final Supplier<T> supplier;
    private final EventFactory<T> factory;

    public EventType(Supplier<T> supplier, EventFactory<T> factory) {
        this.supplier = supplier;
        this.factory = factory;
    }

    private static <T extends Event> EventType<T> register(String id, Builder<T> type) {
        return Registry.register(Registries.EVENT_TYPE, new Identifier(Cosdt.NAMESPACE, id), type.build());
    }

    public T getFromSupplier() {
        return this.supplier.get();
    }

    public T createFromNbt(NbtCompound nbt) {
        return this.factory.create(nbt);
    }

    public static Optional<Event> getEventFromNbt(NbtCompound nbt) {
        return fromNbt(nbt).map(type -> {
            if (nbt.getSize() > 1) {
                return type.createFromNbt(nbt);
            } else {
                return type.getFromSupplier();
            }
        });
    }

    public static Optional<EventType<?>> fromNbt(NbtCompound nbt) {
        return Registries.EVENT_TYPE.getOrEmpty(new Identifier(nbt.getString("id")));
    }

    private record Builder<T extends Event>(Supplier<T> supplier, EventFactory<T> factory) {

        public static <T extends Event> Builder<T> createWithSupplier(Supplier<T> supplier) {
            return new Builder<>(supplier, null);
        }

        public static <T extends Event> Builder<T> createWithFactory(EventFactory<T> factory) {
            return new Builder<>(null, factory);
        }

        public EventType<T> build() {
            return new EventType<>(this.supplier, this.factory);
        }

    }

    public interface EventFactory<T extends Event> {
        T create(NbtCompound nbt);
    }

}

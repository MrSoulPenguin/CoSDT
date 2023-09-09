package me.mrsoulpenguin.cosdt;

import com.mojang.serialization.Lifecycle;
import me.mrsoulpenguin.cosdt.challenge.goal.GoalType;
import me.mrsoulpenguin.cosdt.event.EventType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

public class Registries {

    public static final RegistryKey<Registry<GoalType<?>>> GOAL_TYPE_KEY = createRegistryKey("goal_type");
    public static final RegistryKey<Registry<EventType<?>>> EVENT_TYPE_KEY = createRegistryKey("event_type");
    public static final Registry<GoalType<?>> GOAL_TYPE = register(GOAL_TYPE_KEY);
    public static final Registry<EventType<?>> EVENT_TYPE = register(EVENT_TYPE_KEY);

    private static <T> SimpleRegistry<T> register(RegistryKey<? extends Registry<T>> key) {
        return FabricRegistryBuilder.from(new SimpleRegistry<T>(key, Lifecycle.stable(), null)).buildAndRegister();
    }

    private static <T> RegistryKey<Registry<T>> createRegistryKey(String registryId) {
        return RegistryKey.ofRegistry(new Identifier(Cosdt.NAMESPACE, registryId));
    }

}

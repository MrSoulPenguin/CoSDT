package me.mrsoulpenguin.cosdt.command;

import com.mojang.brigadier.CommandDispatcher;
import me.mrsoulpenguin.cosdt.challenge.event.EventType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class EventCommand implements RegisterableCommand {

    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("event")
                        .then(literal("trigger")
                                .then(argument("recipient", EntityArgumentType.player())
                                        .then(argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                .executes(context -> this.executeTrigger(
                                                        EntityArgumentType.getPlayer(context, "recipient"),
                                                        NbtCompoundArgumentType.getNbtCompound(context, "nbt")
                                                ))
                                        )
                                )
                        )
                        .then(literal("stop")
                                .then(argument("recipient", EntityArgumentType.player())
                                        .then(argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                .executes(context -> this.executeStop(
                                                        EntityArgumentType.getPlayer(context, "recipient"),
                                                        NbtCompoundArgumentType.getNbtCompound(context, "nbt")
                                                ))
                                        )
                                )
                        )
        );
    }

    private int executeTrigger(PlayerEntity recipient, NbtCompound nbt) {
        EventType.getEventFromNbt(nbt).ifPresent(event -> event.execute(recipient));
        return 1;
    }

    private int executeStop(PlayerEntity recipient, NbtCompound nbt) {
        EventType.getEventFromNbt(nbt).ifPresent(event -> event.undo(recipient));
        return 1;
    }
}

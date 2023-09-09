package me.mrsoulpenguin.cosdt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.mrsoulpenguin.cosdt.challenge.Challenge;
import me.mrsoulpenguin.cosdt.challenge.ChallengeFactory;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ChallengeCommand {

    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("challenge")
                        .then(literal("give")
                                .then(argument("participant", EntityArgumentType.player())
                                        .then(argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                .executes(context -> execute(context, EntityArgumentType.getPlayer(context, "participant"), NbtCompoundArgumentType.getNbtCompound(context, "nbt")))
                                        )
                                )
                        )
        );
    }

    private int execute(CommandContext<ServerCommandSource> context, PlayerEntity participant, NbtCompound nbtCompound) {
        // Challenge NBT example:
        // {challenge:{timed:1b,time_limit:6000,goal:{id:"cosdt:answer_question",question:"What is 2 + 2?",answer:"4"},punishment:{id:"cosdt:no_hands"},reward:{id:"cosdt:item_give",item:{id:"minecraft:diamond",Count:1}}}}

        Challenge challenge = ChallengeFactory.fromNbt(nbtCompound.getCompound("challenge"));
        challenge.setParticipant(participant);
        challenge.start();
        return 1;
    }

}

package me.mrsoulpenguin.cosdt.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface RegisterableCommand {

    void register(CommandDispatcher<ServerCommandSource> dispatcher);

}

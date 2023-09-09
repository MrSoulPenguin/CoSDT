package me.mrsoulpenguin.cosdt;

import me.mrsoulpenguin.cosdt.command.ChallengeCommand;
import me.mrsoulpenguin.cosdt.command.EventCommand;
import me.mrsoulpenguin.cosdt.listener.MessageSendListener;
import me.mrsoulpenguin.cosdt.listener.PickupItemListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Cosdt implements ModInitializer {

    public static String NAMESPACE = "cosdt";

    @Override
    public void onInitialize() {
        Logger logger = LoggerFactory.getLogger("CoSDT");

        logger.info("Registering Listeners...");
        this.registerListeners();
        logger.info("Registering Commands...");
        this.registerCommands();
    }

    private void registerListeners() {
        new PickupItemListener();
        new MessageSendListener();
    }

    private void registerCommands() {
        List.of(
                new ChallengeCommand(),
                new EventCommand()
        ).forEach(command -> CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            command.register(dispatcher);
        }));
    }

}

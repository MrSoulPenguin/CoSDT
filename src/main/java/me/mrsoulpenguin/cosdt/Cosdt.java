package me.mrsoulpenguin.cosdt;

import me.mrsoulpenguin.cosdt.listener.MessageSendListener;
import me.mrsoulpenguin.cosdt.listener.PickupItemListener;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cosdt implements ModInitializer {

    @Override
    public void onInitialize() {
        Logger logger = LoggerFactory.getLogger("CoSDT");

        logger.info("Registering Listeners...");
        this.registerListeners();
    }

    private void registerListeners() {
        new PickupItemListener();
        new MessageSendListener();
    }

}

package dev.zskn.avl;

import dev.zskn.avl.config.AVLConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedVanillaLogistics implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("AVL");
    public static AVLConfig config;

    @Override
    public void onInitializeClient() {
        config = AVLConfig.loadConfig();
        LOGGER.info("Advanced Vanilla Logistics loaded successfully!");
    }
}

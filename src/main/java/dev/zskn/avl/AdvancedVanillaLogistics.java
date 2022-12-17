package dev.zskn.avl;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedVanillaLogistics implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("AVL");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Advanced Vanilla Logistics loaded successfully!");
	}
}

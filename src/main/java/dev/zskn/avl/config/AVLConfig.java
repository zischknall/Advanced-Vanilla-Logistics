package dev.zskn.avl.config;

import me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.Yaml;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class AVLConfig  {
    public boolean tweakBreakBlockToggle = false;
    public boolean tweakItemPickupToggle = false;

    public BlackList tweakBreakBlockBlacklist = new BlackList();
    public BlackList tweakItemPickupBlacklist = new BlackList();

    public static AVLConfig loadConfig() {
        Yaml yaml = new Yaml();
        try {
            FileReader reader = new FileReader(FabricLoader.getInstance().getConfigDir().resolve("avl.yaml").toFile());
            return (AVLConfig) yaml.load(reader);
        } catch (FileNotFoundException e) {
            return new AVLConfig();
        }
    }
}

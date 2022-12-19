package dev.zskn.avl.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.zskn.avl.AdvancedVanillaLogistics;
import me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.Yaml;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class ModMenuConfig implements ModMenuApi {
    Block blockSelection = Blocks.AIR;
    Item itemSelection = Items.AIR;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("title.avl.config"));
            ConfigCategory blockBreakingTweaks = builder.getOrCreateCategory(Text.translatable("category.avl.blockbreaking"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            blockBreakingTweaks.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.avl.blockbreaking.toggle"), AdvancedVanillaLogistics.config.tweakBreakBlockToggle).setDefaultValue(false).setTooltip(Text.translatable("option.avl.blockbreking.toggle.tooltip")).setSaveConsumer(newValue -> AdvancedVanillaLogistics.config.tweakBreakBlockToggle = newValue).build());

            DropdownBoxEntry<Block> blockDropdown = entryBuilder.startDropdownMenu(Text.translatable("option.avl.blockbreaking.dropdown"), DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(blockSelection), DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject()).setSelections(Registries.BLOCK.stream().collect(Collectors.toSet())).setSaveConsumer(block -> {
                if (block != Blocks.AIR) {
                    blockSelection = block;
                }
            }).build();
            blockBreakingTweaks.addEntry(blockDropdown);

            StringListListEntry blacklistEntry = entryBuilder.startStrList(Text.translatable("option.avl.blockbreaking.blacklist"), AdvancedVanillaLogistics.config.tweakBreakBlockBlacklist).setSaveConsumer(newValue -> {
                String blockSelectionID = Registries.BLOCK.getId(blockSelection).toString();
                if (blockSelection != Blocks.AIR && !newValue.contains(blockSelectionID)) {
                    newValue.add(blockSelectionID);
                    blockSelection = Blocks.AIR;
                }
                AdvancedVanillaLogistics.config.tweakBreakBlockBlacklist = new BlackList(newValue);
            }).build();
            blacklistEntry.setEditable(false);
            blockBreakingTweaks.addEntry(blacklistEntry);

            ConfigCategory itemPickupTweaks = builder.getOrCreateCategory(Text.translatable("category.avl.itempickup"));
            itemPickupTweaks.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.avl.itempickup.toggle"), AdvancedVanillaLogistics.config.tweakItemPickupToggle).setSaveConsumer(newValue -> AdvancedVanillaLogistics.config.tweakItemPickupToggle = newValue).build());

            DropdownBoxEntry<Item> itemDropdown = entryBuilder.startDropdownMenu(Text.translatable("option.avl.itempickup.dropdown"), DropdownMenuBuilder.TopCellElementBuilder.ofItemObject(itemSelection), DropdownMenuBuilder.CellCreatorBuilder.ofItemObject()).setSelections(Registries.ITEM.stream().collect(Collectors.toSet())).setSaveConsumer(item -> {
                if (item != Items.AIR) {
                    itemSelection = item;
                }
            }).build();
            itemPickupTweaks.addEntry(itemDropdown);

            StringListListEntry itemBlacklistEntry = entryBuilder.startStrList(Text.translatable("option.avl.itempickup.blacklist"), AdvancedVanillaLogistics.config.tweakItemPickupBlacklist).setSaveConsumer(newValue -> {
                String itemSelectionID = Registries.ITEM.getId(itemSelection).toString();
                if (itemSelection != Items.AIR && !newValue.contains(itemSelectionID)) {
                    newValue.add(itemSelectionID);
                    itemSelection = Items.AIR;
                }
                AdvancedVanillaLogistics.config.tweakItemPickupBlacklist = new BlackList(newValue);
            }).build();
            blacklistEntry.setEditable(false);
            itemPickupTweaks.addEntry(itemBlacklistEntry);

            builder.setSavingRunnable(this::saveConfig);

            return builder.build();
        };
    }

    private void saveConfig() {
        Yaml yaml = new Yaml();
        try {
            FileWriter writer = new FileWriter(FabricLoader.getInstance().getConfigDir().resolve("avl.yaml").toFile());
            yaml.dump(AdvancedVanillaLogistics.config, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

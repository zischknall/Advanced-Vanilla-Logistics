package dev.zskn.avl.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.stream.Collectors;

public class AVLConfig implements ModMenuApi {
    public static boolean tweakBreakBlockToggle = false;

    public static BlockBlackList tweakBreakBlockBlacklist = new BlockBlackList();

    Block blockSelection = Blocks.AIR;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("title.avl.config"));
            ConfigCategory blockBreakingTweaks = builder.getOrCreateCategory(Text.translatable("category.avl.blockbreaking"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            blockBreakingTweaks.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.avl.blockbreaking.toggle"), tweakBreakBlockToggle).setDefaultValue(false).setTooltip(Text.translatable("Toggles break block blacklisting")).setSaveConsumer(newValue -> tweakBreakBlockToggle = newValue).build());

            DropdownBoxEntry<Block> blockDropdown = entryBuilder.startDropdownMenu(Text.translatable("option.avl.blockbreaking.dropdown"), DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(blockSelection), DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject()).setSelections(Registries.BLOCK.stream().collect(Collectors.toSet())).setSaveConsumer(block -> {
                if (block != Blocks.AIR) {
                    blockSelection = block;
                }
            }).build();
            blockBreakingTweaks.addEntry(blockDropdown);

            StringListListEntry blacklistEntry = entryBuilder.startStrList(Text.translatable("option.avl.blockbreaking.blacklist"), tweakBreakBlockBlacklist).setSaveConsumer(newValue -> {
                String blockSelectionID = Registries.BLOCK.getId(blockSelection).toString();
                if (blockSelection != Blocks.AIR && !newValue.contains(blockSelectionID)) {
                    newValue.add(blockSelectionID);
                    blockSelection = Blocks.AIR;
                }
                tweakBreakBlockBlacklist = new BlockBlackList(newValue);
            }).build();
            blacklistEntry.setEditable(false);
            blockBreakingTweaks.addEntry(blacklistEntry);

            return builder.build();
        };
    }
}

package dev.zskn.avl;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class AVLConfig implements ModMenuApi {
    public static boolean tweakBreakBlockToggle = false;

    public static Block blacklisted = Blocks.DIRT;
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.of("title.avl.config"));
            ConfigCategory blockBreakingTweaks = builder.getOrCreateCategory(Text.of("category.avl.blockbreaking"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            blockBreakingTweaks.addEntry(entryBuilder.startBooleanToggle(Text.of("option.avl.blockbreaking.toggle"), tweakBreakBlockToggle)
                    .setDefaultValue(false) // Recommended: Used when user click "Reset"
                    .setTooltip(Text.of("Toggles break block blacklisting")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> tweakBreakBlockToggle = newValue) // Recommended: Called when user save the config
                    .build()); // Builds the option entry for cloth config

            blockBreakingTweaks.addEntry(entryBuilder.startDropdownMenu(Text.of("option.avl.blockbreaking.blacklist"),
                    DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(blacklisted),
                    DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject())
                    .setSelections(Registries.BLOCK)
                    .setSaveConsumer(block -> blacklisted = block)
                    .build());

            return builder.build();
        };
    }
}

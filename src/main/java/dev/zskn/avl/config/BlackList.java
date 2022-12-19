package dev.zskn.avl.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class BlackList extends LinkedList<String> {

    public BlackList(List<String> list) {
        super(list);
    }

    public BlackList() {
        super();
    }

    public boolean contains(Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return super.contains(identifier.toString()) || super.contains(identifier.getPath());
    }

    public boolean contains(Item item) {
        Identifier identifier = Registries.ITEM.getId(item);
        return super.contains(identifier.toString()) || super.contains(identifier.getPath());
    }
}

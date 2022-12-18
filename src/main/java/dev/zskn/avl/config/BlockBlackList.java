package dev.zskn.avl.config;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class BlockBlackList extends LinkedList<String> {

    public BlockBlackList(List<String> list) {
        super(list);
    }

    public BlockBlackList() {
        super();
    }

    public boolean contains(Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return super.contains(identifier.toString()) || super.contains(identifier.getPath());
    }
}

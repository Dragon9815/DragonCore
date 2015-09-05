package net.dragon9815.dragoncore.creativetab;

import net.dragon9815.dragoncore.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CreativeTabsDragonCore {
    public static final CreativeTabs tabDragonCore = new CreativeTabs(Reference.MOD_NAME) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.dragon_egg);
        }
    };
}

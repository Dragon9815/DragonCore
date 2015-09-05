package net.dragon9815.dragoncore.item.itemblock;

import net.dragon9815.dragoncore.block.BlockUpgradeable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemBlockUpgradeable extends ItemBlockBase {
    public ItemBlockUpgradeable(Block block) {
        super(block);
    }

    protected abstract String getMachineName();

    @Override
    protected void addExtraInfo(ItemStack stack, EntityPlayer player, List tooltips, boolean par4) {
        ItemStack[] upgradeStacks = ((BlockUpgradeable)this.field_150939_a).getValidUpgrades();

        /*if(upgradeStacks != null && upgradeStacks.length > 0)
        {
            tooltips.add("");
            tooltips.add(("" + StringHelper.localize("string.validUpgrades")).trim() + ":");

            for (ItemStack upgradeStack : upgradeStacks)
            {
                if(upgradeStack != null)
                {
                    tooltips.add(" - " + String.valueOf(upgradeStack.stackSize) + " x " + ((ItemUpgrade)upgradeStack.getItem()).getLocalizedName());
                }
            }
        }*/
    }
}

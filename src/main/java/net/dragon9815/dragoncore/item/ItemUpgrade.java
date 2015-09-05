package net.dragon9815.dragoncore.item;

import net.dragon9815.dragoncore.helpers.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class ItemUpgrade extends ItemBase {
    public ItemUpgrade() {
        super();
    }

    public ItemUpgrade[] getRequirements() {
        return new ItemUpgrade[0];
    }

    public abstract String getUnlocalizedUpgradeName();

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    @Override
    public void addExtraInfo(ItemStack stack, EntityPlayer player, List tooltips, boolean par4) {
        ItemUpgrade[] reqs = this.getRequirements();

        if (reqs != null && reqs.length > 0) {
            tooltips.add("");
            tooltips.add(("" + StatCollector.translateToLocal("string.requirements")).trim() + ":");

            for (ItemUpgrade req : reqs) {
                tooltips.add(" - " + StringHelper.localize(req.getUnlocalizedUpgradeName()));
            }
        }
    }
}

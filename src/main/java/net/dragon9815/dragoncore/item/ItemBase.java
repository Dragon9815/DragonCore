package net.dragon9815.dragoncore.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.dragon9815.dragoncore.creativetab.CreativeTabsDragonCore;
import net.dragon9815.dragoncore.helpers.StringHelper;
import net.dragon9815.dragoncore.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class ItemBase extends Item {
    public ItemBase() {
        super();

        this.setCreativeTab(CreativeTabsDragonCore.tabDragonCore);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(this.getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean par4) {
        super.addInformation(stack, player, tooltips, par4);

        if (player.isSneaking()) {
            String[] description;

            description = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".description")).trim().split(";");

            for (String line : description) {
                tooltips.add(StringHelper.GREEN + line);
            }

            this.addExtraInfo(stack, player, tooltips, par4);
        } else {
            tooltips.add(("" + StringHelper.BRIGHT_BLUE + StatCollector.translateToLocal("string.moreInformation")).trim());
        }
    }

    protected void addExtraInfo(ItemStack stack, EntityPlayer player, List tooltips, boolean par4) {
    }
}

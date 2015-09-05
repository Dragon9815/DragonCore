package net.dragon9815.dragoncore.block;

import net.dragon9815.dragoncore.helpers.LogHelper;
import net.dragon9815.dragoncore.helpers.StringHelper;
import net.dragon9815.dragoncore.inventory.InventoryUpgradeable;
import net.dragon9815.dragoncore.item.ItemUpgrade;
import net.dragon9815.dragoncore.tileentity.TileEntityUpgradeable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public abstract class BlockUpgradeable extends BlockTileEntityBase {
    public BlockUpgradeable() {
        super();
    }

    public BlockUpgradeable(Material material) {
        super(material);
    }

    public abstract int getNumUpgradeSlots();

    public abstract ItemStack[] getValidUpgrades();

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntityUpgradeable te = (TileEntityUpgradeable) world.getTileEntity(x, y, z);

            if (player.getHeldItem() != null) {
                if (player.getHeldItem().getItem() instanceof ItemUpgrade) {
                    ItemUpgrade item = (ItemUpgrade) player.getHeldItem().getItem();

                    if (this.doUpgrade(item, te, player)) {
                        player.getHeldItem().stackSize--;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityUpgradeable te = (TileEntityUpgradeable) world.getTileEntity(x, y, z);

        te.dropAllUpgrades(world, x, y, z);

        super.breakBlock(world, x, y, z, block, meta);
    }

    public boolean doUpgrade(ItemUpgrade upgrade, TileEntityUpgradeable tileEntity, EntityPlayer player) {
        if (tileEntity.isUpgradeValid(upgrade)) {
            tileEntity.addUpgrade(upgrade, 1);
            player.addChatMessage(new ChatComponentText(StringHelper.GREEN + String.format(StringHelper.localize("message.upgrade.installed"), upgrade.getLocalizedName())));
            return true;
        } else {
            InventoryUpgradeable.UpgradeFault fault;
            fault = ((InventoryUpgradeable) tileEntity.getUpgradeInventory()).getLastUpgradeFault();

            player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + String.format(StringHelper.localize("message.upgrade.fault"), upgrade.getLocalizedName())));

            switch (fault) {
            case Unknown:
                player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + StringHelper.localize("message.upgrade.fault.unknown")));
                break;
            case Invalid:
                player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + StringHelper.localize("message.upgrade.fault.invalid")));
                break;
            case AlreadyInstalled:
                player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + StringHelper.localize("message.upgrade.fault.alreadyInstalled")));
                break;
            case MissingReq:
                player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + StringHelper.localize("message.upgrade.fault.missingReq")));

                List<ItemUpgrade> missig = ((InventoryUpgradeable) tileEntity.getUpgradeInventory()).listMissingUpgrades(upgrade);
                Iterator<ItemUpgrade> it1 = missig.iterator();

                while (it1.hasNext()) {
                    ItemUpgrade var1 = it1.next();

                    LogHelper.info(String.valueOf(var1));

                    player.addChatComponentMessage(new ChatComponentText(StringHelper.RED + " - " + StringHelper.localize(var1.getUnlocalizedUpgradeName())));
                }

                break;
            }
        }


        return false;
    }
}
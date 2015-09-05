package net.dragon9815.dragoncore.tileentity;

import net.dragon9815.dragoncore.block.BlockUpgradeable;
import net.dragon9815.dragoncore.helpers.LogHelper;
import net.dragon9815.dragoncore.inventory.InventoryUpgradeable;
import net.dragon9815.dragoncore.item.ItemUpgrade;
import net.dragon9815.dragoncore.registry.UpgradeRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;

import java.util.Map;

public abstract class TileEntityUpgradeable extends TileEntityBase {

    public InventoryUpgradeable upgradeInventory;

    public TileEntityUpgradeable() {
        super();

        if (!(this.blockType instanceof BlockUpgradeable)) {
            LogHelper.fatal("The TileEntity can not find it's Block.");
        }

        this.upgradeInventory = new InventoryUpgradeable(((BlockUpgradeable) this.getBlockType()).getNumUpgradeSlots(), ((BlockUpgradeable) this.getBlockType()).getValidUpgrades());
    }

    protected ItemStack[] getItemStackArray(Map<String, Integer> upgrades) {
        ItemStack[] itemStacks = new ItemStack[upgrades.size()];
        String[] keySet = (String[]) upgrades.keySet().toArray();

        for (int i = 0; i < itemStacks.length; i++) {
            itemStacks[i] = new ItemStack((ItemUpgrade) UpgradeRegistry.instance().getUpgrade(keySet[i]), upgrades.get(keySet[i]));
        }

        return itemStacks;
    }

    public InventoryUpgradeable getUpgradeInventory() {
        return this.upgradeInventory;
    }

    public boolean isUpgradeValid(ItemUpgrade upgrade) {
        return this.upgradeInventory.isUpgradeValid(upgrade);
    }

    public void addUpgrade(ItemUpgrade upgrade, int num) {
        upgradeInventory.addUpgrade(upgrade, num);

        onUpgrade(upgrade);
    }

    public abstract void onUpgrade(ItemUpgrade upgrade);

    public void dropAllUpgrades(World world, int x, int y, int z) {
        for (int i = 0; i < this.upgradeInventory.getSizeInventory(); i++) {
            ItemStack itemstack = this.upgradeInventory.getStackInSlot(i);

            if (itemstack != null) {
                float f = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0) {
                    int j = this.worldObj.rand.nextInt(21) + 10;

                    if (j > itemstack.stackSize) {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityItem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound()) {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;

                    entityItem.motionX = (double) ((float) this.worldObj.rand.nextGaussian() * f3);
                    entityItem.motionY = (double) ((float) this.worldObj.rand.nextGaussian() * f3 + 0.2F);
                    entityItem.motionZ = (double) ((float) this.worldObj.rand.nextGaussian() * f3);

                    world.spawnEntityInWorld(entityItem);
                }
            }

        }
    }

    public boolean hasUpgrade(ItemUpgrade upgrade) {
        return this.upgradeInventory.hasUpgrade(upgrade);
    }

    public boolean hasUpgrade(String upgradeID) {
        return this.upgradeInventory.hasUpgrade(UpgradeRegistry.instance().getUpgrade(upgradeID));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.upgradeInventory.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        this.upgradeInventory.writeToNBT(tag);
    }

    public void writeToSyncNBT(NBTTagCompound tag) {
        this.upgradeInventory.writeToNBT(tag);
    }

    public void readFromSyncNBT(NBTTagCompound tag) {
        this.upgradeInventory.readFromNBT(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromSyncNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();

        writeToSyncNBT(tag);

        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }
}

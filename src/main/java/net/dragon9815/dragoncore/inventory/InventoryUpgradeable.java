package net.dragon9815.dragoncore.inventory;

import net.dragon9815.dragoncore.item.ItemUpgrade;
import net.dragon9815.dragoncore.registry.UpgradeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class InventoryUpgradeable implements IInventory {
    protected ItemStack[] upgradeStacks;
    protected ItemStack[] validUpgrades;

    protected int inventorySize;
    UpgradeFault lastFault;

    public InventoryUpgradeable(int size, ItemStack[] validUpgrades) {
        this.upgradeStacks = new ItemStack[size];
        this.validUpgrades = new ItemStack[size];

        int i = 0;
        for (ItemStack upgrade : validUpgrades) {
            this.validUpgrades[i] = upgrade;
            i++;
        }

        this.inventorySize = size;
    }

    public ItemStack[] getInstalledUpgrades() {
        return this.upgradeStacks.clone();
    }

    public int getNumInstalled(ItemUpgrade upgrade) {
        for (ItemStack stack : this.upgradeStacks) {
            if (stack != null && stack.getItem().equals(upgrade)) {
                return stack.stackSize;
            }
        }

        return 0;
    }

    public int getNumInstalled(String upgradeName) {
        ItemUpgrade upgrade = UpgradeRegistry.instance().getUpgrade(upgradeName);

        for (ItemStack stack : this.upgradeStacks) {
            if (stack != null && stack.getItem().equals(upgrade)) {
                return stack.stackSize;
            }
        }

        return 0;
    }

    public boolean isUpgradeValid(ItemUpgrade upgrade) {
        int validNum = 0, installedNum = 0;
        boolean foundValid = false, foundInstalled = false;
        boolean foundPrereq = false;

        if (upgrade == null) {
            this.lastFault = UpgradeFault.Unknown;
            return false;
        }

        for (int i = 0; i < this.inventorySize; i++) {
            if (!foundValid && this.validUpgrades[i] != null && this.validUpgrades[i].getItem().equals(upgrade)) {
                validNum = this.validUpgrades[i].stackSize;
                foundValid = true;
            }

            if (!foundInstalled && this.upgradeStacks[i] != null && this.upgradeStacks[i].getItem().equals(upgrade)) {
                installedNum = this.upgradeStacks[i].stackSize;
                foundInstalled = true;
            }
        }

        if (!foundValid) {
            this.lastFault = UpgradeFault.Invalid;
            return false;
        }

        ItemUpgrade[] Prereq = upgrade.getRequirements();
        if (Prereq != null) {
            for (ItemUpgrade upgrade1 : Prereq) {
                foundPrereq = false;

                for (int i = 0; i < this.inventorySize; i++) {
                    ItemStack stack = this.upgradeStacks[i];

                    if (stack != null && stack.getItem().equals(upgrade1)) {
                        foundPrereq = true;
                    }
                }

                if (!foundPrereq) {
                    this.lastFault = UpgradeFault.MissingReq;
                    return false;
                }
            }
        }

        if (installedNum >= validNum) {
            this.lastFault = UpgradeFault.AlreadyInstalled;
        }

        return (installedNum < validNum);
    }

    public UpgradeFault getLastUpgradeFault() {
        return this.lastFault;
    }

    public List<ItemUpgrade> listMissingUpgrades(ItemUpgrade upgrade) {
        boolean foundPrereq;
        List<ItemUpgrade> ret = new ArrayList<ItemUpgrade>();

        ItemUpgrade[] Prereq = upgrade.getRequirements();
        if (Prereq != null) {
            for (ItemUpgrade upgrade1 : Prereq) {

                foundPrereq = false;

                for (int i = 0; i < this.inventorySize; i++) {
                    ItemStack stack = this.upgradeStacks[i];

                    if (stack != null && stack.getItem().equals(upgrade1)) {
                        foundPrereq = true;
                    }
                }

                if (!foundPrereq) {
                    ret.add(upgrade1);
                }
            }
        }

        return ret;
    }

    public void addUpgrade(ItemUpgrade upgrade, int num) {
        boolean found = false;

        for (int i = 0; i < this.inventorySize; i++) {
            if (this.upgradeStacks[i] != null) {
                if (this.upgradeStacks[i].getItem().equals(upgrade)) {
                    found = true;
                    this.upgradeStacks[i].stackSize += num;
                    break;
                }
            }
        }

        if (!found) {
            for (int i = 0; i < this.inventorySize; i++) {
                if (this.upgradeStacks[i] == null) {
                    this.upgradeStacks[i] = new ItemStack(upgrade, num);
                    break;
                }
            }
        }
    }

    public boolean removeUpgrade(ItemUpgrade upgrade, int num) {
        for (int i = 0; i < this.inventorySize; i++) {
            if (this.upgradeStacks[i] != null) {
                if (this.upgradeStacks[i].getItem().equals(upgrade)) {
                    this.upgradeStacks[i].stackSize -= num;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasUpgrade(ItemUpgrade upgrade) {
        for (int i = 0; i < this.inventorySize; i++) {
            if (this.upgradeStacks[i] != null) {
                if (this.upgradeStacks[i].getItem().equals(upgrade)) {
                    return true;
                }
            }
        }

        return false;
    }

    public ItemStack[] getUpgradeStacks() {
        return this.upgradeStacks.clone();
    }

    @Override
    public int getSizeInventory() {
        return this.inventorySize;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.upgradeStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        ItemStack[] itemStacks = new ItemStack[this.upgradeStacks.length];

        for (int i = 0; i < itemStacks.length; i++) {
            itemStacks[i] = upgradeStacks[i];
        }

        if (itemStacks[slot] != null) {
            ItemStack itemStack;

            if (itemStacks[slot].stackSize <= num) {
                itemStack = itemStacks[slot];
                itemStacks[slot] = null;
                return itemStack;
            } else {
                itemStack = itemStacks[slot].splitStack(num);

                if (itemStacks[slot].stackSize == 0) {
                    itemStacks[slot] = null;
                }

                return itemStack;
            }
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null; // null
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        boolean found = false;

        if (itemStack != null && itemStack.getItem() instanceof ItemUpgrade) {
            if (this.upgradeStacks[slot] != null && this.upgradeStacks[slot].getItem().equals(itemStack.getItem())) // is already right upgrade in slot, add to slot
            {
                while (this.isUpgradeValid((ItemUpgrade) itemStack.getItem())) {
                    this.addUpgrade((ItemUpgrade) itemStack.getItem(), 1);
                    itemStack.stackSize--;
                }
            } else if (this.upgradeStacks[slot] == null) {
                for (int i = 0; i < this.inventorySize; i++) {
                    if (this.upgradeStacks[i] != null && this.upgradeStacks[i].getItem().equals(itemStack.getItem())) // is put into an empty slot try to shift all to left
                    {
                        while (this.isUpgradeValid((ItemUpgrade) itemStack.getItem())) {
                            this.addUpgrade((ItemUpgrade) itemStack.getItem(), 1);
                            itemStack.stackSize--;
                        }

                        return;
                    }
                }
            }
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemUpgrade && isUpgradeValid((ItemUpgrade) itemStack.getItem());
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.upgradeStacks = new ItemStack[this.inventorySize];
        if (tag.hasKey("Upgrades")) {
            NBTTagList tagList = tag.getTagList("Upgrades", 10);

            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag1 = tagList.getCompoundTagAt(i);

                ItemStack stack = ItemStack.loadItemStackFromNBT(tag1);

                //LogHelper.info("UpgradeName: " + stack.getDisplayName());

                this.upgradeStacks[i] = stack;
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < this.inventorySize; i++) {
            ItemStack stack = this.upgradeStacks[i];

            NBTTagCompound tag2 = new NBTTagCompound();

            if (stack != null) {
                stack.writeToNBT(tag2);
            }

            tagList.appendTag(tag2);
        }

        tag.setTag("Upgrades", tagList);
    }

    public enum UpgradeFault {
        Unknown, MissingReq, AlreadyInstalled, Invalid
    }
}

package net.dragon9815.dragoncore.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.UUID;

public class PlayerHelper {
    public static EntityPlayer getPlayer(UUID playerUUID) {
        for (EntityPlayer player : (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            if (player.getUniqueID().equals(playerUUID)) {
                return player;
            }
        }

        return null;
    }

    public static ItemStack tryInsertItemStackIntoPlayerInventory(EntityPlayer player, ItemStack stack, boolean preferArmorInventory) {
        if (stack == null) {
            return null;
        }

        if (player == null) {
            return null;
        }

        ItemStack retStack = stack.copy();

        // Prefer Armor-Inventory
        if (preferArmorInventory) {
            final int armorSlotIndex = player.inventory.mainInventory.length - 1; // Slots for Armor begin after main Inventory
            if (stack.getItem() instanceof ItemArmor) {
                for (int i = 1; i <= player.inventory.armorInventory.length; i++) {
                    ItemStack itemStack = player.inventory.getStackInSlot(i + armorSlotIndex);

                    if (itemStack == null) {
                        if (player.inventory.armorInventory.length - i == ((ItemArmor) retStack.getItem()).armorType) {
                            player.inventory.setInventorySlotContents(i + armorSlotIndex, new ItemStack(retStack.getItem()));
                            retStack.stackSize--;
                            return retStack;
                        }
                    }
                }
            }
        }

        // No Armor, using standard function
        player.inventory.addItemStackToInventory(retStack);

        if (retStack.stackSize <= 0) {
            retStack = null;
        }

        player.inventoryContainer.detectAndSendChanges();

        return retStack;
    }
}

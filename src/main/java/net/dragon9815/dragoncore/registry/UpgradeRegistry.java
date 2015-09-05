package net.dragon9815.dragoncore.registry;

import net.dragon9815.dragoncore.block.BlockUpgradeable;
import net.dragon9815.dragoncore.helpers.LogHelper;
import net.dragon9815.dragoncore.item.ItemUpgrade;
import net.minecraft.item.ItemStack;

import java.util.*;

public class UpgradeRegistry {
    private static UpgradeRegistry INSTANCE = null;
    private List<ItemUpgrade> registeredUpgrades;

    public UpgradeRegistry() {
        registeredUpgrades = new LinkedList<ItemUpgrade>();
    }

    public static UpgradeRegistry instance() {
        if (INSTANCE == null) {
            INSTANCE = new UpgradeRegistry();
        }

        return INSTANCE;
    }

    public void registerUpgrade(ItemUpgrade upgrade) {
        if (!isRegistered(upgrade.getUnlocalizedUpgradeName())) {
            registeredUpgrades.add(upgrade);
        } else {
            LogHelper.warn("Upgrade already registered! " + upgrade.getUnlocalizedUpgradeName());
        }
    }

    public boolean isRegistered(String upgradeID) {
        return getUpgrade(upgradeID) != null;
    }

    public ItemUpgrade getUpgrade(String upgradeID) {
        Iterator<ItemUpgrade> it = registeredUpgrades.iterator();

        while (it.hasNext()) {
            ItemUpgrade upgrade = it.next();

            if (upgrade.getUnlocalizedUpgradeName().equals(upgradeID)) {
                return upgrade;
            }
        }

        return null;
    }
}

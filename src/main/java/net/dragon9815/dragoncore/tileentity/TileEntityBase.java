package net.dragon9815.dragoncore.tileentity;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {

    public abstract void updateEntityServer();

    public abstract void updateEntityClient();

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {
            this.updateEntityServer();
        } else {
            this.updateEntityClient();
        }
    }

}

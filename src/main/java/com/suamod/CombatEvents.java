package com.suamod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CombatEvents {
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.entityPlayer == null) return;
        if (event.entityPlayer.worldObj.isRemote) return;
        Entity target = event.target;
        if (target instanceof EntityPlayerMP) {
            LastHitRegistry.setLastHit((EntityPlayerMP) event.entityPlayer, (EntityPlayerMP) target);
        }
    }
}
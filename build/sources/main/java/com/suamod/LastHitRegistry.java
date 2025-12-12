package com.suamod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LastHitRegistry {
    private static final Map<UUID, UUID> LAST_HIT = new HashMap<>();

    public static synchronized void setLastHit(EntityPlayerMP attacker, EntityPlayerMP target) {
        LAST_HIT.put(attacker.getUniqueID(), target.getUniqueID());
    }

    public static synchronized EntityPlayerMP getLastHitTarget(WorldServer world, EntityPlayerMP attacker) {
        UUID targetId = LAST_HIT.get(attacker.getUniqueID());
        if (targetId == null) return null;
        EntityPlayer p = world.getPlayerEntityByUUID(targetId);
        if (!(p instanceof EntityPlayerMP)) return null;
        return (EntityPlayerMP) p;
    }
}


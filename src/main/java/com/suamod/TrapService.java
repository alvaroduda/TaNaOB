package com.suamod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class TrapService {
    public static void runTrap(EntityPlayerMP executor) {
        WorldServer world = (WorldServer) executor.worldObj;
        Resources res = findResources(executor);
        if (res == null) return;
        EntityPlayerMP target = findTarget(world, executor);
        if (target == null) return;
        BlockPos feet = new BlockPos(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ));
        BlockPos head = feet.up();
        surroundSides(world, feet, executor, res);
        placeTop(world, head.up(), executor, res);
        placeLava(world, head, executor, res);
        placeWaterAdjacent(world, head, executor, res);
    }

    private static class Resources {
        int blockSlot = -1;
        int lavaSlot = -1;
        int waterSlot = -1;
    }

    private static Resources findResources(EntityPlayerMP player) {
        Resources res = new Resources();
        int woodSlot = -1;
        int anySlot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack s = player.inventory.mainInventory[i];
            if (s == null) continue;
            Item it = s.getItem();
            if (it instanceof ItemBlock) {
                Block b = ((ItemBlock) it).getBlock();
                if (b == Blocks.cobblestone_wall) res.blockSlot = i;
                if (woodSlot == -1 && (b == Blocks.planks || b == Blocks.log || b == Blocks.log2)) woodSlot = i;
                if (anySlot == -1) anySlot = i;
            }
            if (res.lavaSlot == -1 && it == Items.lava_bucket) res.lavaSlot = i;
            if (res.waterSlot == -1 && it == Items.water_bucket) res.waterSlot = i;
        }
        if (res.blockSlot == -1) res.blockSlot = woodSlot != -1 ? woodSlot : anySlot;
        if (res.blockSlot == -1 || res.lavaSlot == -1 || res.waterSlot == -1) return null;
        return res;
    }

    private static EntityPlayerMP findTarget(World world, EntityPlayerMP player) {
        List<EntityPlayer> players = world.playerEntities;
        EntityPlayerMP best = null;
        double bestDist = Double.MAX_VALUE;
        for (EntityPlayer p : players) {
            if (!(p instanceof EntityPlayerMP)) continue;
            if (p == player) continue;
            double d = p.getDistanceToEntity(player);
            if (d < 5.0 && d < bestDist && player.canEntityBeSeen(p)) {
                best = (EntityPlayerMP) p;
                bestDist = d;
            }
        }
        return best;
    }

    private static boolean isReplaceable(World world, BlockPos pos) {
        Block b = world.getBlockState(pos).getBlock();
        if (b.isAir(world, pos)) return true;
        Material m = b.getMaterial();
        if (m.isReplaceable()) return true;
        if (b instanceof BlockLiquid) return true;
        return false;
    }

    private static boolean placeBlockFromHotbar(WorldServer world, BlockPos pos, EntityPlayerMP player, int slot) {
        if (slot < 0) return false;
        ItemStack s = player.inventory.mainInventory[slot];
        if (s == null) return false;
        if (!(s.getItem() instanceof ItemBlock)) return false;
        Block block = ((ItemBlock) s.getItem()).getBlock();
        if (!isReplaceable(world, pos)) return false;
        world.setBlockState(pos, block.getDefaultState(), 3);
        if (s.stackSize == 1) {
            player.inventory.mainInventory[slot] = null;
        } else {
            s.stackSize--;
        }
        player.inventory.markDirty();
        return true;
    }

    private static void surroundSides(WorldServer world, BlockPos feet, EntityPlayerMP player, Resources res) {
        placeBlockFromHotbar(world, feet.north(), player, res.blockSlot);
        placeBlockFromHotbar(world, feet.south(), player, res.blockSlot);
        placeBlockFromHotbar(world, feet.east(), player, res.blockSlot);
        placeBlockFromHotbar(world, feet.west(), player, res.blockSlot);
    }

    private static void placeTop(WorldServer world, BlockPos top, EntityPlayerMP player, Resources res) {
        placeBlockFromHotbar(world, top, player, res.blockSlot);
    }

    private static void placeLava(WorldServer world, BlockPos head, EntityPlayerMP player, Resources res) {
        if (!isReplaceable(world, head)) return;
        world.setBlockState(head, Blocks.lava.getDefaultState(), 3);
        consumeFilledBucket(player, res.lavaSlot);
    }

    private static void placeWaterAdjacent(WorldServer world, BlockPos head, EntityPlayerMP player, Resources res) {
        BlockPos[] sides = new BlockPos[]{head.north(), head.south(), head.east(), head.west()};
        for (BlockPos p : sides) {
            if (isReplaceable(world, p)) {
                world.setBlockState(p, Blocks.water.getDefaultState(), 3);
                consumeFilledBucket(player, res.waterSlot);
                return;
            }
        }
    }

    private static void consumeFilledBucket(EntityPlayerMP player, int slot) {
        if (slot < 0) return;
        ItemStack s = player.inventory.mainInventory[slot];
        if (s == null) return;
        if (s.stackSize == 1) {
            player.inventory.mainInventory[slot] = new ItemStack(Items.bucket);
        } else {
            s.stackSize--;
            ItemStack empty = new ItemStack(Items.bucket);
            boolean added = player.inventory.addItemStackToInventory(empty);
            if (!added) {
                player.dropPlayerItemWithRandomChoice(empty, false);
            }
        }
        player.inventory.markDirty();
    }
}

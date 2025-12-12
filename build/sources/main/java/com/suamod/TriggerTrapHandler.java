package com.suamod;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TriggerTrapHandler implements IMessageHandler<TriggerTrapMessage, IMessage> {
    @Override
    public IMessage onMessage(TriggerTrapMessage message, MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        IThreadListener thread = player.getServerForPlayer();
        thread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                TrapService.runTrap(player);
            }
        });
        return null;
    }
}

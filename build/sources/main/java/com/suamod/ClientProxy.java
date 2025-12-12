package com.suamod;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private static KeyBinding KEY_TRAP;

    @Override
    public void init() {
        KEY_TRAP = new KeyBinding("Ativar trap de obsidian", Keyboard.KEY_O, "Auto Obsidian Trap");
        ClientRegistry.registerKeyBinding(KEY_TRAP);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KEY_TRAP != null && KEY_TRAP.isPressed()) {
            AutoObsidianTrapMod.CHANNEL.sendToServer(new TriggerTrapMessage());
        }
    }
}

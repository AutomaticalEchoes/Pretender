package com.AutomaticalEchoes.Pretender.client;

import com.AutomaticalEchoes.Pretender.api.DisableControlKeyConflictContext;
import com.AutomaticalEchoes.Pretender.register.EffectsRegister;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void ClientTickEvent(TickEvent.PlayerTickEvent playerTickEvent){
        DisableInput(playerTickEvent);
    }


    public static void DisableInput(TickEvent.PlayerTickEvent playerTickEvent){
        if(playerTickEvent.player.hasEffect(EffectsRegister.ENTANGLEMENT.get())){
            if(!(Minecraft.getInstance().options.keyJump.getKeyConflictContext() instanceof DisableControlKeyConflictContext)) Minecraft.getInstance().options.keyJump.setKeyConflictContext(new DisableControlKeyConflictContext());
            return;
        }
        if(Minecraft.getInstance().options.keyJump.getKeyConflictContext() instanceof DisableControlKeyConflictContext){
            Minecraft.getInstance().options.keyJump.setKeyConflictContext(KeyConflictContext.IN_GAME);
        }
    }


}

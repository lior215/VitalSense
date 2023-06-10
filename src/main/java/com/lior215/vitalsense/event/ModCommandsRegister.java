package com.lior215.vitalsense.event;


import com.lior215.vitalsense.commands.EyeHealthSetCommand;
import com.lior215.vitalsense.vitalsense;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID)
public class ModCommandsRegister {


    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new EyeHealthSetCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }




}

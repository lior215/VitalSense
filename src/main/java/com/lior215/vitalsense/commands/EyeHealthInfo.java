package com.lior215.vitalsense.commands;

import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import com.lior215.vitalsense.client.ClientEyeHealth;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class EyeHealthInfo {
        public EyeHealthInfo(CommandDispatcher<CommandSourceStack> dispatcher) {
            dispatcher.register(Commands.literal("vitalsense").then(Commands.literal("eyehealth").then(Commands.literal("info")
                    .executes(commandContext -> eyeHealthInfo(commandContext.getSource())))));
        }
    private int eyeHealthInfo (CommandSourceStack source) {
        Player player = source.getPlayer();
        player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> source.sendSuccess(Component.literal("Your Eye health is " + eyeHealth.getHealthValue() + " Client: "+ ClientEyeHealth.getEyeHealth()), true));

        return 1;
    }
}

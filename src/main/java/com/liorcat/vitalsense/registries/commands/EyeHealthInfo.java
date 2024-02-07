package com.liorcat.vitalsense.registries.commands;

import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
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
        IEyeHealth cap = player.getCapability(VSCapabilities.EyeHealth.ENTITY);
        source.sendSuccess(() -> Component.literal("Your Eye health is " + cap.getHealth()), true);
        return 1;
    }
}

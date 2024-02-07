package com.liorcat.vitalsense.registries.commands;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class EyeHealthSetCommand {
    public EyeHealthSetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(VitalSense.MOD_ID)
                .then(Commands.literal("eyehealth")
                        .then(Commands.literal("set")
                                .then(Commands.argument("value", IntegerArgumentType.integer())
                                        .executes(commandContext -> {
                                                    int healthValue = IntegerArgumentType.getInteger(commandContext, "value");
                                                    return setEyeHealth(commandContext.getSource(), healthValue);
                                                }
                                        )
                                )
                        )
                )
        );
    }

    private int setEyeHealth(CommandSourceStack source, int value) {
        ServerPlayer player = source.getPlayer();
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        IEyeHealth cap = player.getCapability(VSCapabilities.EyeHealth.ENTITY);
        int newValue = (int) Math.min(value, cap.getMinHealth());
        cap.setHealth(newValue);
        IEyeHealth clientCap = localPlayer.getCapability(VSCapabilities.EyeHealth.ENTITY);
        source.sendSuccess(() -> Component.literal("Your Eye health is now set to " + newValue + ", client: " + clientCap.getHealth()), true);
        return value;
    }
}

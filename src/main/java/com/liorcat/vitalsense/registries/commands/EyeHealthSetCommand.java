package com.liorcat.vitalsense.registries.commands;

import com.liorcat.vitalsense.VitalSense;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
        /*
        EyeHealth cap = player.getCapability(EyeHealthProvider.eHealth);
        cap.setHealthValue(Math.min(value, 100));
        ModPackets.sendToPlayer(new S2CEyeHealth(cap.getHealthValue()), player);

         */
        source.sendSuccess(() -> Component.literal("Your Eye health is now set to " + value), true);
        return value;
    }
}

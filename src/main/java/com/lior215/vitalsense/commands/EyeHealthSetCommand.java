package com.lior215.vitalsense.commands;

import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class EyeHealthSetCommand {
        public EyeHealthSetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
            dispatcher.register(Commands.literal("vitalsense").then(Commands.literal("eyehealth").then(Commands.literal("set").then(Commands.argument("count23", IntegerArgumentType.integer())
                    .executes(commandContext -> {
                            int value23 = IntegerArgumentType.getInteger(commandContext, "count23");
                                return setEyeHealth(commandContext.getSource(), value23);
                        })))));
        }
    private int setEyeHealth (CommandSourceStack source, int value) {
        Player player = source.getPlayer();
        player.getCapability(EyeHealthProvider.EyeHealth).ifPresent(eyeHealth -> eyeHealth.setHealthValue(value));
        source.sendSuccess(Component.literal("Your Eye health is now set to " + value), true);
        return value;
    }
}
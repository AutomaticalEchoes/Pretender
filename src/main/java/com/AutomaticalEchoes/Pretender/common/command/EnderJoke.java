package com.AutomaticalEchoes.Pretender.common.command;

import com.AutomaticalEchoes.Pretender.common.CommonModEvents;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import com.AutomaticalEchoes.Pretender.common.netWork.packet.EnderJokeTask;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class EnderJoke {
    private static int AngryJoke(CommandSourceStack context , Collection<? extends Entity> p_137815_, @Nullable Integer structuresId) throws CommandSyntaxException {
        int EntityID =0;
        for (Entity entity : p_137815_) {
            EntityID =  entity.getId();
        }
        CommonModEvents.CHANNEL.sendToServer(new EnderJokeTask(structuresId, EntityID));
        return 0;
    }

    public static void register(CommandDispatcher<CommandSourceStack> p_137808_) {
        p_137808_.register(Commands.literal("joke").requires((p_137812_) -> p_137812_.hasPermission(2))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes((p_137810_) -> AngryJoke(p_137810_.getSource(), EntityArgument.getEntities(p_137810_, "targets"),IntegerArgumentType.getInteger(p_137810_,null)))
                        .then(Commands.argument("structureid", IntegerArgumentType.integer(0, SuspiciousEnderman.STRUCTURES.length))
                                .executes((p_137810_) -> AngryJoke(p_137810_.getSource(), EntityArgument.getEntities(p_137810_, "targets"),IntegerArgumentType.getInteger(p_137810_,"structureid"))))));
    }
}

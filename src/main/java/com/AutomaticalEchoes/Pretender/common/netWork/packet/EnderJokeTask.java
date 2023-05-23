package com.AutomaticalEchoes.Pretender.common.netWork.packet;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EnderJokeTask {
    private @Nullable Integer StructureID;
    private int EntityID;


    public EnderJokeTask(@Nullable Integer structureID, int entityID) {
        StructureID = structureID;
        EntityID = entityID;
    }

    public static void encode(EnderJokeTask msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeNullable(msg.StructureID,FriendlyByteBuf::writeInt);
        packetBuffer.writeInt(msg.EntityID);
    }
    public static EnderJokeTask decode(FriendlyByteBuf packetBuffer) {
        return new EnderJokeTask(packetBuffer.readNullable(FriendlyByteBuf::readInt),packetBuffer.readInt());
    }

    public static void onMessage(EnderJokeTask msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(EnderJokeTask msg, ServerPlayer sender) {
        Entity entity = sender.level.getEntity(msg.EntityID);
        if(entity instanceof SuspiciousEnderman suspiciousEnderman){
            suspiciousEnderman.setAngry(true);
            suspiciousEnderman.startJokeWith(sender,msg.StructureID);
        }
    }
}

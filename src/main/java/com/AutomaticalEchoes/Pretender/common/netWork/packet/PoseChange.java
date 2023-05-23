package com.AutomaticalEchoes.Pretender.common.netWork.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PoseChange {
    private static final Pose[] poseValue=Pose.values();
    public int poseNum=0;

    public PoseChange(int poseNum) {
        this.poseNum = poseNum;
    }
    public static void encode(PoseChange msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.poseNum);
    }
    public static PoseChange decode(FriendlyByteBuf packetBuffer) {
        return new PoseChange(packetBuffer.readInt());
    }
    public static void onMessage(PoseChange msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(PoseChange msg, ServerPlayer sender) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        player.setForcedPose(msg.poseNum !=0?poseValue[msg.poseNum]:null);
        player.setPose(poseValue[msg.poseNum]);
    }

}

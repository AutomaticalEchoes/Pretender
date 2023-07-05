package com.AutomaticalEchoes.Pretender.common.netWork;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.netWork.packet.EnderJokeTask;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final ResourceLocation CHANNEL_NAME=new ResourceLocation(Pretender.MOD_ID,"network");
    private static final String PROTOCOL_VERSION = new ResourceLocation(Pretender.MOD_ID,"1").toString();
    public static SimpleChannel RegisterPacket(){
        final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(()->PROTOCOL_VERSION)
                .simpleChannel();
        INSTANCE.messageBuilder(EnderJokeTask.class,1)
                .encoder(EnderJokeTask::encode)
                .decoder(EnderJokeTask::decode)
                .consumerMainThread(EnderJokeTask::onMessage)
                .add();
        return INSTANCE;
    }
}

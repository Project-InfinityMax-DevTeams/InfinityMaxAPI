package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.loader.LoaderExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.network.NetworkDirection;

public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    private static final String PROTOCOL_VERSION = "1";
    private final SimpleChannel channel;

    public ForgeNetworkImpl() {
        this.channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("yourmodid", "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
    }

    @Override
    public void register() {
        // packet登録はここで行う
        // channel.messageBuilder(...)
    }

    @Override
    public void sendToServer(Object packet) {
        channel.sendToServer(packet);
    }

    @Override
    public void sendToPlayer(Object packet, Object player) {
        channel.sendTo(packet,
                ((net.minecraft.server.level.ServerPlayer) player).connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }
}
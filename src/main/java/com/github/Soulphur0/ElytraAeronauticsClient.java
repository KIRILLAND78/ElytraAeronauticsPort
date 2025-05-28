package com.github.Soulphur0;

import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import com.github.Soulphur0.networking.client.EanServerToClientPacketHandler;
import com.github.Soulphur0.networking.server.EanClientToServerPacketHandler;
import com.github.Soulphur0.networking.server.EanServerPacketSender;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ElytraAeronauticsClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    PayloadTypeRegistry.playS2C().register(ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID,
        EanServerSettingsPacket.CODEC);
    PayloadTypeRegistry.playS2C().register(ElytraAeronautics.CLIENT_CONFIG_CHANGE_PAYLOAD_ID,
        EanClientSettingsPacket.CODEC);
    ClientPlayNetworking.registerGlobalReceiver(ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID,
        new EanServerToClientPacketHandler());
    ClientPlayNetworking.registerGlobalReceiver(ElytraAeronautics.CLIENT_CONFIG_CHANGE_PAYLOAD_ID,
        new EanClientToServerPacketHandler());

    CloudConfig.readFromDisk();
  }
}

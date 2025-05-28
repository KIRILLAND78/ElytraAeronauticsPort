package com.github.Soulphur0.networking.client;

import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.PlayPayloadHandler;
//import com.github.Soulphur0.networking.server.EanServerSettingsPacketSerializer
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * Class that handles incoming packets from the server.<br>
 * <br>
 * Right now, its use mainly revolve around synchronizing settings, it may serve
 * other purposes further on.<br>
 * <br>
 */
public class EanServerToClientPacketHandler implements PlayPayloadHandler<EanServerSettingsPacket> {

  @Override
  public void receive(EanServerSettingsPacket payload,
      Context context) {
    EanServerSettings serverSettings = payload.getServerSettings();
    FlightConfig.updateClientSettings(serverSettings.getFlightConfigInstance());
  }

  // ? Based on the received client setting's category, a method for its related
  // config class updates the settings with the passed values.
}

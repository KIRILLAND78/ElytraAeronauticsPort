package com.github.Soulphur0.networking.server;

import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.PlayPayloadHandler;
//import com.github.Soulphur0.networking.server.EanServerSettingsPacketSerializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
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
public class EanClientToServerPacketHandler implements PlayPayloadHandler<EanClientSettingsPacket> {

  @Override
  public void receive(EanClientSettingsPacket payload,
      Context context) {
    switch (payload.getClientSettings().settingValues.get(0)) {
      case "generalCloudConfig" ->
        CloudConfig.updateGeneralConfig(
            context.player(),
            payload.getClientSettings().settingValues.get(1),
            payload.getClientSettings().settingValues.get(2));
      case "cloudLayerConfig" -> CloudConfig.updateCloudLayerConfig(
          context.player(),
          payload.getClientSettings().settingValues.get(1),
          payload.getClientSettings().settingValues.get(2),
          payload.getClientSettings().settingValues.get(3));
    }
  }

  // ? Based on the received client setting's category, a method for its related
  // config class updates the settings with the passed values.

}

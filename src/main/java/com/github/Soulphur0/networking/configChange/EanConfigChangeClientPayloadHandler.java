package com.github.Soulphur0.networking.configChange;

import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.configSync.EanConfigSyncPayload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.PlayPayloadHandler;


/**
 * Class that handles incoming packets from the server.<br>
 * <br>
 * Right now, its use mainly revolve around synchronizing settings, it may serve
 * other purposes further on.<br>
 * <br>
 */
public class EanConfigChangeClientPayloadHandler implements PlayPayloadHandler<EanConfigSyncPayload> {

  @Override
  public void receive(EanConfigSyncPayload payload,
                      Context context) {
    EanServerSettings serverSettings = payload.getServerSettings();
    FlightConfig.updateClientSettings(serverSettings.getFlightConfigInstance());
  }

  // ? Based on the received client setting's category, a method for its related
  // config class updates the settings with the passed values.
}

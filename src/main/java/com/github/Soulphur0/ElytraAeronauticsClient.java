package com.github.Soulphur0;

import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.networking.configChange.EanConfigChangePayload;
import com.github.Soulphur0.networking.configChange.EanConfigChangeClientPayloadHandler;
import com.github.Soulphur0.networking.configSync.EanConfigSyncClientPayloadHandler;
import com.github.Soulphur0.networking.configSync.EanConfigSyncPayload;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

@Environment(EnvType.CLIENT)
public class ElytraAeronauticsClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    PayloadTypeRegistry.playS2C().register(ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID,
        EanConfigSyncPayload.CODEC);
    PayloadTypeRegistry.playS2C().register(ElytraAeronautics.CONFIG_CHANGE_PAYLOAD_ID,
        EanConfigChangePayload.CODEC);
    ClientPlayNetworking.registerGlobalReceiver(ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID,
        new EanConfigChangeClientPayloadHandler());
    ClientPlayNetworking.registerGlobalReceiver(ElytraAeronautics.CONFIG_CHANGE_PAYLOAD_ID,
        new EanConfigSyncClientPayloadHandler());

    CloudConfig.readFromDisk();
  }
}

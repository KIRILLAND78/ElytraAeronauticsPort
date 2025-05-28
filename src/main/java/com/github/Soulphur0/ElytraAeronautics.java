package com.github.Soulphur0;

import com.github.Soulphur0.config.commands.EanCommands;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.configChange.EanConfigChangePayload;
import com.github.Soulphur0.networking.configSync.EanServerPayloadSender;
import com.github.Soulphur0.networking.configSync.EanConfigSyncPayload;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElytraAeronautics implements ModInitializer {

  public static final Logger LOGGER = LogManager.getLogger("ElytraAeronautics");

  public static final CustomPayload.Id<EanConfigSyncPayload> CONFIG_FULL_SYNC_PAYLOAD_ID = new CustomPayload.Id<EanConfigSyncPayload>(
      Identifier.of("ean", "sync_config"));
  public static final CustomPayload.Id<EanConfigChangePayload> CONFIG_CHANGE_PAYLOAD_ID = new CustomPayload.Id<EanConfigChangePayload>(
      Identifier.of("ean", "client_config"));

  @Override
  public void onInitialize() {
    // ? Read the config data saved in disk di // initialization.
    FlightConfig.readFromDisk();

    // ? Register config command.
    mands.register();

    // ? Register event handler.
    // ¿ On world/server join, sync the config, on dedicated servers reading from
    // disk is not needed.
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
      ServerPlayerEntity player = (ServerPlayerEntity) handler.player;

      if (server.isDedicated())
        EanServerPayloadSender.syncClientConfigWithServer(player);
      else {
        FlightConfig.readFromDisk();
        EanServerPayloadSender.syncClientConfigWithServer(player);
      }
    });

    LOGGER.info("Elytra Aeronautics initialized! Have a good flight!");
  }
}
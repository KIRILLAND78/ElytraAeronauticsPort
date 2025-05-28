package com.github.Soulphur0;

import com.github.Soulphur0.config.commands.EanCommands;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import com.github.Soulphur0.networking.server.EanServerPacketSender;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElytraAeronautics implements ModInitializer {

  public static final Logger LOGGER = LogManager.getLogger("ElytraAeronautics");

  public static final Identifier CONFIG_FULL_SYNC_PACKET_ID = new Identifier("ean", "sync_config");
  public static final Identifier CLIENT_CONFIG_CHANGE_PACKET_ID = new Identifier("ean", "client_config");

  public static final CustomPayload.Id<EanServerSettingsPacket> CONFIG_FULL_SYNC_PAYLOAD_ID = new CustomPayload.Id<EanServerSettingsPacket>(
      new Identifier("ean", "sync_config"));
  public static final CustomPayload.Id<EanClientSettingsPacket> CLIENT_CONFIG_CHANGE_PAYLOAD_ID = new CustomPayload.Id<EanClientSettingsPacket>(
      new Identifier("ean", "client_config"));

  @Override
  public void onInitialize() {
    // ? Read the config data saved in disk directly to config instance upon
    // initialization.
    FlightConfig.readFromDisk();

    // ? Register config command.
    EanCommands.register();

    // ? Register event handler.
    // ¿ On world/server join, sync the config, on dedicated servers reading from
    // disk is not needed.
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
      ServerPlayerEntity player = (ServerPlayerEntity) handler.player;

      if (server.isDedicated())
        EanServerPacketSender.syncClientConfigWithServer(player);
      else {
        FlightConfig.readFromDisk();
        EanServerPacketSender.syncClientConfigWithServer(player);
      }
    });

    LOGGER.info("Elytra Aeronautics initialized! Have a good flight!");
  }
}
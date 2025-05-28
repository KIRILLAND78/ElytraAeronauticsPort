package com.github.Soulphur0.networking.server;

import com.github.Soulphur0.ElytraAeronautics;
import com.github.Soulphur0.config.EanClientSettings;
import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

/**
 * Elytra Aeronautics' custom packet class, it holds a EanServerSettings
 * instance with all server-dependant config settings.<br>
 * <br>
 * Reading and writing calls are done by the EanServerSettingsPacketSerializer
 * class.<br>
 * <br>
 * The logic of packing and unpacking the settings themselves is done within the
 * EanServerSettings class.<br>
 * <br>
 * 
 * @see EanServerSettingsPacketSerializer
 * @see EanServerSettings
 */
public class EanServerSettingsPacket implements CustomPayload {
  private final EanServerSettings serverSettings;

  @Override
  public Id<? extends CustomPayload> getId() {
    return ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID;
  }

  public EanServerSettingsPacket(EanServerSettings eanServerSettings) {
    this.serverSettings = eanServerSettings;
  }

  public EanServerSettings getServerSettings() {
    return serverSettings;
  }

  public static final PacketCodec<PacketByteBuf, EanServerSettingsPacket> CODEC = new PacketCodec<PacketByteBuf, EanServerSettingsPacket>() {
    @Override
    public EanServerSettingsPacket decode(PacketByteBuf buf) {
      return new EanServerSettingsPacket(EanServerSettings.createFromBuffer(buf));
    }

    @Override
    public void encode(PacketByteBuf buf, EanServerSettingsPacket packet) {
      packet.serverSettings.writeToBuffer(buf);
    }
  };

}

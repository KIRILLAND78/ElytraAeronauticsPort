package com.github.Soulphur0.networking.client;

import com.github.Soulphur0.ElytraAeronautics;
import com.github.Soulphur0.config.EanClientSettings;
import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * Elytra Aeronautics' custom packet class, it holds a EanClientSettings
 * instance with a client config setting.<br>
 * <br>
 * Reading and writing calls are done by the EanClientSettingsPacketSerializer
 * class.<br>
 * <br>
 * The logic of packing and unpacking the settings themselves is done within the
 * EanClientSettings class.<br>
 * <br>
 * 
 * @see EanClientSettingsPacketSerializer
 * @see EanClientSettings
 */
public class EanClientSettingsPacket implements CustomPayload {
  private final EanClientSettings clientSettings;

  @Override
  public Id<? extends CustomPayload> getId() {
    return ElytraAeronautics.CLIENT_CONFIG_CHANGE_PAYLOAD_ID;
  }

  public EanClientSettingsPacket(EanClientSettings eanClientSettings) {
    this.clientSettings = eanClientSettings;
  }

  public EanClientSettings getClientSettings() {
    return clientSettings;
  }

  public static final PacketCodec<PacketByteBuf, EanClientSettingsPacket> CODEC = new PacketCodec<PacketByteBuf, EanClientSettingsPacket>() {
    @Override
    public EanClientSettingsPacket decode(PacketByteBuf buf) {
      return new EanClientSettingsPacket(EanClientSettings.createFromBuffer(buf));
    }

    @Override
    public void encode(PacketByteBuf buf, EanClientSettingsPacket packet) {
      packet.clientSettings.writeToBuffer(buf);
    }
  };
}

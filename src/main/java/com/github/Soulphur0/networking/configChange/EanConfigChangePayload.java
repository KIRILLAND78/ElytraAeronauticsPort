package com.github.Soulphur0.networking.configChange;

import com.github.Soulphur0.ElytraAeronautics;
import com.github.Soulphur0.config.EanConfigChange;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

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
 * @see EanConfigChange
 */
public class EanConfigChangePayload implements CustomPayload {
  private final EanConfigChange clientSettings;

  @Override
  public Id<? extends CustomPayload> getId() {
    return ElytraAeronautics.CONFIG_CHANGE_PAYLOAD_ID;
  }

  public EanConfigChangePayload(EanConfigChange eanClientSettings) {
    this.clientSettings = eanClientSettings;
  }

  public EanConfigChange getClientSettings() {
    return clientSettings;
  }

  public static final PacketCodec<PacketByteBuf, EanConfigChangePayload> CODEC = new PacketCodec<PacketByteBuf, EanConfigChangePayload>() {
    @Override
    public EanConfigChangePayload decode(PacketByteBuf buf) {
      return new EanConfigChangePayload(EanConfigChange.createFromBuffer(buf));
    }

    @Override
    public void encode(PacketByteBuf buf, EanConfigChangePayload packet) {
      packet.clientSettings.writeToBuffer(buf);
    }
  };
}

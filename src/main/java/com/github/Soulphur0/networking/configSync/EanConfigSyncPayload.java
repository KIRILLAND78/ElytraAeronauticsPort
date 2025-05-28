package com.github.Soulphur0.networking.configSync;

import com.github.Soulphur0.ElytraAeronautics;
import com.github.Soulphur0.config.EanServerSettings;

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
 * @see EanServerSettings
 */
public class EanConfigSyncPayload implements CustomPayload {
  private final EanServerSettings serverSettings;

  @Override
  public Id<? extends CustomPayload> getId() {
    return ElytraAeronautics.CONFIG_FULL_SYNC_PAYLOAD_ID;
  }

  public EanConfigSyncPayload(EanServerSettings eanServerSettings) {
    this.serverSettings = eanServerSettings;
  }

  public EanServerSettings getServerSettings() {
    return serverSettings;
  }

  public static final PacketCodec<PacketByteBuf, EanConfigSyncPayload> CODEC = new PacketCodec<PacketByteBuf, EanConfigSyncPayload>() {
    @Override
    public EanConfigSyncPayload decode(PacketByteBuf buf) {
      return new EanConfigSyncPayload(EanServerSettings.createFromBuffer(buf));
    }

    @Override
    public void encode(PacketByteBuf buf, EanConfigSyncPayload packet) {
      packet.serverSettings.writeToBuffer(buf);
    }
  };

}

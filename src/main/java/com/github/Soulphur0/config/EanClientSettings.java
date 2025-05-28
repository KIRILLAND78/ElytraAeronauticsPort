package com.github.Soulphur0.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

//import com.github.Soulphur0.networking.client.EanClientSettingsPacketSerializer;
import net.minecraft.network.PacketByteBuf;

/**
 * Class for EanClientSettings objects.<br>
 * <br>
 * This class, unlike the rest of config classes, is not a singleton, but rather
 * an object that is instantiated whenever
 * client's settings are updated via command (server-sided) and need to be
 * packed to be sent from the server to the client.<br>
 * <br>
 * 
 * @see EanClientSettingsPacket
 * @see EanClientSettingsPacketSerializer
 */
public class EanClientSettings {
  public List<String> settingValues;

  // ? Constructor with arbitrary number of parameters.
  // ¿ The first String determines the setting type, the rest of string depend on
  // the setting in particular.
  // ¿ The EanClientPacketHandler class redirects to the setting processing method
  // for each client setting in its corresponding class.
  public EanClientSettings(String... values) {
    settingValues = Arrays.asList(values);
  }

  public EanClientSettings(List<String> values) {
    this.settingValues = values;
  }

  // . NETWORKING

  // ? Write all the values of the setting to the packet buff sequentially.
  public void writeToBuffer(PacketByteBuf buf) {
    buf.writeVarInt(settingValues.size());
    for (String settingValue : settingValues) {
      buf.writeString(settingValue);
    }
  }

  // ? Read from the packet buf all the values of the setting sequentially.
  // ¿ Used to rebuild this class object from a received packet.
  public static EanClientSettings createFromBuffer(PacketByteBuf buf) {
    int count = buf.readVarInt();
    List<String> settingValues = new ArrayList<>(count);

    for (int i = 0; i < count; i++) {
      settingValues.add(buf.readString());
    }

    return new EanClientSettings(settingValues);
  }
}

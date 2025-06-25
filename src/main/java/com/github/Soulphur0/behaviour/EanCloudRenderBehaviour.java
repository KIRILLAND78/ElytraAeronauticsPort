
package com.github.Soulphur0.behaviour;

import com.github.Soulphur0.config.objects.CloudLayer;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.mixin.CloudRendererAccessors;
import com.github.Soulphur0.mixin.WorldRendererAccessors;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class EanCloudRenderBehaviour {

  public static void renderClouds(WorldRenderer instance,
                                  FrameGraphBuilder frameGraphBuilder,
                                  Matrix4f positionMatrix,
                                  Matrix4f projectionMatrix,
                                  Vec3d cameraPos,
                                  float ticks,
                                  int timeColor) {
    // my opinion: simpler = better (feel free to disagree, we can talk about it in discord)
    // (also DRY, why should i remake cloud drawing from scratch if it already is in the game (also me lazy))

    // most of the functionality will be lost, but this is not THAT bad. I mean, i am sure noone will be sad because
    // clouds can't be half-transparent now. If i am wrong, i will try to implement it back later.
    CloudConfig config = CloudConfig.getOrCreateInstance();
    for (int layerNum = 0; layerNum < config.getNumberOfLayers(); layerNum++) {
      CloudLayer layer = CloudConfig.cloudLayers[layerNum];

      layer.setWithinRenderDistance(Math.abs(layer.getAltitude() - cameraPos.y) <= layer.getVerticalRenderDistance());
      layer.setWithinLodRenderDistance(Math.abs(layer.getAltitude() - cameraPos.y) <= layer.getLodRenderDistance());

      CloudRenderMode render = CloudRenderMode.FAST;
      if ((layer.getCloudType() == CloudConfig.CloudTypes.FANCY) ||
              ((layer.getCloudType() == CloudConfig.CloudTypes.LOD) && (layer.isWithinLodRenderDistance()))){
        render = CloudRenderMode.FANCY;
      }
      var usedColor = layer.getCloudColor();
      if (layer.isShading()){
        usedColor = ColorHelper.multiplyColor(layer.getCloudColor(), timeColor);
      }

      ((WorldRendererAccessors) instance).rсInvoker(frameGraphBuilder, positionMatrix, projectionMatrix, render, cameraPos, ticks*layer.getCloudSpeed(), usedColor, (float) layer.getAltitude());
    }
  }
}
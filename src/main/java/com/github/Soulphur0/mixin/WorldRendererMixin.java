package com.github.Soulphur0.mixin;

import com.github.Soulphur0.config.objects.CloudLayer;
import com.github.Soulphur0.config.singletons.CloudConfig;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.*;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {

  @Shadow @Final private CloudRenderer cloudRenderer;
  @Shadow @Final private DefaultFramebufferSet framebufferSet;

  // Separate CloudRenderer for each additional layer
  @Unique
  private CloudRenderer[] elytra_layerRenderers = null;
  @Unique
  private int elytra_lastLayerCount = 0;

  @Redirect(
          method = "render(Lnet/minecraft/client/util/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V",
          at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/render/FrameGraphBuilder;Lnet/minecraft/client/option/CloudRenderMode;Lnet/minecraft/util/math/Vec3d;JFIF)V"
          )
  )
  private void redirectRenderClouds(WorldRenderer instance,
                                    FrameGraphBuilder frameGraphBuilder,
                                    CloudRenderMode renderMode,
                                    Vec3d cameraPos,
                                    long susTicks,
                                    float ticks,
                                    int color,
                                    float cloudHeight) {

    if (!CloudConfig.getOrCreateInstance().isUseEanClouds()) {
      ((WorldRendererAccessors) instance).rсInvoker(frameGraphBuilder, renderMode, cameraPos, susTicks, ticks, color, cloudHeight);
      return;
    }

    CloudConfig config = CloudConfig.getOrCreateInstance();
    int numLayers = config.getNumberOfLayers();

    if (CloudConfig.cloudLayers == null || CloudConfig.cloudLayers.length == 0) {
      return;
    }

    // Get cloud cells from main renderer
    CloudRenderer.CloudCells cells = ((CloudRendererAccessors) cloudRenderer).getCells();
    if (cells == null) {
      return;
    }

    // Initialize layer renderers if needed
    if (elytra_layerRenderers == null || elytra_lastLayerCount != numLayers) {
      if (elytra_layerRenderers != null) {
        for (CloudRenderer r : elytra_layerRenderers) {
          if (r != null) r.close();
        }
      }
      elytra_layerRenderers = new CloudRenderer[numLayers];
      for (int i = 0; i < numLayers; i++) {
        elytra_layerRenderers[i] = new CloudRenderer();
      }
      elytra_lastLayerCount = numLayers;
    }

    // Render each layer with its own CloudRenderer and frame pass
    for (int layerNum = 0; layerNum < numLayers; layerNum++) {

      CloudLayer layer = CloudConfig.cloudLayers[layerNum];

      if (layer == null) {
        continue;
      }

      layer.setWithinRenderDistance(Math.abs(layer.getAltitude() - cameraPos.y) <= layer.getVerticalRenderDistance());
      layer.setWithinLodRenderDistance(Math.abs(layer.getAltitude() - cameraPos.y) <= layer.getLodRenderDistance());

      CloudRenderMode mode = CloudRenderMode.FAST;
      if ((layer.getCloudType() == CloudConfig.CloudTypes.FANCY) ||
              ((layer.getCloudType() == CloudConfig.CloudTypes.LOD) && (layer.isWithinLodRenderDistance()))) {
        mode = CloudRenderMode.FANCY;
      }

      int layerColor = layer.getCloudColor();

      if (((layerColor >> 24) & 0xFF) == 0) {
        layerColor = layerColor | 0xAA000000;
      }

      if (layer.isShading()) {
        layerColor = elytra_multiplyColors(layerColor, color);
      }

      float layerAltitude = (float) layer.getAltitude();

      double susScaled = (double)susTicks * layer.getCloudSpeed();
      double ticksScaled = (double)ticks * layer.getCloudSpeed();

      long susWhole = (long)susScaled;
      double susFrac = susScaled - susWhole;

      double combinedFrac = ticksScaled + susFrac;
      long carryOver = (long)combinedFrac;

      long layerTicksSus = susWhole + carryOver;
      float layerTicks = (float)(combinedFrac - carryOver);


      // Get this layer's dedicated renderer
      CloudRenderer layerRenderer = elytra_layerRenderers[layerNum];
      ((CloudRendererAccessors) layerRenderer).setCells(cells);

      // Create a frame pass for this layer (like vanilla does)
      FramePass framePass = frameGraphBuilder.createPass("clouds_layer_" + layerNum);
      if (this.framebufferSet.cloudsFramebuffer != null) {
        this.framebufferSet.cloudsFramebuffer = framePass.transfer(this.framebufferSet.cloudsFramebuffer);
      } else {
        this.framebufferSet.mainFramebuffer = framePass.transfer(this.framebufferSet.mainFramebuffer);
      }

      // Capture values for lambda
      final int finalColor = layerColor;
      final CloudRenderMode finalMode = mode;
      final float finalAltitude = layerAltitude;
      final float finalTicks = layerTicks;
      final CloudRenderer finalRenderer = layerRenderer;

      framePass.setRenderer(() -> {
        finalRenderer.renderClouds(finalColor, finalMode, finalAltitude, cameraPos, layerTicksSus, finalTicks);
      });
    }
  }

  @Unique
  private static int elytra_multiplyColors(int color1, int color2) {
    int r1 = (color1 >> 16) & 0xFF;
    int g1 = (color1 >> 8) & 0xFF;
    int b1 = color1 & 0xFF;
    int r2 = (color2 >> 16) & 0xFF;
    int g2 = (color2 >> 8) & 0xFF;
    int b2 = color2 & 0xFF;
    int a = (color1 >> 24) & 0xFF;
    int r = (r1 * r2) / 255;
    int g = (g1 * g2) / 255;
    int b = (b1 * b2) / 255;
    return (a << 24) | (r << 16) | (g << 8) | b;
  }
}

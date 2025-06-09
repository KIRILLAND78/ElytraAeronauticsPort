package com.github.Soulphur0.mixin;

import com.github.Soulphur0.behaviour.EanCloudRenderBehaviour;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {
  @Shadow public CloudRenderer cloudRenderer;

  @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/render/FrameGraphBuilder;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/option/CloudRenderMode;Lnet/minecraft/util/math/Vec3d;FIF)V"))
  private void ean_renderClouds(WorldRenderer instance,
                                FrameGraphBuilder frameGraphBuilder,
                                Matrix4f positionMatrix,
                                Matrix4f projectionMatrix,
                                CloudRenderMode renderMode,
                                Vec3d cameraPos,
                                float ticks,
                                int color,
                                float cloudHeight,
                                Operation<Void> original)
  {
    if (CloudConfig.getOrCreateInstance().isUseEanClouds())//todo: use CloudRenderMode and cloudHeight (wtf is frameGraphBuilder)
      EanCloudRenderBehaviour.ean_renderClouds2(instance, projectionMatrix, positionMatrix, ticks, cameraPos);
    else
      original.call(instance, frameGraphBuilder, positionMatrix, projectionMatrix, renderMode, cameraPos, ticks, color, cloudHeight);
  }

}
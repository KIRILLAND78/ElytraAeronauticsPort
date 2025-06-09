package com.github.Soulphur0.mixin;

import com.github.Soulphur0.behaviour.EanCloudRenderBehaviour;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.SynchronousResourceReloader;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {
  @Shadow public CloudRenderer cloudRenderer;
  @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V"))
  private void ean_renderClouds(WorldRenderer instance,
      MatrixStack matrices,
      Matrix4f matrix1,
      Matrix4f matrix2,
      float tickDelta,
      double x,
      double y,
      double z,
      Operation<Void> original) {
    if (CloudConfig.getOrCreateInstance().isUseEanClouds())
      EanCloudRenderBehaviour.ean_renderClouds(instance, matrices, matrix2, matrix1, tickDelta, x, y, z);
    else
      original.call(instance, matrices, matrix1, matrix2, tickDelta, x, y, z);
  }

}
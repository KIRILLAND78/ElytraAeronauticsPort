package com.github.Soulphur0.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CloudRenderer.class)
public interface CloudRendererAccessors {
    @Invoker("renderClouds")
    public void renderCloudsInvoker(int color, CloudRenderMode cloudRenderMode, float cloudHeight, Vec3d cameraPos, float ticks);

}

package com.github.Soulphur0.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessors {
    @Accessor
    ClientWorld getWorld();
    @Invoker("renderClouds")
    void rсInvoker(FrameGraphBuilder frameGraphBuilder, Matrix4f positionMatrix, Matrix4f projectionMatrix, CloudRenderMode renderMode, Vec3d cameraPos, float ticks, int color, float cloudHeight);
    @Accessor
    int getTicks();

    @Accessor
    MinecraftClient getClient();
}

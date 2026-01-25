package com.github.Soulphur0.mixin;

import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessors {
    @Invoker("renderClouds")
    void rсInvoker(FrameGraphBuilder frameGraphBuilder, CloudRenderMode renderMode, Vec3d cameraPos, long susTicks, float ticks, int color, float cloudHeight);
}

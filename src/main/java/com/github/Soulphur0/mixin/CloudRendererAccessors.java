package com.github.Soulphur0.mixin;

import net.minecraft.client.render.CloudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CloudRenderer.class)
public interface CloudRendererAccessors {
    @Accessor("cells")
    CloudRenderer.CloudCells getCells();

    @Accessor("cells")
    void setCells(CloudRenderer.CloudCells cells);
}

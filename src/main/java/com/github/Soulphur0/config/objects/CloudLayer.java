package com.github.Soulphur0.config.objects;

import com.github.Soulphur0.config.singletons.CloudConfig;
import com.google.gson.annotations.Expose;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;

@Environment(EnvType.CLIENT)
public class CloudLayer {
    // ; Cloud positioning settings
    @Expose
    private double altitude;

    // ; Cloud rendering settings
    @Expose
    private CloudConfig.CloudTypes cloudType = CloudConfig.CloudTypes.LOD;
    @Expose
    private float verticalRenderDistance = 1000.0F;
    @Expose
    private float lodRenderDistance = 150.0F;

    // ; Cloud style settings
    @Expose
    private float cloudSpeed = 1.0F;
    @Expose
    private int cloudColor = 0xffffff;
    @Expose
    private float cloudOpacity = 0.8F;
    @Expose
    private boolean shading = true;
    private boolean withinRenderDistance;
    private boolean withinLodRenderDistance;
    private float translationX;
    private float translationZ;

    public CloudLayer(){

    }

    // $ GETTERS & SETTERS
    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public CloudConfig.CloudTypes getCloudType() {
        return cloudType;
    }

    public void setCloudType(CloudConfig.CloudTypes cloudType) {
        this.cloudType = cloudType;
    }

    public float getVerticalRenderDistance() {
        return verticalRenderDistance;
    }

    public void setVerticalRenderDistance(float verticalRenderDistance) {
        this.verticalRenderDistance = verticalRenderDistance;
    }

    public float getLodRenderDistance() {
        return lodRenderDistance;
    }

    public void setLodRenderDistance(float lodRenderDistance) {
        this.lodRenderDistance = lodRenderDistance;
    }

    public float getCloudSpeed() {
        return cloudSpeed;
    }

    public void setCloudSpeed(float cloudSpeed) {
        this.cloudSpeed = cloudSpeed;
    }

    public int getCloudColor() {
        return cloudColor;
    }

    public void setCloudColor(int cloudColor) {
        this.cloudColor = cloudColor;
    }

    public float getCloudOpacity() {
        return cloudOpacity;
    }

    public void setCloudOpacity(float cloudOpacity) {
        this.cloudOpacity = cloudOpacity;
    }

    public boolean isShading() {
        return shading;
    }

    public void setShading(boolean shading) {
        this.shading = shading;
    }

    public void setRenderAltitude(double renderAltitude) {
        this.renderAltitude = renderAltitude;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }

    public boolean isWithinRenderDistance() {
        return withinRenderDistance;
    }

    public void setWithinRenderDistance(boolean withinRenderDistance) {
        this.withinRenderDistance = withinRenderDistance;
    }

    public boolean isWithinLodRenderDistance() {
        return withinLodRenderDistance;
    }

    public void setWithinLodRenderDistance(boolean withinLodRenderDistance) {
        this.withinLodRenderDistance = withinLodRenderDistance;
    }

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public float getTranslationZ() {
        return translationZ;
    }

    public void setTranslationZ(float translationZ) {
        this.translationZ = translationZ;
    }
}

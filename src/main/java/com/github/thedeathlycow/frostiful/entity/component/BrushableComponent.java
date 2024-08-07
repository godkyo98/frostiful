package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class BrushableComponent implements Component, AutoSyncedComponent {

    private static final String LAST_BRUSHED_TIME_KEY = "last_brushed_time";

    private long lastBrushTime = -1;

    private final AnimalEntity provider;
    private final FBrushable brushable;

    public BrushableComponent(AnimalEntity provider) {
        this.provider = provider;
        this.brushable = (FBrushable) provider;
    }

    @Override
    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeLong(this.lastBrushTime);
    }

    @Override
    public void applySyncPacket(RegistryByteBuf buf) {
        this.lastBrushTime = buf.readLong();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        if (tag.contains(LAST_BRUSHED_TIME_KEY, NbtElement.LONG_TYPE)) {
            this.lastBrushTime = tag.getLong(LAST_BRUSHED_TIME_KEY);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        if (brushable.frostiful$wasBrushed()) {
            tag.putLong(LAST_BRUSHED_TIME_KEY, lastBrushTime);
        }
    }

    public long getLastBrushTime() {
        return lastBrushTime;
    }

    public void setLastBrushTime(long lastBrushTime) {
        if (this.lastBrushTime != lastBrushTime) {
            this.lastBrushTime = lastBrushTime;
            FComponents.BRUSHABLE_COMPONENT.sync(this.provider);
        }
    }

    public boolean isBrushable() {
        return this.provider.isAlive()
                && !this.provider.isBaby()
                && !this.wasBrushed();
    }

    public boolean wasBrushed() {
        return lastBrushTime >= 0L
                && this.provider.getWorld().getTimeOfDay() - lastBrushTime <= FBrushable.BRUSH_COOLDOWN;
    }
}

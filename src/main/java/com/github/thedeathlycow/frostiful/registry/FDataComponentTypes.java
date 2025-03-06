package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.component.FrostResistanceLevelComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public final class FDataComponentTypes {
    public static final ComponentType<FrostResistanceLevelComponent> FROST_RESISTANCE_LEVEL = register(
            "frost_resistance_level",
            builder -> builder
                    .codec(FrostResistanceLevelComponent.CODEC)
                    .packetCodec(FrostResistanceLevelComponent.PACKET_CODEC)
                    .cache()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item components");
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Frostiful.id(id),
                builderOperator.apply(ComponentType.builder()).build()
        );
    }

    private FDataComponentTypes() {

    }
}
package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public final class FEntityAttributes {
    public static final double BASE_MIN_TEMPERATURE = 45;

    public static final RegistryEntry<EntityAttribute> ICE_BREAK_DAMAGE = register(
            "generic.ice_breaker_damage",
            new ClampedEntityAttribute(
                    "attribute.frostiful.generic.ice_break_damage", 3.0, 0, 1024.0
            ).setTracked(true)
    );

    // called from mixin
    public static void createLivingAttributes(DefaultAttributeContainer.Builder builder) {
        builder.add(ICE_BREAK_DAMAGE);
    }

    private static RegistryEntry<EntityAttribute> register(String name, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Frostiful.id(name), attribute);
    }

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful attributes");

        ThermooAttributes.baseValueEvent(ThermooAttributes.MIN_TEMPERATURE).register((entity, baseValue) -> {
            if (entity.getType() == FEntityTypes.FROSTOLOGER) {
                return baseValue;
            } else {
                return baseValue + BASE_MIN_TEMPERATURE;
            }
        });
    }

    private FEntityAttributes() {

    }
}
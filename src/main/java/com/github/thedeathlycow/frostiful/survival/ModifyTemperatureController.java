package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;

public class ModifyTemperatureController extends EnvironmentControllerDecorator {

    public ModifyTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public double getBaseValueForAttribute(RegistryEntry<EntityAttribute> attribute, LivingEntity entity) {
        double base = controller.getBaseValueForAttribute(attribute, entity);
        if (attribute == ThermooAttributes.MIN_TEMPERATURE && entity.getType() == EntityType.PLAYER) {
            base += 5.0;
        }
        if (attribute == ThermooAttributes.MAX_TEMPERATURE && entity.getType() == FEntityTypes.FROSTOLOGER) {
            base = 0;
        }
        return base;
    }
}

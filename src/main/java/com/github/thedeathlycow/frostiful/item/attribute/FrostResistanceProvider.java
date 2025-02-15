package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.group.CombatConfigGroup;
import com.github.thedeathlycow.thermoo.api.armor.material.ArmorMaterialEvents;
import com.github.thedeathlycow.thermoo.api.armor.material.ArmorMaterialTags;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class FrostResistanceProvider implements ArmorMaterialEvents.GetResistance {

    @Override
    public double getValue(RegistryEntry<ArmorMaterial> armorMaterial, ArmorItem.Type armorType) {
        ProtectionLevel level = ProtectionLevel.forMaterial(armorMaterial);

        FArmorType type = FArmorType.forArmorType(armorType);

        CombatConfigGroup config = Frostiful.getConfig().combatConfig;
        return level != ProtectionLevel.DEFAULT
                ? type.getBaseFrostResistance() * level.getFrostResistanceMultiplier(config)
                : Double.NaN;
    }

    public enum ProtectionLevel {
        VERY_PROTECTIVE(ArmorMaterialTags.VERY_RESISTANT_TO_COLD, CombatConfigGroup::getVeryProtectiveFrostResistanceMultiplier),
        PROTECTIVE(ArmorMaterialTags.RESISTANT_TO_COLD, CombatConfigGroup::getProtectiveFrostResistanceMultiplier),
        HARMFUL(ArmorMaterialTags.WEAK_TO_COLD, CombatConfigGroup::getHarmfulFrostResistanceMultiplier),
        VERY_HARMFUL(ArmorMaterialTags.VERY_WEAK_TO_COLD, CombatConfigGroup::getVeryHarmfulFrostResistanceMultiplier),
        DEFAULT(material -> true, c -> Double.NaN);

        private final Predicate<RegistryEntry<ArmorMaterial>> predicate;

        private final ToDoubleFunction<CombatConfigGroup> frostResistanceProvider;

        ProtectionLevel(TagKey<ArmorMaterial> tag, ToDoubleFunction<CombatConfigGroup> frostResistanceProvider) {
            this(material -> material.isIn(tag), frostResistanceProvider);
        }

        ProtectionLevel(Predicate<RegistryEntry<ArmorMaterial>> predicate, ToDoubleFunction<CombatConfigGroup> frostResistanceProvider) {
            this.predicate = predicate;
            this.frostResistanceProvider = frostResistanceProvider;
        }

        public double getFrostResistanceMultiplier(CombatConfigGroup config) {
            return this.frostResistanceProvider.applyAsDouble(config);
        }

        public boolean appliesToMaterial(RegistryEntry<ArmorMaterial> material) {
            return this.predicate.test(material);
        }

        public static ProtectionLevel forMaterial(RegistryEntry<ArmorMaterial> material) {
            for (ProtectionLevel level : values()) {
                if (level.appliesToMaterial(material)) {
                    return level;
                }
            }
            return DEFAULT;
        }
    }

}

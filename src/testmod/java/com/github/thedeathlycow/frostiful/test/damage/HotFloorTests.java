package com.github.thedeathlycow.frostiful.test.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class HotFloorTests {
    @GameTest(templateName = "frostiful-test:magma_block_test")
    public void villager_on_magma_heated_more_than_villager_on_stone(TestContext context) {
        int temperatureChange = Frostiful.getConfig().freezingConfig.getHeatFromHotFloor();

        final BlockPos stonePos = new BlockPos(2, 3, 3);
        final BlockPos magmaPos = new BlockPos(4, 3, 3);
        final int initialTemperature = -1000;

        final VillagerEntity magmaVillager = context.spawnMob(EntityType.VILLAGER, magmaPos);
        final VillagerEntity stoneVillager = context.spawnMob(EntityType.VILLAGER, stonePos);

        stoneVillager.thermoo$setTemperature(initialTemperature);
        magmaVillager.thermoo$setTemperature(initialTemperature);
        context.expectEntityWithData(
                magmaPos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.expectEntityWithData(
                stonePos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.waitAndRun(
                20L, () -> {
                    context.assertTrue(
                            magmaVillager.thermoo$getTemperature() > stoneVillager.thermoo$getTemperature(),
                            String.format(
                                    "Magma Villager temperature of %d is not greater than Stone Villager temperature of %d",
                                    magmaVillager.thermoo$getTemperature(),
                                    stoneVillager.thermoo$getTemperature()
                            )
                    );
                    context.complete();
                }
        );
    }

}

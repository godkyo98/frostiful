<p align="center">
    <img src="./src/main/resources/assets/frostiful/frostiful.png" alt="Frostiful title">
    A Frosty Vanilla+ Survival Experience
</p>

# Download Pages

CurseForge: https://www.curseforge.com/minecraft/mc-mods/frostiful

Modrinth: https://modrinth.com/mod/frostiful

The above download pages are the ONLY source of this mod, DO NOT TRUST ANY OTHER SOURCE!

# Wiki

Basically everything about this mod is documented extensively on the [wiki](https://github.com/TheDeathlyCow/frostiful/wiki/).

# Mod Integrations 

This documents mod integrations that have been created specifically for Frostiful and are included with Frostiful out of the box.

Many other patches that are also relevant for Scorchful are provided by the standalone [Thermoo Patches](https://modrinth.com/mod/thermoo-patches) mod, including season integration, heart bar fixes, and more.

* [Tips](https://modrinth.com/mod/tips): Added some Frostiful-specific tips
* [Enchantment Descriptions](https://modrinth.com/mod/enchantment-descriptions): Descriptions are provided for Frostiful's enchantments
* [Trinkets](https://modrinth.com/mod/trinkets): The Cloaks of Frostology can be equipped in the Trinkets Cape slot (also works with Accessories with the Trinkets compat layer) 
* [Farmer's Delight](https://modrinth.com/mod/farmers-delight-fabric): Hot Cocoa provides the Warmth effect
* [Farmer's Respite](https://www.curseforge.com/minecraft/mc-mods/farmers-respite): Teas and warm foods provide the Warmth effect
* [Festive Delight](https://www.curseforge.com/minecraft/mc-mods/festive-delight): Christmas Tea provides the Warmth effect
* [Frozen Up](https://www.curseforge.com/minecraft/mc-mods/frozen-up): Truffle Hot Chocolate provides the Warmth effect
* [Spectrum](https://modrinth.com/mod/spectrum): Hot Chocolate, Demon Tea, Restoration Tea, and Glistering Jelly Tea provide the Warmth effect

# Additional Credits

Thanks to everyone who has contributed to Frostiful, no matter how big or small! See the [Credits](./CREDITS.md) for the full list of contributors.

# LTS Policy

These are the current versions being supported by Frostiful.

| Minecraft Version | Support Status         |
|-------------------|------------------------|
| 1.21.4            | ✅ Supported            |
| 1.21.3            | ⚠️ Critical fixes only |
| 1.21.1            | ✅ Supported            | 
| 1.20.4            | ❌ Unsupported          | 
| 1.20.2            | ❌ Unsupported          | 
| 1.20.1            | ❌ Unsupported          |
| 1.19.4            | ❌ Unsupported          |
| 1.19.2            | ❌ Unsupported          | 

Status Definitions:

* ✅ Supported: This version is fully supported and will receive all new features, fixes, and updates (where possible)
* ⚠️ Critical fixes only: This version will receive only critical crash and security fixes, as well as minor features where they can be easily cherry-picked
* ❌ Unsupported: This version will receive no future updates, except for critical security fixes

# Build and Run

Frostiful is built using [Gradle](https://gradle.org/) using the [Fabric Loom Gradle plugin](https://github.com/FabricMC/fabric-loom).

```bash
# builds a production jar of Frostiful
./gradlew build 

# runs Frostiful's unit tests
./gradlew check

# runs Frostiful's game tests
./gradlew runGametest
```

## Updating structures

Frostiful has a custom run configuration for updating structure templates between Minecraft versions, to account for changes in datafixer format. To run this configuration, place your structure template `.nbt` files in `run/structure_update/structure` and then run the following command:

```bash
./gradlew runUpdateStructures
```

This will dump the updated NBT files into `run/structure_update/updated/structure`. Note that you will need to agree to Minecraft's [EULA](https://account.mojang.com/documents/minecraft_eula) for this to work.

> [!WARNING]
> This only works for vanilla stuff, so if needed you may have to temporarily and manually change the ID to the closest vanilla thing for the datafixes to apply, eg `frostiful:chillager` -> `minecraft:pillager`
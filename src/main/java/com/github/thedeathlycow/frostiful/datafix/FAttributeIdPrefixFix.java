package com.github.thedeathlycow.frostiful.datafix;

import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public final class FAttributeIdPrefixFix {
    private static final String PREFIX = "frostiful:generic.";

    public static String fixPrefixedAttributeIds(String id) {
        String normalizedID = IdentifierNormalizingSchema.normalize(id);

        String normalizedPrefix = IdentifierNormalizingSchema.normalize(PREFIX);
        if (normalizedID.startsWith(normalizedPrefix)) {
            return "frostiful:" + normalizedID.substring(normalizedPrefix.length());
        }

        return id;
    }

    private FAttributeIdPrefixFix() {

    }
}
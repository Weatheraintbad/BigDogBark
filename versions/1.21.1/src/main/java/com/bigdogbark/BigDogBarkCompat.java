package com.bigdogbark;

import net.minecraft.util.Identifier;

public final class BigDogBarkCompat {
    public static Identifier id(String path) {
        return Identifier.of(BigDogBarkClient.MOD_ID, path);
    }

    private BigDogBarkCompat() {
    }
}

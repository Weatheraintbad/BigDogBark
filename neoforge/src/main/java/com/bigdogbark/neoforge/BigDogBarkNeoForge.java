package com.bigdogbark.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BigDogBarkNeoForge.MOD_ID)
public final class BigDogBarkNeoForge {
    public static final String MOD_ID = "bigdogbark";

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);

    public static final ResourceLocation BIGDOG_ID = id("bigdog");
    public static final ResourceLocation BARK_ID = id("bark");

    public static final DeferredHolder<SoundEvent, SoundEvent> BIGDOG = SOUND_EVENTS.register(
            "bigdog",
            () -> SoundEvent.createVariableRangeEvent(BIGDOG_ID)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BARK = SOUND_EVENTS.register(
            "bark",
            () -> SoundEvent.createVariableRangeEvent(BARK_ID)
    );

    public BigDogBarkNeoForge(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);

        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.register(BigDogBarkNeoForgeClient.class);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}

package com.bigdogbark.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(BigDogBarkForge.MOD_ID)
public final class BigDogBarkForge {
    public static final String MOD_ID = "bigdogbark";

    public static final ResourceLocation BIGDOG_ID = id("bigdog");
    public static final ResourceLocation BARK_ID = id("bark");
    public static final SoundEvent BIGDOG = SoundEvent.createVariableRangeEvent(BIGDOG_ID);
    public static final SoundEvent BARK = SoundEvent.createVariableRangeEvent(BARK_ID);

    public BigDogBarkForge() {
        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.register(BigDogBarkForgeClient.class);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Sounds {
        @SubscribeEvent
        public static void register(RegisterEvent event) {
            event.register(ForgeRegistries.Keys.SOUND_EVENTS, BIGDOG_ID, () -> BIGDOG);
            event.register(ForgeRegistries.Keys.SOUND_EVENTS, BARK_ID, () -> BARK);
        }

        private Sounds() {
        }
    }
}

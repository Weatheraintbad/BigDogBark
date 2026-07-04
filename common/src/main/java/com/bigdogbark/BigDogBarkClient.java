package com.bigdogbark;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

import java.util.ArrayList;
import java.util.List;

public final class BigDogBarkClient implements ClientModInitializer {
    public static final String MOD_ID = "bigdogbark";

    public static final Identifier BIGDOG_ID = BigDogBarkCompat.id("bigdog");
    public static final Identifier BARK_ID = BigDogBarkCompat.id("bark");
    public static final SoundEvent BIGDOG = SoundEvent.of(BIGDOG_ID);
    public static final SoundEvent BARK = SoundEvent.of(BARK_ID);

    private static final int MIN_RELEASE_TICKS = 4;
    private static final int FULL_CHARGE_TICKS = 60;
    private static final float BIGDOG_SOUND_TICKS = 10.971429F;
    private static final int BIGDOG_OVERLAP_TICKS = 4;
    private static final int MIN_INTERVAL_TICKS = 1;
    private static final float START_CHARGE_PITCH = 1.0F;
    private static final float END_CHARGE_PITCH = 1.85F;

    private static final List<ChargingSoundInstance> chargingSounds = new ArrayList<>();
    private static int chargeTicks;
    private static int nextChargeSoundTick;
    private static boolean wasCharging;

    @Override
    public void onInitializeClient() {
        Registry.register(Registries.SOUND_EVENT, BIGDOG_ID, BIGDOG);
        Registry.register(Registries.SOUND_EVENT, BARK_ID, BARK);
    }

    public static void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;

        if (player == null || client.world == null) {
            stopCharging();
            return;
        }

        boolean charging = isChargingWeapon(player);

        if (charging) {
            chargeTicks++;
            chargingSounds.removeIf(ChargingSoundInstance::isDone);

            if (chargeTicks >= nextChargeSoundTick) {
                float progress = chargeProgress();
                float pitch = chargePitch(progress);
                ChargingSoundInstance chargingSound = new ChargingSoundInstance(player, pitch, chargeVolume(progress),
                        chargeSoundLifetime(pitch));
                chargingSounds.add(chargingSound);
                client.getSoundManager().play(chargingSound);

                nextChargeSoundTick = chargeTicks + chargeInterval(pitch);
            }
        } else {
            if (wasCharging && chargeTicks >= MIN_RELEASE_TICKS) {
                player.playSound(BARK, 1.0F, 1.0F);
            }

            stopCharging();
        }

        wasCharging = charging;
    }

    private static float chargeProgress() {
        return Math.min(1.0F, Math.max(0.0F, (chargeTicks - 1) / (float) FULL_CHARGE_TICKS));
    }

    private static float chargePitch(float progress) {
        float acceleratedProgress = progress * progress;
        return START_CHARGE_PITCH + (END_CHARGE_PITCH - START_CHARGE_PITCH) * acceleratedProgress;
    }

    private static float chargeVolume(float progress) {
        return 0.75F + 0.25F * progress;
    }

    private static int chargeInterval(float pitch) {
        return Math.max(MIN_INTERVAL_TICKS, Math.round(BIGDOG_SOUND_TICKS / pitch - BIGDOG_OVERLAP_TICKS));
    }

    private static int chargeSoundLifetime(float pitch) {
        return Math.max(1, Math.round(BIGDOG_SOUND_TICKS / pitch));
    }

    private static boolean isChargingWeapon(ClientPlayerEntity player) {
        if (!player.isUsingItem()) {
            return false;
        }

        ItemStack stack = player.getActiveItem();

        if (stack.isEmpty()) {
            return false;
        }

        UseAction action = stack.getUseAction();
        return action == UseAction.BOW
                || action == UseAction.SPEAR
                || stack.isOf(Items.BOW)
                || stack.isOf(Items.TRIDENT);
    }

    private static void stopCharging() {
        for (ChargingSoundInstance chargingSound : chargingSounds) {
            chargingSound.finish();
        }

        chargingSounds.clear();
        chargeTicks = 0;
        nextChargeSoundTick = 0;
        wasCharging = false;
    }

    public BigDogBarkClient() {
    }
}

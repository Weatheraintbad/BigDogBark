package com.bigdogbark.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public final class BigDogBarkForgeClient {
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

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        tick(Minecraft.getInstance());
    }

    private static void tick(Minecraft client) {
        LocalPlayer player = client.player;

        if (player == null || client.level == null) {
            stopCharging();
            return;
        }

        boolean charging = isChargingWeapon(player);

        if (charging) {
            chargeTicks++;
            chargingSounds.removeIf(ChargingSoundInstance::isStopped);

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
                player.playSound(BigDogBarkForge.BARK, 1.0F, 1.0F);
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

    private static boolean isChargingWeapon(LocalPlayer player) {
        if (!player.isUsingItem()) {
            return false;
        }

        ItemStack stack = player.getUseItem();

        if (stack.isEmpty()) {
            return false;
        }

        UseAnim action = stack.getUseAnimation();
        return action == UseAnim.BOW
                || action == UseAnim.SPEAR
                || stack.is(Items.BOW)
                || stack.is(Items.TRIDENT);
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

    private BigDogBarkForgeClient() {
    }
}

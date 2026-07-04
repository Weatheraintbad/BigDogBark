package com.bigdogbark.neoforge;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public final class ChargingSoundInstance extends AbstractTickableSoundInstance {
    private final int lifetimeTicks;
    private int age;

    public ChargingSoundInstance(LocalPlayer player, float pitch, float volume, int lifetimeTicks) {
        super(BigDogBarkNeoForge.BIGDOG.get(), SoundSource.PLAYERS, RandomSource.create());
        this.lifetimeTicks = lifetimeTicks;
        this.looping = false;
        this.delay = 0;
        this.volume = volume;
        this.pitch = pitch;
        updatePosition(player);
    }

    @Override
    public void tick() {
        age++;

        if (age >= lifetimeTicks) {
            finish();
        }
    }

    public void finish() {
        stop();
    }

    private void updatePosition(LocalPlayer player) {
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}

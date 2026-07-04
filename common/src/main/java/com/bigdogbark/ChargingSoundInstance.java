package com.bigdogbark;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.random.Random;

public final class ChargingSoundInstance extends MovingSoundInstance {
    private final int lifetimeTicks;
    private int age;

    public ChargingSoundInstance(ClientPlayerEntity player, float pitch, float volume, int lifetimeTicks) {
        super(BigDogBarkClient.BIGDOG, SoundCategory.PLAYERS, Random.create());
        this.lifetimeTicks = lifetimeTicks;
        this.repeat = false;
        this.repeatDelay = 0;
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
        setDone();
    }

    private void updatePosition(ClientPlayerEntity player) {
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}

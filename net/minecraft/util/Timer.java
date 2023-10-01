package net.minecraft.util;

import net.minecraft.client.Minecraft;

public class Timer
{
    /**
     * How many full ticks have turned over since the last call to updateTimer(), capped at 10.
     */
    public int elapsedTicks;
    public float field_194147_b;
    public float field_194148_c;

    /**
     * The time reported by the system clock at the last sync, in milliseconds
     */
    private long lastSyncSysClock;
    public float tickLength;

    public Timer(float tps)
    {
        this.tickLength = 1000.0F / tps;
        this.lastSyncSysClock = Minecraft.getSystemTime();
    }

    /**
     * Updates all fields of the Timer using the current time
     */
    public void updateTimer()
    {
        long i = Minecraft.getSystemTime();
        this.field_194148_c = (float)(i - this.lastSyncSysClock) / this.tickLength;
        this.lastSyncSysClock = i;
        this.field_194147_b += this.field_194148_c;
        this.elapsedTicks = (int)this.field_194147_b;
        this.field_194147_b -= (float)this.elapsedTicks;
    }
}

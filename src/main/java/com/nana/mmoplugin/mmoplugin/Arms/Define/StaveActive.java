package com.nana.mmoplugin.mmoplugin.Arms.Define;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class StaveActive extends BukkitRunnable {
    private LivingEntity caster;
    private Mmoplugin plugin;

    public StaveActive(LivingEntity caster, Mmoplugin plugin) {
        this.caster = caster;
        this.plugin = plugin;
    }

    public Mmoplugin getPlugin() {
        return plugin;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public abstract void Active();

    public void run() {
        Active();
    }

}

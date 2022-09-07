package com.nana.mmoplugin.mmoplugin.Arms.Staves.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class StaveActive extends BukkitRunnable {
    private LivingEntity caster;
    private MmoPlugin plugin;

    public StaveActive(LivingEntity caster, MmoPlugin plugin) {
        this.caster = caster;
        this.plugin = plugin;
    }

    public MmoPlugin getPlugin() {
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

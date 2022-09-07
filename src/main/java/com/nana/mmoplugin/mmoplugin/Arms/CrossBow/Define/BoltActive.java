package com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BoltActive extends BukkitRunnable {
    private MmoPlugin plugin;
    private LivingEntity shooter;

    public BoltActive(LivingEntity shooter, MmoPlugin plugin) {
        this.plugin = plugin;
        this.shooter = shooter;
    }

    public MmoPlugin getPlugin() {
        return plugin;
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public abstract void Active();

    @Override
    public void run() {
        Active();
    }
}

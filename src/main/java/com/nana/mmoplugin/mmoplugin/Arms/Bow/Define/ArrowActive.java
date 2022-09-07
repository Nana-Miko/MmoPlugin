package com.nana.mmoplugin.mmoplugin.Arms.Bow.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ArrowActive extends BukkitRunnable {
    private LivingEntity shooter;
    private Entity arrow;
    private MmoPlugin plugin;

    public abstract void Track(LivingEntity shooter,Entity arrow);

    public ArrowActive(LivingEntity shooter, Entity arrow, MmoPlugin plugin) {
        this.shooter = shooter;
        this.arrow = arrow;
        this.plugin = plugin;
    }

    public MmoPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void run(){
        Track(this.shooter,this.arrow);
    }

}

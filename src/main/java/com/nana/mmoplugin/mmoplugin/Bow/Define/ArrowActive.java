package com.nana.mmoplugin.mmoplugin.Bow.Define;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ArrowActive extends BukkitRunnable {
    private LivingEntity shooter;
    private Entity arrow;
    private Mmoplugin plugin;

    public abstract void Track(LivingEntity shooter,Entity arrow);

    public ArrowActive(LivingEntity shooter, Entity arrow, Mmoplugin plugin) {
        this.shooter = shooter;
        this.arrow = arrow;
        this.plugin = plugin;
    }

    public Mmoplugin getPlugin() {
        return plugin;
    }

    @Override
    public void run(){
        Track(this.shooter,this.arrow);
    }

}

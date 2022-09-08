package com.nana.mmoplugin.mmoplugin.util;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.util.Define.MmoUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncUtil implements MmoUtil {

    public static List<Entity> getNearbyEntitiesAsync(Entity entity, double length, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        List<Entity> entityList = new ArrayList<>();
        Callable callable = () -> entity.getNearbyEntities(length, length, length);
        Future future = bukkitScheduler.callSyncMethod(plugin, callable);
        try {
            entityList = (List<Entity>) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return entityList;

    }

    public static List<LivingEntity> getNearbyLivingEntitiesAsync(Entity entity, double length, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        List<Entity> entityList = new ArrayList<>();
        Callable callable = () -> entity.getNearbyEntities(length, length, length);
        Future future = bukkitScheduler.callSyncMethod(plugin, callable);
        try {
            entityList = (List<Entity>) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<LivingEntity> livingEntityList = new ArrayList<>();
        for (Entity en :
                entityList) {
            try {
                LivingEntity livingEntity = (LivingEntity) en;
                livingEntityList.add(livingEntity);
            }catch (ClassCastException exception){continue;}
        }

        return livingEntityList;

    }


    public static void teleportAsync(Entity entity0, Entity entity1, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> {
            entity0.teleport(entity1);
            return null;
        };
        bukkitScheduler.callSyncMethod(plugin, callable);
    }

    public static void teleportAsync(Entity entity0, Location location, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> {
            entity0.teleport(location);
            return null;
        };
        bukkitScheduler.callSyncMethod(plugin, callable);
    }

    public static void CallEventAsync(Event event, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> {
            plugin.getServer().getPluginManager().callEvent(event);
            return null;
        };
        bukkitScheduler.callSyncMethod(plugin, callable);
    }

    public static Entity SpawnEntityAsync(World world, Location location, EntityType entityType, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> world.spawnEntity(location, entityType);
        Future future = bukkitScheduler.callSyncMethod(plugin, callable);
        Entity entity = null;
        try {
            entity = (Entity) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static void EntityRemoveAsync(Entity entity, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> {
            entity.remove();
            return null;
        };
        bukkitScheduler.callSyncMethod(plugin, callable);
    }

    public static Arrow SpawnArrowAsync(World world, Location location, Vector direction, float speed,
                                        float spread, MmoPlugin plugin) {
        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        Callable callable = () -> world.spawnArrow(location, direction, speed, spread);
        Future future = bukkitScheduler.callSyncMethod(plugin, callable);
        Arrow arrow = null;
        try {
            arrow = (Arrow) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return arrow;
    }

}

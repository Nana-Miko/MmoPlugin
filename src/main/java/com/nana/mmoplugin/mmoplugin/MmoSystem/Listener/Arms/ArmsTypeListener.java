package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Arms;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsCatchType;
import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsType;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.util.Lock.CanLock;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmsTypeListener implements Listener, CanLock {

    private Mmoplugin plugin;
    private ClassLock user = null;

    public ArmsTypeListener(Mmoplugin plugin) {
        this.plugin = plugin;
        ArmsCheck();
    }

    // 监听装备在主手的事件
    @EventHandler(priority = EventPriority.LOWEST)
    public void DealEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());

        String lore = itemUtil.hasLore(itemStack, "[武器类型] ");
        if (lore == null) {
            return;
        }

        ArmsType armsType = null;

        for (ArmsType armsTypes :
                ArmsType.values()) {
            if (armsTypes.getName().equals(lore)) {
                if (CanCatchArm(player, armsTypes.getCatchType()) == false) {
                    player.sendMessage("装备双手武器需副手为空");
                    event.setCancelled(true);
                } else {
                    armsType = armsTypes;
                }
                break;
            }
        }

        if (event.isCancelled()) {
            return;
        }


        switch (armsType) {
            // case something
        }


    }


    private Boolean CanCatchArm(Player player, ArmsCatchType armsCatchType) {

        if (armsCatchType.equals(ArmsCatchType.SINGLE_HAND)) {
            return true;
        }
        ItemStack itemStack = player.getEquipment().getItemInOffHand();
        if (itemStack.getType().equals(Material.AIR)) {
            return true;
        } else {
            return false;
        }
    }

    private void ArmsCheck() {
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player :
                        plugin.getServer().getOnlinePlayers()) {
                    EntityEquipment equipment = player.getEquipment();
                    ItemStack itemStack = equipment.getItemInOffHand();
                    if (itemStack.getType().equals(Material.AIR)) {
                        continue;
                    }
                    String lore = itemUtil.hasLore(itemStack, "[武器类型] ");
                    if (lore == null) {
                        continue;
                    }
                    for (ArmsType armsTypes :
                            ArmsType.values()) {
                        if (armsTypes.getName().equals(lore)) {
                            if (armsTypes.getCatchType().equals(ArmsCatchType.BOTH_HAND)) {
                                PlayerInventory inventory = player.getInventory();
                                player.sendMessage("双手武器不允许装备在副手");
                                inventory.clear(40);
                                inventory.addItem(itemStack);

                            }
                            break;
                        }
                    }

                }
            }
        };
        task.runTaskTimerAsynchronously(plugin, 1, 5);
    }

    @Override
    public void setUser(ClassLock locker) {
        user = locker;
    }

    @Override
    public ClassLock getUser() {
        return user;
    }
}

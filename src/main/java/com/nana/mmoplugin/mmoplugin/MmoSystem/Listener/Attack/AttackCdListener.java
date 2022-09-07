package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsType;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AttackCdListener extends MmoListener {

    private Map<Player, Long> LastAttack = new HashMap<>();

    public AttackCdListener(MmoPlugin plugin) {
        super(plugin);
    }

    private Boolean CanAttack(Player player) {
        if (!LastAttack.containsKey(player)) {
            return true;
        }
        ItemStack arms = player.getEquipment().getItemInMainHand();
        String lore = itemUtil.hasLore(arms, "[武器类型] ");
        if (lore == null) {
            return true;
        }
        ArmsType armsType = null;
        for (ArmsType armType :
                ArmsType.values()) {
            if (armType.getName().equals(lore)) {
                armsType = armType;
                break;
            }
        }
        if (System.currentTimeMillis() - LastAttack.get(player) > armsType.getAttackCd()) {
            return true;
        } else {
            return false;
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void DealEvent(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (!entity.getType().equals(EntityType.PLAYER)) {
            return;
        }

        Player player = (Player) entity;
        if (CanAttack(player) == true) {
            LastAttack.put(player, System.currentTimeMillis());
        } else {
            event.setCancelled(true);
        }


    }
}

package com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Listener;

import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define.BoltActive;
import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define.BoltType;
import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Event.CrossBowAttackEvent;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.util.MmoComponent;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CrossBowAttackListener extends MmoListener {

    private Map<Player, Long> cdMap = new HashMap<>();


    @EventHandler
    public void DealEvent(CrossBowAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Set<BoltType> boltTypeSet = event.getStaveTypeSet();

        Boolean hasAmmo = false;

        for (BoltType boltType :
                boltTypeSet) {
            if (cdMap.containsKey(player)) {
                if (System.currentTimeMillis() - cdMap.get(player) <= boltType.getCd()) {
                    return;
                }
            }
            if (!boltType.hasAmmo(player)) {
                continue;
            }
            Object object = null;
            try {
                object = boltType.getClazz().getDeclaredConstructor(LivingEntity.class, MmoPlugin.class).newInstance(player, getPlugin());
                BoltActive boltActive = (BoltActive) object;
                boltActive.runTaskAsynchronously(getPlugin());
                boltType.useAmmo(player);
                cdMap.put(player, System.currentTimeMillis());
                hasAmmo = true;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (hasAmmo == false) {
            MmoComponent mmoComponent = new MmoComponent("未装备对应类型的" + ChatColor.RED + "弩箭");
            mmoComponent.showText(player, ChatMessageType.ACTION_BAR);
        }


    }
}

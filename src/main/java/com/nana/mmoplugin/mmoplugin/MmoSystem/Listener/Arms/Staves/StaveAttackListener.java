package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Arms.Staves;

import com.nana.mmoplugin.mmoplugin.Arms.Define.StaveActive;
import com.nana.mmoplugin.mmoplugin.Arms.Define.StaveType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Arms.Stave.StaveAttackEvent;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class StaveAttackListener implements Listener {
    private Mmoplugin plugin;
    private Map<Player, Long> cdMap = new HashMap<>();

    public StaveAttackListener(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DealEvent(StaveAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        StaveType staveType = event.getStaveType();

        Object object = null;
        try {
            object = staveType.getClazz().getDeclaredConstructor(LivingEntity.class, Mmoplugin.class).newInstance(player, plugin);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (cdMap.containsKey(player)) {
            if (System.currentTimeMillis() - cdMap.get(player) <= staveType.getCd()) {
                return;
            }
        }

        StaveActive staveActive = (StaveActive) object;
        staveActive.runTaskAsynchronously(plugin);
        cdMap.put(player, System.currentTimeMillis());


    }


}


package com.nana.mmoplugin.mmoplugin.Arms.Staves.Listener;

import com.nana.mmoplugin.mmoplugin.Arms.Staves.Define.MagicType;
import com.nana.mmoplugin.mmoplugin.Arms.Staves.Define.StaveActive;
import com.nana.mmoplugin.mmoplugin.Arms.Staves.Event.StaveAttackEvent;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class StaveAttackListener extends MmoListener {
    private Map<Player, Long> cdMap = new HashMap<>();

    public StaveAttackListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(StaveAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        MagicType magicType = event.getStaveType();

        Object object = null;
        try {
            object = magicType.getClazz().getDeclaredConstructor(LivingEntity.class, MmoPlugin.class).newInstance(player, getPlugin());
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
            if (System.currentTimeMillis() - cdMap.get(player) <= magicType.getCd()) {
                return;
            }
        }

        StaveActive staveActive = (StaveActive) object;
        staveActive.runTaskAsynchronously(getPlugin());
        cdMap.put(player, System.currentTimeMillis());


    }


}


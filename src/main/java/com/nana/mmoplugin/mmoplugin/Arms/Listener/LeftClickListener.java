package com.nana.mmoplugin.mmoplugin.Arms.Listener;

import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Event.CrossBowAttackEvent;
import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsType;
import com.nana.mmoplugin.mmoplugin.Arms.Staves.Event.StaveAttackEvent;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LeftClickListener extends MmoListener {

    public LeftClickListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            return;
        }
        Player player = event.getPlayer();

        ItemStack arms = player.getEquipment().getItemInMainHand();
        String lore = itemUtil.hasLore(arms, "[武器类型] ");
        if (lore == null) {
            return;
        }
        ArmsType armsType = null;
        for (ArmsType armType :
                ArmsType.values()) {
            if (armType.getName().equals(lore)) {
                armsType = armType;
                break;
            }
        }

        switch (armsType) {
            case STAVE:
                getPlugin().getServer().getPluginManager().callEvent(new StaveAttackEvent(player));
                break;
            case CROSSBOW:
                getPlugin().getServer().getPluginManager().callEvent(new CrossBowAttackEvent(player));
        }


    }

}

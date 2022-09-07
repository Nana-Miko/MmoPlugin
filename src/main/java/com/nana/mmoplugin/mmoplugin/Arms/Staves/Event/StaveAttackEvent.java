package com.nana.mmoplugin.mmoplugin.Arms.Staves.Event;

import com.nana.mmoplugin.mmoplugin.Arms.Staves.Define.StaveType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Define.MmoEvent;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaveAttackEvent extends MmoEvent {


    private Player player;
    private StaveType staveType = null;


    public StaveAttackEvent(Player player) {
        this.player = player;
        StaveType();
    }

    public Player getPlayer() {
        return player;
    }

    public StaveType getStaveType() {
        return staveType;
    }

    private void StaveType() {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();

        String lore = itemUtil.hasLore(itemStack, "[法术类型] ");
        if (lore == null) {
            this.setCancelled(true);
            return;
        }

        StaveType staveType = null;
        for (StaveType stavesType :
                StaveType.values()) {
            if (stavesType.getName().equals(lore)) {
                staveType = stavesType;
                break;
            }
        }
        this.staveType = staveType;


    }
}

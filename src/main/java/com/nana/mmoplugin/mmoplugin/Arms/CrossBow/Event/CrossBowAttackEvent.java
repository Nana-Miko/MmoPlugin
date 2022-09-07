package com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Event;

import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define.BoltType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Define.MmoEvent;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CrossBowAttackEvent extends MmoEvent {
    private Player player;
    private Set<BoltType> boltTypeSet = new HashSet<>();

    public CrossBowAttackEvent(Player player) {
        this.player = player;
        BoltType();
    }

    public Player getPlayer() {
        return player;
    }

    public Set<BoltType> getStaveTypeSet() {
        return boltTypeSet;
    }

    private void BoltType() {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();

        Set<String> loreSet = new HashSet<>();
        loreSet = itemUtil.hasLore(itemStack, "[弩箭类型] ", loreSet);


        if (loreSet == null || loreSet.isEmpty()) {
            this.setCancelled(true);
            return;
        }

        for (String lore :
                loreSet) {
            for (BoltType boltType :
                    BoltType.values()) {
                if (boltType.getName().equals(lore)) {
                    this.boltTypeSet.add(boltType);
                    break;
                }
            }
        }


    }
}

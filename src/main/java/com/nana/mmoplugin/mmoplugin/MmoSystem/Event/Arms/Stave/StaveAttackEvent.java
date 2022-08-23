package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Arms.Stave;

import com.nana.mmoplugin.mmoplugin.Arms.Define.StaveType;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class StaveAttackEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private Boolean Cancelled = false;
    private Player player;
    private StaveType staveType = null;

    @Override
    public boolean isCancelled() {
        return Cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.Cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

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

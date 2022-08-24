package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface Skill extends Listener {

    Boolean skillRun();

    void setArmsRouse(ItemStack itemStack);

    ArmsRouse getArmsRouse();


}

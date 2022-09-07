package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import org.bukkit.inventory.ItemStack;

public interface Skill {

    Boolean skillRun();

    void setArmsRouse(ItemStack itemStack);

    ArmsRouse getArmsRouse();

    Boolean skillRunZeroStar();

    Boolean skillRunOneStar();

    Boolean skillRunTwoStar();

    Boolean skillRunThreeStar();

    Boolean skillRunFourStar();

    Boolean skillRunFiveStar();


}

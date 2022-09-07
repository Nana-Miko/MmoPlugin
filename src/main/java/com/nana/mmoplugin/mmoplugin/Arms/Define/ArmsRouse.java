package com.nana.mmoplugin.mmoplugin.Arms.Define;

import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.inventory.ItemStack;

public enum ArmsRouse {
    ZERO_STAR,
    ONE_STAR,
    TWO_STAR,
    THREE_STAR,
    FOUR_STAR,
    FIVE_STAR,
    ;

    public static ArmsRouse getArmsRouse(ItemStack itemStack) {
        String lore = itemUtil.hasLore(itemStack, "[觉醒] ");
        ArmsRouse armsRouse = ArmsRouse.ZERO_STAR;
        if (lore == null) {
            return armsRouse;
        }
        switch (lore) {
            case "☆☆☆☆☆":
                armsRouse = ZERO_STAR;
                break;
            case "★☆☆☆☆":
                armsRouse = ONE_STAR;
                break;
            case "★★☆☆☆":
                armsRouse = TWO_STAR;
                break;
            case "★★★☆☆":
                armsRouse = THREE_STAR;
                break;
            case "★★★★☆":
                armsRouse = FOUR_STAR;
                break;
            case "★★★★★":
                armsRouse = FIVE_STAR;
                break;
        }
        return armsRouse;
    }
}

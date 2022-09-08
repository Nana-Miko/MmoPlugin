package com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define;

import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Bolt.BreakArmour;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public enum BoltType implements MmoAttributeType {
    BREAK_ARMOUR("破甲", 1.0, BreakArmour.class),
    ;

    private String name;
    private Class<? extends BoltActive> clazz;
    private Long cd;

    BoltType(String name, Double cd, Class<? extends BoltActive> clazz) {
        cd = cd * 1000;
        this.name = name;
        this.cd = cd.longValue();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Long getCd() {
        return cd;
    }

    public Class<? extends BoltActive> getClazz() {
        return clazz;
    }

    public Boolean hasAmmo(LivingEntity livingEntity) {
        ItemStack offItemStack = livingEntity.getEquipment().getItemInOffHand();

        String lore = itemUtil.hasLore(offItemStack, "[弩箭] ");
        if (lore == null) {
            return false;
        }

        if (lore.equals(this.getName() + "箭")) {
            return true;
        } else {
            return false;
        }

    }

    public void useAmmo(LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getEquipment().getItemInOffHand();
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    @Override
    public String getTypeName() {
        return "弩箭类型";
    }
}

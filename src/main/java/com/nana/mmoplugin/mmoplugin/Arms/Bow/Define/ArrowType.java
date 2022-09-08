package com.nana.mmoplugin.mmoplugin.Arms.Bow.Define;


import com.nana.mmoplugin.mmoplugin.Arms.Bow.Arrow.SonicArrow;
import com.nana.mmoplugin.mmoplugin.Arms.Bow.Arrow.SpiltArrow;
import com.nana.mmoplugin.mmoplugin.Arms.Bow.Arrow.TrackingArrow;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public enum ArrowType implements MmoAttributeType {
    TRACKING("跟踪", TrackingArrow.class),
    SPILT("分裂", SpiltArrow.class),
    SONIC("音爆", SonicArrow.class),
    ;

    private String name;
    private Class<? extends ArrowActive> clazz;
    private String ammo;

    ArrowType(String name, Class<? extends ArrowActive> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<? extends ArrowActive> getClazz() {
        return clazz;
    }

    public Boolean hasAmmo(LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getEquipment().getItemInOffHand();

        Set<String> loreSet = new HashSet<>();
        loreSet = itemUtil.hasLore(itemStack, "[箭矢] ", loreSet);
        if (loreSet == null) {
            return false;
        }

        if (loreSet.contains(this.getName() + "箭")) {
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
        return "箭矢类型";
    }
}

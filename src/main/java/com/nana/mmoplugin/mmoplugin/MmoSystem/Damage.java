package com.nana.mmoplugin.mmoplugin.MmoSystem;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.Set;

public class Damage {

    private LivingEntity Attacker;
    private LivingEntity Attacked;
    private Double Multiplier;
    private Mmoplugin plugin;
    private Double Damage = null;
    private Set<DamageType> damageType = new HashSet<>();

    public Damage(LivingEntity attacker, LivingEntity attacked, Double multiplier, Mmoplugin plugin) {
        Attacker = attacker;
        Attacked = attacked;
        Multiplier = multiplier;
        this.plugin = plugin;
    }

    public void setDamage(Double damage) {
        Damage = damage;
    }

    private Set<DamageType> getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType.add(damageType);
    }


    private void analysisDamageType(){
        // 若有指定的伤害类型，则不分析装备伤害类型
        if (!getDamageType().isEmpty()){return;}

        EntityEquipment attackerEntityEquipment = Attacker.getEquipment();
        ItemStack itemStack = attackerEntityEquipment.getItemInMainHand();
        if (itemStack==null){damageType.add(DamageType.NORMAL);return;}
        if (!itemStack.hasItemMeta()){damageType.add(DamageType.NORMAL);return;}


        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()){damageType.add(DamageType.NORMAL);return;}

        Set<String> DamageTypeNameSet = new HashSet<>();

        DamageTypeNameSet = itemUtil.hasLore(itemStack,"[质变] ",DamageTypeNameSet);
        if(DamageTypeNameSet==null){DamageTypeNameSet = new HashSet<>();DamageTypeNameSet.add("普通");}

        for (DamageType damageType :
                DamageType.values()) {
            if(DamageTypeNameSet.contains(damageType.getDamageTypeName())){
                this.damageType.add(damageType);
            }
        }

        return;


    }

    public void attack(){
        // 分析伤害类型
        analysisDamageType();


        for (DamageType damageType :
                this.damageType) {
            if (Damage==null) {
                DamageSystem.attack(Attacker, Attacked, Multiplier, damageType, plugin);
            }
            else {DamageSystem.attack(Attacker,Attacked,Multiplier,damageType,Damage,plugin);}
        }

    }
}

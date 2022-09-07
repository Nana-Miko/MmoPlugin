package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;


import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.NormalDamageEvent;
import com.nana.mmoplugin.mmoplugin.util.DodgeUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;

public class NormalAttackListener extends AttackListener {

    public NormalAttackListener(MmoPlugin plugin) {
        super(plugin);
    }

    @Override
    public void DealEvent(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        return;
    }

    @EventHandler
    public void DealEvent(NormalDamageEvent event){

        LivingEntity Attacker = event.getAttacker();
        LivingEntity Attacked = event.getAttacked();
        Double Damage = event.getDamage();
        Double Multiplier = event.getMultiplier();

        if (DodgeUtil.IsDodgeIng(Attacked.getUniqueId(),getPlugin())){
            Attacked.sendMessage("完美闪避！");
            if (Attacker.getType().equals(EntityType.PLAYER)){
                Attacker.sendMessage("你的攻击已被躲开");
            }
            DodgeUtil.DodgeEffect(Attacked);
            return;
        }


        AttributeInstance attackedArmor = Attacked.getAttribute(Attribute.GENERIC_ARMOR);//获取被攻击者的防御值
        double Armor = attackedArmor.getValue();
        Collection<AttributeModifier> attributeModifier_armor = attackedArmor.getModifiers();
        for (AttributeModifier x :
                attributeModifier_armor) {
            if (x.getName().equals("generic.armor")) {
                Armor = x.getAmount();
                break;
            }
        }

        Double sucking;

        sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击
        Damage = DamageSystem.BlockDamage(Attacked,Damage);// 计算格挡

        Double finalDamage = (Damage * (1 + Multiplier)) - Armor;

        DamageSystem.Hurt(Attacker,Attacked,finalDamage,sucking);

    }
}

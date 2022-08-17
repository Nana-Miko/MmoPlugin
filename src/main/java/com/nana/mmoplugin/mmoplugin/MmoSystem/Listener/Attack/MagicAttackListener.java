package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageType;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.MagicDamageEvent;
import com.nana.mmoplugin.mmoplugin.util.DodgeUtil;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MagicAttackListener extends AttackListener {
    public MagicAttackListener(Mmoplugin plugin) {
        super(plugin);
    }

    @Override
    public void DealEvent(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        return;
    }

    @EventHandler
    public void DealEvent(MagicDamageEvent event){
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

        ItemStack[] itemStacks = Attacked.getEquipment().getArmorContents();

        Double magicArmor = 0.0;

        for (ItemStack itemStack :
                itemStacks) {
            String armor = itemUtil.hasLore(itemStack, DamageType.MAGIC.getDamageArmorName());
            if(armor!=null){
                Double temp = new Double(armor);
                magicArmor = magicArmor + temp;
            }
        }

        Double sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击


        Double finalDamage = (Damage * (1 + Multiplier)) - magicArmor;

        DamageSystem.Hurt(Attacker,Attacked,finalDamage,sucking);

        return;
    }
}

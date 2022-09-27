package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;


import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageScore;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.ScoreDamageTypeString;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.ArmoredAttack;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.NormalDamageEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.util.DodgeUtil;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collection;

public class NormalAttackListener extends MmoListener implements ArmoredAttack, PlayerStorageListener {
    private ClassLock user = null;



    @EventHandler
    public void DealEvent(NormalDamageEvent event) {

        LivingEntity Attacker = event.getAttacker();
        LivingEntity Attacked = event.getAttacked();
        Double Damage = event.getDamage();
        Double Multiplier = event.getMultiplier();

        if (DodgeUtil.IsDodgeIng(Attacked.getUniqueId(), getPlugin())) {
            Attacked.sendMessage("完美闪避！");
            if (Attacker.getType().equals(EntityType.PLAYER)) {
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


        Double criticalDamage = Damage; // 复制一个damage的副本
        Double sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击
        criticalDamage = Damage - criticalDamage; // 得出暴击的增伤
        Double finalDamage = DamageSystem.BlockDamage(Attacked, Damage);// 计算格挡

        finalDamage = CountFinalDamage(finalDamage, Multiplier, Armor); // 计算对应护甲减免

        if (Attacker.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) Attacker;
            if (getPlugin().getDamageScoreBoardManager().hasDamageScoreBoard(player)) {
                DamageScore damageScore = getPlugin().getDamageScoreBoardManager().getDamageScore(player);
                damageScore.addAttributeValue(criticalDamage, ScoreDamageTypeString.CRITICAL);
                damageScore.addAttributeValue(CountFinalDamage(Damage, Multiplier, 0.0) - finalDamage, ScoreDamageTypeString.BLOCKED);
            }
        }


        DamageSystem.Hurt(Attacker, Attacked, finalDamage, sucking, DamageType.NORMAL, getPlugin());

    }

    @Override
    public Boolean unregisterPlayer(Player player) {
        return false;
    }

    @Override
    public Double CountFinalDamage(Double damage, Double multiplier, Double armor) {
        return (damage * (1 + multiplier)) - armor;
    }

    @Override
    public void setUser(ClassLock locker) {
        user = locker;
    }

    @Override
    public ClassLock getUser() {
        return user;
    }
}

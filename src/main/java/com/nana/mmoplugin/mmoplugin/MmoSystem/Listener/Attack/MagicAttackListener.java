package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageScore;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.ScoreDamageTypeString;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.ArmoredAttack;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.MagicDamageEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.util.DodgeUtil;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class MagicAttackListener extends MmoListener implements ArmoredAttack, PlayerStorageListener {
    private ClassLock user = null;



    @EventHandler
    public void DealEvent(MagicDamageEvent event) {
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

        Double criticalDamage = Damage; // 复制一个damage的副本
        Double sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击
        criticalDamage = Damage - criticalDamage; // 得出暴击的增伤

        Double finalDamage = CountFinalDamage(Damage, Multiplier, magicArmor); // 计算对应护甲减免

        if (Attacker.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) Attacker;
            if (getPlugin().getDamageScoreBoardManager().hasDamageScoreBoard(player)) {
                DamageScore damageScore = getPlugin().getDamageScoreBoardManager().getDamageScore(player);
                damageScore.addAttributeValue(criticalDamage, ScoreDamageTypeString.CRITICAL);
                damageScore.addAttributeValue(CountFinalDamage(Damage, Multiplier, 0.0) - finalDamage, ScoreDamageTypeString.BLOCKED);
            }
        }


        DamageSystem.Hurt(Attacker, Attacked, finalDamage, sucking, DamageType.MAGIC, getPlugin());

        return;
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

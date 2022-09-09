package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageScore;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.ScoreDamageTypeString;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.ArmoredAttack;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.CuttingDamageEvent;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CuttingAttackListener extends MmoListener implements ArmoredAttack, PlayerStorageListener {
    private Map<LivingEntity, Double> bleedingValue;
    private ClassLock user = null;

    public CuttingAttackListener(MmoPlugin plugin) {
        super(plugin);
        bleedingValue = new HashMap<>();
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                BleedingCount();
            }
        };
        task.runTaskAsynchronously(this.getPlugin());
    }


    @EventHandler
    public void DealEvent(CuttingDamageEvent event){
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

        Double cuttingArmor = 0.0;

        for (ItemStack itemStack :
                itemStacks) {
            String armor = itemUtil.hasLore(itemStack, DamageType.CUTTING.getDamageArmorName());
            if (armor != null) {
                Double temp = new Double(armor);
                cuttingArmor = cuttingArmor + temp;
            }
        }

        Double criticalDamage = Damage; // 复制一个damage的副本
        Double sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击
        criticalDamage = Damage - criticalDamage; // 得出暴击的增伤
        Double finalDamage = DamageSystem.BlockDamage(Attacked, Damage);// 计算格挡

        finalDamage = CountFinalDamage(finalDamage, Multiplier, cuttingArmor); // 计算对应护甲减免和技能倍率

        //System.out.println(Damage+" "+finalDamage);

        if (Attacker.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) Attacker;
            if (getPlugin().getDamageScoreBoardManager().hasDamageScoreBoard(player)) {
                DamageScore damageScore = getPlugin().getDamageScoreBoardManager().getDamageScore(player);
                damageScore.addAttributeValue(criticalDamage, ScoreDamageTypeString.CRITICAL);
                damageScore.addAttributeValue(CountFinalDamage(Damage, Multiplier, 0.0) - finalDamage, ScoreDamageTypeString.BLOCKED);
            }
        }

        DamageSystem.Hurt(Attacker, Attacked, finalDamage, sucking, DamageType.CUTTING, getPlugin()); //造成伤害

        Double bleedingAddValue = finalDamage;

        if (!bleedingValue.containsKey(Attacked)) {
            bleedingValue.put(Attacked, 0.0);
        }

        if (bleedingValue.get(Attacked) + bleedingAddValue >= 100) {
            Bleeding(Attacker, Attacked, sucking);
            bleedingValue.put(Attacked, 0.0);
        } else {
            bleedingValue.put(Attacked, bleedingValue.get(Attacked) + bleedingAddValue);
        }


        return;
    }

    private void BleedingCount(){
        int Count = 0;
        while (true){
            Iterator<Map.Entry<LivingEntity, Double>> iterator = bleedingValue.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<LivingEntity, Double> entry = iterator.next();
                if(entry.getValue()<=0){continue;}
                Double temp = entry.getValue() - 0.1;
                if(temp<0){iterator.remove();}
                else{bleedingValue.put(entry.getKey(),temp);}
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Count = Count+10;
            }
        }
    }

    private void Bleeding(LivingEntity Attacker, LivingEntity Attacked, Double sucking) {

        Double bleedingDamage = Attacked.getMaxHealth() * 0.08;
        DamageSystem.Hurt(Attacker, Attacked, bleedingDamage, sucking, DamageType.CUTTING, getPlugin());


        // 一些出血特效
        Attacker.sendMessage("你造成了出血！");
    }

    @Override
    public Boolean unregisterPlayer(Player player) {
        if (bleedingValue.containsKey(player)) {
            bleedingValue.remove(player);
            return true;
        }
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

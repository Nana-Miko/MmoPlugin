package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageSystem;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.CuttingDamageEvent;
import com.nana.mmoplugin.mmoplugin.util.DodgeUtil;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CuttingAttackListener extends AttackListener {
    private Map<LivingEntity, Double> bleedingValue;

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

    @Override
    public void DealEvent(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        return;
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
            if(armor!=null){
                Double temp = new Double(armor);
                cuttingArmor = cuttingArmor + temp;
            }
        }

        Double sucking = DamageSystem.SuckingBlood(Attacker);// 计算吸血
        Damage = DamageSystem.CriticalDamage(Attacker, Damage);// 计算暴击
        Damage = DamageSystem.BlockDamage(Attacked,Damage);// 计算格挡


        Double finalDamage = (Damage * (1 + Multiplier)) - cuttingArmor;

        DamageSystem.Hurt(Attacker,Attacked,finalDamage,sucking);

        Double bleedingAddValue = finalDamage;

        if (!bleedingValue.containsKey(Attacked)){bleedingValue.put(Attacked,0.0);}

        if (bleedingValue.get(Attacked) + bleedingAddValue >=100){
            Bleeding(Attacker,Attacked,sucking);
            bleedingValue.put(Attacked,0.0);
        }
        else {
            bleedingValue.put(Attacked,bleedingValue.get(Attacked)+bleedingAddValue);
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

    private void Bleeding(LivingEntity Attacker,LivingEntity Attacked,Double sucking){

        Double bleedingDamage = Attacked.getMaxHealth()*0.08;
        DamageSystem.Hurt(Attacker,Attacked,bleedingDamage,sucking);


        // 一些出血特效
        Attacker.sendMessage("你造成了出血！");
    }
}

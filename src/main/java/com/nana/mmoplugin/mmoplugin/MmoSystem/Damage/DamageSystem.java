package com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.CuttingDamageEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.MagicDamageEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.NormalDamageEvent;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DamageSystem {

    public static void attack(LivingEntity Attacker, LivingEntity Attacked, Double Multiplier, DamageType damageType, MmoPlugin plugin) {
        AttributeInstance attackerDamage = Attacker.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);//获取攻击者的攻击力
        double Damage = attackerDamage.getValue();
        Collection<AttributeModifier> attributeModifier_attack = attackerDamage.getModifiers();
        for (AttributeModifier x :
                attributeModifier_attack) {
            if (x.getName().equals("generic.attackDamage")) {
                Damage = x.getAmount();
                break;
            }

        }


        if (Attacked.isInvulnerable()) {
            return ;
        }


        switch (damageType) {
            case NORMAL: {
                AsyncUtil.CallEventAsync(new NormalDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case MAGIC: {
                AsyncUtil.CallEventAsync(new MagicDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case CUTTING: {
                AsyncUtil.CallEventAsync(new CuttingDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
        }


    }

    public static void attack(LivingEntity Attacker, LivingEntity Attacked, Double Multiplier, Double PanelDamagePercentage, DamageType damageType, MmoPlugin plugin) {
        AttributeInstance attackerDamage = Attacker.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);//获取攻击者的攻击力
        double Damage = attackerDamage.getValue();
        Damage = Damage * PanelDamagePercentage;
        Collection<AttributeModifier> attributeModifier_attack = attackerDamage.getModifiers();
        for (AttributeModifier x :
                attributeModifier_attack) {
            if (x.getName().equals("generic.attackDamage")) {
                Damage = x.getAmount();
                break;
            }

        }


        if (Attacked.isInvulnerable()) {
            return;
        }


        switch (damageType) {
            case NORMAL: {
                AsyncUtil.CallEventAsync(new NormalDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case MAGIC: {
                AsyncUtil.CallEventAsync(new MagicDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case CUTTING: {
                AsyncUtil.CallEventAsync(new CuttingDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
        }


    }

    public static void attack(LivingEntity Attacker, LivingEntity Attacked, Double Multiplier, DamageType damageType, Double Damage, MmoPlugin plugin) {

        if (Attacked.isInvulnerable()) {
            return;
        }


        switch (damageType) {
            case NORMAL: {
                AsyncUtil.CallEventAsync(new NormalDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case MAGIC: {
                AsyncUtil.CallEventAsync(new MagicDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
            case CUTTING: {
                AsyncUtil.CallEventAsync(new CuttingDamageEvent(Attacker, Attacked, Damage, Multiplier), plugin);
                break;
            }
        }



    }


    // 吸血
    @NotNull
    public static Double addHealth(LivingEntity Attacker, Double sucking, double finalDamage, MmoPlugin plugin) {
        if (sucking != (double) 0) {
            double Blood = finalDamage * sucking * 0.01;
            double Health = Attacker.getHealth();
            if (Health + Blood > Attacker.getMaxHealth()) {
                Attacker.setHealth(Attacker.getMaxHealth());
            } else {
                Attacker.setHealth(Health + Blood);
            }
            if (Attacker.getType().equals(EntityType.PLAYER)) {
                //Attacker.sendMessage("你已吸取 " + Blood + " 点生命值");
                Player player = (Player) Attacker;
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 3);
                if (plugin.getDamageScoreBoard().hasDamageScoreBoard(player)) {
                    plugin.getDamageScoreBoard().getDamageScore(player).addAttributeValue(Blood, ScoreDamageTypeString.SUCKED_BLEED);
                }


            }
        }

        return finalDamage;
    }

    // 吸血判定
    public static Double SuckingBlood(LivingEntity livingEntity){
        List<String> loreList = new ArrayList<>();
        Double sucking = (double) 0;
        String lore = itemUtil.hasLore(livingEntity.getEquipment().getItemInMainHand(),"生命偷取 + ");
        if (lore!=null){
            Double temp = new Double(lore);
            sucking = temp;
        }

        return sucking;
    }



    //格挡判定
    public static Double BlockDamage (LivingEntity livingEntity , Double damage)
    {
        List<String> loreList = new ArrayList<>();
        Double Block = (double) 0;
        Double finalDamage = damage;
        String lore = itemUtil.hasLore(livingEntity.getEquipment().getItemInMainHand(),"格挡 + ");
        if (lore!=null){
            Double temp = new Double(lore);
            Block = temp;
        }


        int randomInt =  RandomUtils.nextInt(101);
        if(randomInt <= Block) {
            finalDamage = damage - (damage * 0.5);
            if (livingEntity.getType() == EntityType.PLAYER) {
                livingEntity.sendMessage("已格挡");
            }


            // 打铁音效
            livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 15);
            livingEntity.getWorld().spawnParticle(Particle.CRIT, livingEntity.getLocation().add(0, 1, 0), 3);


        }
        return finalDamage;
    }


    //暴击判定
    public static Double CriticalDamage(LivingEntity livingEntity,Double damage){
        List<String> loreList = new ArrayList<>();
        Double Critical = (double) 0;
        Double finalDamage = damage;

        String lore = itemUtil.hasLore(livingEntity.getEquipment().getItemInMainHand(),"暴击 + ");
        if (lore!=null){
            Double temp = new Double(lore);
            Critical = temp;
        }


        int randomInt =  RandomUtils.nextInt(101);
        if(randomInt <= Critical){
            finalDamage = damage + (damage*0.5);
            livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);

            //Attacked.getWorld().playEffect(Attacked.getLocation().add(0,1,0),Effect.CRIT,5);

        }


        return finalDamage;
    }

    //造成伤害
    public static void Hurt(LivingEntity Attacker, LivingEntity Attacked, Double damage, Double sucking, DamageType damageType, MmoPlugin plugin) {
        // 根据计算后，修改血量，同时播放受击效果
        Double attackedHealth = Attacked.getHealth();
        Double attackedMaxHealth = Attacked.getMaxHealth();
        Double surplusHealth = attackedHealth - damage;
        try {
            if (surplusHealth > 0 && surplusHealth <= attackedMaxHealth) {
                Attacked.setHealth(surplusHealth);
                Attacked.playEffect(EntityEffect.HURT);
                Attacked.getWorld().playSound(Attacked.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 3);
            } else if (surplusHealth > attackedMaxHealth) {
                Attacked.setHealth(attackedMaxHealth);
                Attacked.playEffect(EntityEffect.HURT);
                Attacked.getWorld().playSound(Attacked.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 3);
            } else if (surplusHealth < 0) {
                Attacked.setHealth(0);
                Attacked.getWorld().playSound(Attacked.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 3);
            }


        } catch (IllegalStateException e) {
        }

        addHealth(Attacker, sucking, damage, plugin);

        if (Attacker.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) Attacker;
            //player.sendMessage("你本次造成了 " + damage + " 点伤害");
            DamageScoreBoard damageScoreBoard = plugin.getDamageScoreBoard();
            if (damageScoreBoard.hasDamageScoreBoard(player)) {
                DamageScore damageScore = damageScoreBoard.getDamageScore(player);
                damageScore.addDamageValue(damage, damageType);
            }
        }


    }
}


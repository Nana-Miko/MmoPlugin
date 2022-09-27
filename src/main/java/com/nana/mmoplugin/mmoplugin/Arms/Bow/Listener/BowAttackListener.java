package com.nana.mmoplugin.mmoplugin.Arms.Bow.Listener;

import com.nana.mmoplugin.mmoplugin.Arms.Bow.Define.ArrowActive;
import com.nana.mmoplugin.mmoplugin.Arms.Bow.Define.ArrowType;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.util.MmoComponent;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class BowAttackListener extends MmoListener {


    @EventHandler
    public void DealEvent(EntityShootBowEvent event) {
        Entity arrow = event.getProjectile();
        LivingEntity shooter = event.getEntity();
        event.setConsumeItem(false);

        ItemStack itemStack = shooter.getEquipment().getItemInMainHand();
        String lore = itemUtil.hasLore(itemStack, "[箭矢类型] ");
        if (lore == null) {
            return;
        }

        //System.out.println(event.getForce());

        ArrowType arrowType = null;

        for (ArrowType arrowtype :
                ArrowType.values()) {
            if (arrowtype.getName().equals(lore)) {
                arrowType = arrowtype;
            }
        }

        if (arrowType.hasAmmo(shooter) == false) {
            event.setCancelled(true);
            if (shooter.getType().equals(EntityType.PLAYER)) {
                MmoComponent mmoComponent = new MmoComponent("未装备对应类型的" + ChatColor.RED + "箭矢");
                mmoComponent.showText((Player) shooter, ChatMessageType.ACTION_BAR);
            }
            return;
        }

        AttackListener attackListener = (AttackListener) getPlugin().getListener(MmoListenerType.ATTACK);
        //添加伤害判定
        attackListener.addArrow(shooter, arrow, (double) event.getForce());


        try {
            Object object = arrowType.getClazz().getDeclaredConstructor(LivingEntity.class, Entity.class, MmoPlugin.class).newInstance(shooter, arrow, getPlugin());
            ArrowActive arrowActive = (ArrowActive) object;
            arrowActive.runTaskAsynchronously(getPlugin());
            arrowType.useAmmo(shooter);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }



}

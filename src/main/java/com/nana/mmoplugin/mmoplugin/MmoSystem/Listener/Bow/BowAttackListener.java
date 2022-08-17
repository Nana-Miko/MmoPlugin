package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Bow;

import com.nana.mmoplugin.mmoplugin.Bow.Define.ArrowActive;
import com.nana.mmoplugin.mmoplugin.Bow.Define.ArrowType;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;


public class BowAttackListener  implements Listener {
    private Mmoplugin plugin;


    public BowAttackListener(Mmoplugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void DealEvent(EntityShootBowEvent event){
        Entity arrow = event.getProjectile();
        LivingEntity shooter = event.getEntity();
        AttackListener attackListener = (AttackListener) plugin.getListener("AttackListener");
        //添加伤害判定
        attackListener.addArrow(shooter,arrow,(double)event.getForce());

        ItemStack itemStack = shooter.getEquipment().getItemInMainHand();
        String lore = itemUtil.hasLore(itemStack,"[箭矢类型] ");
        if (lore==null){return;}

        //System.out.println(event.getForce());

        for (ArrowType arrowtype :
                ArrowType.values()) {
            if(arrowtype.getName().equals(lore)){
                try {
                    Object object = arrowtype.getClazz().getDeclaredConstructor(LivingEntity.class,Entity.class, Mmoplugin.class).newInstance(shooter,arrow,plugin);
                    ArrowActive arrowActive = (ArrowActive) object;
                    arrowActive.runTaskAsynchronously(plugin);
                    break;
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





    }



}

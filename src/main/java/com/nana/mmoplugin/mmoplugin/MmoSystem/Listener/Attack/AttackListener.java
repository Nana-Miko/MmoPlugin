package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.util.Lock.CanLock;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AttackListener implements Listener, CanLock {

    private Mmoplugin plugin;


    private Map<LivingEntity, Set<Entity>> ArrowMap = new HashMap<>();
    private Map<Entity,Double> ArrowForceMap = new HashMap<>();

    private ClassLock user = null;

    public void addArrow(LivingEntity shooter,Entity arrow,Double force) {
        ClassLock lock = new ClassLock(this);
        lock.getLock();
        if (!ArrowMap.containsKey(shooter)){ArrowMap.put(shooter,new HashSet<>());}
        ArrowMap.get(shooter).add(arrow);
        setArrowForce(arrow,force);
        lock.release();
    }
    private void setArrowForce(Entity arrow,Double force){ArrowForceMap.put(arrow,force);}

    private void removeArrow(LivingEntity shooter,Entity arrow){
        ClassLock lock = new ClassLock(this);
        lock.getLock();
        if (!ArrowMap.containsKey(shooter)){lock.release();return;}
        ArrowMap.get(shooter).remove(arrow);
        if (ArrowMap.get(shooter).isEmpty()){ArrowMap.remove(shooter);}
        if (!ArrowForceMap.containsKey(arrow)){lock.release();return;}
        ArrowForceMap.remove(arrow);
        lock.release();
    }


    public Mmoplugin getPlugin() {
        return plugin;
    }

    public AttackListener(Mmoplugin plugin) {
        this.plugin = plugin;
        ReleaseArrow();
    }

    @EventHandler
    public void DealEvent(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.isCancelled()){return;}
        entityDamageByEntityEvent.setCancelled(true);

        LivingEntity Attacker = null;
        LivingEntity Attacked;
        Boolean isShootedArrow = false;
        Double Multiplier = 0.0;
        Entity Damager = entityDamageByEntityEvent.getDamager();

        ClassLock lock = new ClassLock(this);
        lock.getLock();
        for (Map.Entry<LivingEntity, Set<Entity>> entry :
                ArrowMap.entrySet()) {
            if (isShootedArrow){break;}
            for (Entity arrow :
                    entry.getValue()) {
                if(arrow.equals(Damager)){
                    Attacker = entry.getKey();
                    Multiplier = ArrowForceMap.get(arrow) - 0.5;
                    isShootedArrow = true;
                    break;
                }
            }

        }
        lock.release();


        try {
            if (isShootedArrow == false) {
                Attacker = (LivingEntity) entityDamageByEntityEvent.getDamager();
            }
            Attacked = (LivingEntity) entityDamageByEntityEvent.getEntity();
        } catch (ClassCastException e) {
            //entityDamageByEntityEvent.setCancelled(false);
            return;
        }

        ItemStack arms = Attacker.getEquipment().getItemInMainHand();

        String lore = itemUtil.hasLore(arms, "[武器类型] ");
        if (lore == null) {
            return;
        }
        ArmsType armsType = ArmsType.SWORD;
        for (ArmsType armType :
                ArmsType.values()) {
            if (armsType.getName().equals(lore)) {
                armsType = armType;
                break;
            }
        }


        Damage damage = new Damage(Attacker, Attacked, Multiplier, plugin);
        switch (armsType) {
            case BOW:
            case GIANT_SWORD:
            case SWORD:
                damage.setPanelDamagePercentage(armsType.getPanelDamagePercentage());
                damage.attack();
                break;
            case DOUBLE_BLADES:
                damage.setPanelDamagePercentage(armsType.getPanelDamagePercentage());
                damage.attack();
                damage.attack();
                break;

        }
        damage.attack();
        //System.out.println(damage.getDamageType());

        if (isShootedArrow == true) {
            AbstractArrow arrow = (AbstractArrow) entityDamageByEntityEvent.getDamager();
            if (arrow.getPierceLevel() == 0) {
                removeArrow(Attacker, entityDamageByEntityEvent.getDamager());
                arrow.remove();
            } else {
                entityDamageByEntityEvent.setCancelled(false);
            }

        }



        return;
    }

    // 定期释放ArrowMap以及ArrowForce中的无用变量
    private void ReleaseArrow(){
        AttackListener a = this;
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                ClassLock lock = new ClassLock(a);
                lock.getLock();
                Iterator<Map.Entry<LivingEntity,Set<Entity>>> iterator= ArrowMap.entrySet().iterator();
                while(iterator.hasNext()){
                    Set<Entity> entitySet = iterator.next().getValue();
                    // 测试
                    System.out.println(entitySet.toString());
                    Iterator<Entity> iterator1 = entitySet.iterator();
                    while (iterator1.hasNext()){
                        Entity entity = iterator1.next();
                        AbstractArrow arrow = (AbstractArrow) entity;

                        if (entity.isOnGround() || arrow.isInBlock()){
                            iterator1.remove();
                            if (ArrowForceMap.containsKey(entity)){ArrowForceMap.remove(entity);}
                        }

                    }
                    if (entitySet.isEmpty()){iterator.remove();}
                }

                lock.release();

                }
            };
        task.runTaskTimerAsynchronously(plugin,20*5,20*30);
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

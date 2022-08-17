package com.nana.mmoplugin.mmoplugin.Skill.Active;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.Skill.Define.DamageSkill;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.vectorUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ChopSword extends DamageSkill {

    public ChopSword(Mmoplugin plugin) {
        super(plugin);
    }



    @Override
    public Boolean skillRun() {
        this.setDamageMultipler(1.2);
        World world = getPlayer().getWorld();
        Vector vector = getPlayer().getLocation().add(0,10,0).getDirection().normalize().multiply(2);
        //Location location = vector.toLocation(world,getPlayer().getEyeLocation().getYaw(),0);
        ArmorStand armorStand = (ArmorStand) world.spawnEntity(getPlayer().getLocation().add(vector), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setItem(EquipmentSlot.HAND,new ItemStack(Material.DIAMOND_SWORD));

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                move(armorStand);
            }
        };
        task.runTaskAsynchronously(getPlugin());


        return true;
    }

    private void move(ArmorStand armorStand){

        Double height = 0.0;
        while (true){
            if(height>=6){break;}
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (LivingEntity livingEntity :
                    AsyncUtil.getNearbyLivingEntitiesAsync(armorStand, 1, getPlugin())) {
                if (livingEntity.equals(getPlayer())){continue;}
                if (this.IsAttacked(livingEntity)){continue;}
                skillDamage(livingEntity);
            }

            height = height+0.1;
            Location nowLocation = armorStand.getLocation();
            Location nextLocation = nowLocation.clone().subtract(0,height,0);

            vectorUtil.moveEntity(armorStand,nowLocation,nextLocation);

        }
        AsyncUtil.EntityRemoveAsync(armorStand,getPlugin());
    }

    @Override
    protected void skillDamage(LivingEntity livingEntity) {
        Damage damage = new Damage(getPlayer(),livingEntity,getDamageMultipler(),getPlugin());
        damage.attack();
        this.addAttackedEntity(livingEntity);
    }


}

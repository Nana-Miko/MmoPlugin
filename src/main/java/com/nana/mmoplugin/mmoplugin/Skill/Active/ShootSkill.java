package com.nana.mmoplugin.mmoplugin.Skill.Active;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.Skill.Define.ActiveSkillType;
import com.nana.mmoplugin.mmoplugin.Skill.Define.DamageSkill;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.vectorUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShootSkill extends DamageSkill {

    private ActiveSkillType type = ActiveSkillType.SHOOT_SWORD_CONTROL;

    private int startTime;
    private int continueTime;
    private double flySpeed;


    private EntityType entityType;
    private Location createLocation;

    public ShootSkill(MmoPlugin plugin) {
        super(plugin);
    }

    public double getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(double flySpeed) {
        this.flySpeed = flySpeed;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(int continueTime) {
        this.continueTime = continueTime;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Location getCreateLocation() {
        return createLocation;
    }

    public void setCreateLocation(Location createLocation) {
        this.createLocation = createLocation;
    }

    @Override
    public Boolean skillRunZeroStar() {

        this.setType(ActiveSkillType.SHOOT_SWORD_CONTROL);
        this.setContinueTime(10);
        this.setFlySpeed(0.05);
        this.setCreateLocation(this.getPlayer().getLocation());
        this.setDamageMultipler(0.1);
        this.entityType = EntityType.ARMOR_STAND;

        //System.out.println(continueTime);
        Entity entity0 = creatEntity();


        BukkitRunnable task0 = new BukkitRunnable() {

            @Override
            public void run() {
                move(entity0);
            }
        };

        task0.runTaskAsynchronously(this.getPlugin());


        return true;
    }


    public Entity creatEntity(){
        ArmorStand armorStand = (ArmorStand) this.getPlayer().getWorld().spawnEntity(createLocation,entityType);
        //System.out.println(armorStand.toString());
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        //armorStand.setArms(true);
        armorStand.setInvulnerable(true);//无敌
        //System.out.println(armorStand.hasArms());
        armorStand.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        return armorStand;
    }

    public void move(Entity entity){

        ArmorStand armorStand = (ArmorStand) entity;


        double timeCount = 0;
        double addCount = 1;
        while (true){

            List<Block> s = armorStand.getLineOfSight(null,2);
            Boolean stopSign = false;
            for (Block b :
                    s) {
                if(!b.getType().equals(Material.AIR)){
                    stopSign = true;
                    break;
                }
            }
            if(stopSign){break;}
            //System.out.println(armorStand.getLineOfSight(s,1).toString());


            if (timeCount>=continueTime){break;}

            Location entityLocation = armorStand.getLocation();
            Location playerEyeLocation = this.getPlayer().getEyeLocation();

            // 获取目光向量
            Vector eyeVector = playerEyeLocation.getDirection();

            // 获取新坐标，即玩家坐标加上目光向量(数乘为了改变距离)
            Location eyes = playerEyeLocation.clone().add(eyeVector.multiply(addCount));

            float playerYaw = playerEyeLocation.getYaw();   // 获取玩家头部的Yaw
            float playerPitch = playerEyeLocation.getPitch();   // 获取玩家头部的Pitch


            entityLocation.setPitch(playerPitch);  // 为实体设置Pitch
            entityLocation.setYaw(playerYaw);   // 为实体设置Yaw

            // 将盔甲架随玩家视角旋转
            EulerAngle eulerAngle = EulerAngle.ZERO;
            eulerAngle = eulerAngle.setX((Math.PI/180)*playerPitch);
            armorStand.setRightArmPose(eulerAngle);

            // 移动实体
            vectorUtil.moveEntity(entity,entityLocation,eyes);

            // 伤害判定
            skillDamage(entity);


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();break;
            }
            finally {
                timeCount = timeCount + 0.01;
                addCount = addCount+flySpeed;
            }
        }


        playEffect(entity);
        AsyncUtil.EntityRemoveAsync(entity,getPlugin());


    }


    private Set<Entity> attackedEntity = new HashSet<>();
    public int skillDamage(Entity entity) {

        int num = 0;


        //List<Entity> entityList = entity.getNearbyEntities(entity.getWidth()/2,entity.getHeight()/2,entity.getWidth());
        List<Entity> entityList = AsyncUtil.getNearbyEntitiesAsync(entity,entity.getWidth()/2,getPlugin());
        if (entityList.isEmpty()){return num;};
        //System.out.println(entityList.toString());

        //List<Entity> attackedEntity = new ArrayList<>();

        for (Entity nearlyEntity:
             entityList) {
            if (nearlyEntity.equals(this.getPlayer())){continue;};
            if (nearlyEntity.isDead()){continue;};
            if (attackedEntity.contains(nearlyEntity)){continue;};
            LivingEntity livingEntity;
            try {
                livingEntity = (LivingEntity) nearlyEntity;
            }
            catch (ClassCastException e){continue;}

            Damage damage = new Damage(this.getPlayer(),livingEntity,this.getDamageMultipler(),this.getPlugin());
            damage.attack();


            playEffect(entity);
            attackedEntity.add(nearlyEntity);
            //System.out.println(attackedEntity.toString());



            if (nearlyEntity.isDead()){attackedEntity.add(nearlyEntity);};



            num++;
        }

        return num;


    }

    private void playEffect(Entity entity){
        Location location = entity.getLocation();
        World world = entity.getWorld();
        world.spawnParticle(Particle.EXPLOSION_LARGE,location, 1);
        //world.playSound(location,Sound.);


    }
}

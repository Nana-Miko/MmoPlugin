package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Skill;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.SneakListener;
import com.nana.mmoplugin.mmoplugin.util.CanLock;
import com.nana.mmoplugin.mmoplugin.util.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import com.nana.mmoplugin.mmoplugin.Skill.Define.ActiveSkill;
import com.nana.mmoplugin.mmoplugin.Skill.Define.ActiveSkillType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ActiveSkillListener implements Listener, CanLock {

    private Map<UUID, Map<ActiveSkillType, Long>> skillCD;
    private Map<UUID,Map<ActiveSkillType,Integer>> UsedMap;
    private Mmoplugin plugin;
    private Map<UUID,Map<ActiveSkillType,Long>> lastUseTime;
    private ClassLock User = null;
    @Override
    public void setUser(ClassLock locker) {
        User = locker;
    }

    @Override
    public ClassLock getUser() {
        return User;
    }


    public ActiveSkillListener(Mmoplugin plugin) {
        skillCD = new HashMap<>();
        UsedMap = new HashMap<>();
        lastUseTime = new HashMap<>();
        this.plugin = plugin;

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                CdCheck();
            }
        };
        task.runTaskTimerAsynchronously(plugin,2,2);

    }

    private Integer skillUsed(UUID uid,ActiveSkillType skillType){
        ClassLock lock = new ClassLock(this);
        lock.getLock();
        if (!UsedMap.containsKey(uid)){UsedMap.put(uid,new HashMap<>());}
        Map<ActiveSkillType,Integer> skillTypeMap = UsedMap.get(uid);
        if (!skillTypeMap.containsKey(skillType)){skillTypeMap.put(skillType,0);}
        Integer used = skillTypeMap.get(skillType);
        used++;
        skillTypeMap.put(skillType,used);
        if (!lastUseTime.containsKey(uid)){lastUseTime.put(uid,new HashMap<>());}
        Map<ActiveSkillType,Long> LastUseTime = lastUseTime.get(uid);
        LastUseTime.put(skillType,System.currentTimeMillis());
        lock.release();
        return used;
    }

    private void cleanUsed(UUID uid,ActiveSkillType skillType){
        ClassLock lock = new ClassLock(this);
        lock.getLock();
        Map<ActiveSkillType,Integer> skillTypeMap = UsedMap.get(uid);
        if (skillTypeMap!=null) {
            if (skillTypeMap.containsKey(skillType)) {
                skillTypeMap.remove(skillType);
            }
            if (skillTypeMap.isEmpty()) {
                UsedMap.remove(uid);
            }
        }
        Map<ActiveSkillType,Long> LastUseTime = lastUseTime.get(uid);
        if (LastUseTime!=null) {
            if (LastUseTime.containsKey(skillType)) {
                LastUseTime.remove(skillType);
            }
            if (LastUseTime.isEmpty()) {
                lastUseTime.remove(uid);
            }
        }
        lock.release();
    }

    private Long getLastUseTime(UUID uid,ActiveSkillType skillType){
        ClassLock lock = new ClassLock(this);
        lock.getLock();
        if (!lastUseTime.containsKey(uid)){lastUseTime.put(uid,new HashMap<>());}
        Map<ActiveSkillType,Long> LastUseTime = lastUseTime.get(uid);
        if (LastUseTime.containsKey(skillType)){lock.release();return LastUseTime.get(skillType);}
        else {lock.release();return (long) 0;}

    }

    private Map<ActiveSkillType, Long> getCdMap(UUID uid,ActiveSkillType skillType){

        if (!skillCD.containsKey(uid)) {
            skillCD.put(uid, new HashMap<>());
        }
        Map<ActiveSkillType, Long> cdMap = skillCD.get(uid);
        if (!cdMap.containsKey(skillType)) {
            cdMap.put(skillType, (long) 0);
        }
        return cdMap;
    }

    private void SetCd(UUID uid,ActiveSkillType skillType,Map cdMap){
        //设置CD
        cleanUsed(uid,skillType);
        cdMap.put(skillType, System.currentTimeMillis());
        skillCD.put(uid, cdMap);
        plugin.getServer().getPlayer(uid).sendMessage(skillType.getName()+" 进入冷却");
    }



    @EventHandler
    public void useSkill(PlayerInteractEvent playerInteractEvent){

        AttackListener attackListener = (AttackListener) plugin.getListener("AttackListener");
        SneakListener sneakListener = (SneakListener) plugin.getListener("SneakListener");
        Player player = playerInteractEvent.getPlayer();


        // 判断是否右击空气
        if (!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR)){
            return;
        }


        // 判断手中是否拿着东西
        if (!playerInteractEvent.hasItem()) {
            return;
        }

        // 判断手中的是否为弓
        if (playerInteractEvent.getItem().getType().equals(Material.BOW)){
            return;
        }

        System.out.println("主动技能监听");



        ItemStack itemStack = playerInteractEvent.getItem();
        String skillName = itemUtil.hasLore(itemStack, "[主动技能] ");


        ActiveSkillType[] skills = ActiveSkillType.values();
        for (ActiveSkillType skill :
                skills) {
            PlayerCreateSkill(skillName, skill, player);
        }


        return;

    }

    // 玩家释放技能
    private void PlayerCreateSkill(String skillName, ActiveSkillType skillType, Player player) {
        AttackListener attackListener = (AttackListener) plugin.getListener("AttackListener");

        if (!skillType.getName().equals(skillName)) {
            return;
        }

        UUID uid = player.getUniqueId();
        Map<ActiveSkillType, Long> cdMap = getCdMap(uid,skillType);
        Long cd = cdMap.get(skillType);

        if ((System.currentTimeMillis() - cd) / 1000 <= skillType.getCd()) {
            player.sendMessage("技能 "
                    + skillType.getName()
                    + " 正在冷却中 剩余"
                    + (skillType.getCd() - (System.currentTimeMillis() - cd) / 1000) +
                    "秒");
            return;
        }

        for (ActiveSkillType skilltype :
                ActiveSkillType.values()) {
            if (skilltype != skillType) {
                continue;
            }
            try {
                Object object = null;
                object = skilltype.getClazz().getDeclaredConstructor(Mmoplugin.class).newInstance(plugin);


                ActiveSkill activeSkill = (ActiveSkill) object;
                activeSkill.setPlayer(player);

                switch (skilltype.getSingleType()){
                    case ONCE:{
                        if (activeSkill.skillRun() == false) {
                            player.sendMessage(skillType.getErrorTips());
                            return;
                        }else {player.sendMessage("您使用了 "+skillType.getName());}
                        break;
                    }
                    case MORE:{
                        Long lastUseTime = getLastUseTime(uid,skillType);
                        //System.out.println(lastUseTime);
                        if (lastUseTime!=(long)0 && System.currentTimeMillis()-lastUseTime<skilltype.getIntervalCd()*1000){
                            player.sendMessage("使用间隔过短");
                            return;
                        }
                        int use = skillUsed(uid,skillType);
                        //System.out.println(use);
                        activeSkill.setUsed(use);
                        if (activeSkill.skillRun() == false){
                            player.sendMessage("您使用了 "+skillType.getName());
                            return;
                        }
                        else {}
                        break;
                    }
                }


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



        //System.out.println(skillCD.toString());
        SetCd(uid,skillType,cdMap);
        return;
    }

    private void CdCheck(){
        ClassLock lock = new ClassLock(this);
        lock.getLock();

        Map<UUID,ActiveSkillType> SetCdMap = new HashMap<>();

        for (Map.Entry<UUID, Map<ActiveSkillType, Long>> en :
                lastUseTime.entrySet()) {
            for (Map.Entry<ActiveSkillType, Long> ex :
                    en.getValue().entrySet()) {
                if (ex.getValue()!=(long) 0 && System.currentTimeMillis() - ex.getValue() > ex.getKey().getMoreCd()*1000) {
                    SetCdMap.put(en.getKey(),ex.getKey());
                }
            }
        }
        lock.release();

        for (Map.Entry<UUID, ActiveSkillType> en :
                SetCdMap.entrySet()) {
            UUID uid = en.getKey();ActiveSkillType skillType = en.getValue();
            Map<ActiveSkillType, Long> cdMap = getCdMap(uid,skillType);
            SetCd(uid,skillType,cdMap);
        }

    }


}

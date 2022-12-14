package com.nana.mmoplugin.mmoplugin.Skill.Listener;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.SneakListener;
import com.nana.mmoplugin.mmoplugin.Skill.Define.ActiveSkill;
import com.nana.mmoplugin.mmoplugin.Skill.Define.ActiveSkillType;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.MmoComponent;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActiveSkillListener extends MmoListener implements PlayerStorageListener {

    private Map<UUID, Map<ActiveSkillType, Long>> skillCD;
    private Map<UUID, Map<ActiveSkillType, Integer>> UsedMap;
    private Map<UUID, Map<ActiveSkillType, Long>> lastUseTime;
    private ClassLock User = null;

    @Override
    public void setUser(ClassLock locker) {
        User = locker;
    }

    @Override
    public ClassLock getUser() {
        return User;
    }


    public ActiveSkillListener() {
        skillCD = new HashMap<>();
        UsedMap = new HashMap<>();
        lastUseTime = new HashMap<>();

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                CdCheck();
            }
        };
        task.runTaskTimerAsynchronously(getPlugin(), 2, 2);

    }

    private Integer skillUsed(UUID uid, ActiveSkillType skillType) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return null;
        }
        if (!UsedMap.containsKey(uid)) {
            UsedMap.put(uid, new HashMap<>());
        }
        Map<ActiveSkillType, Integer> skillTypeMap = UsedMap.get(uid);
        if (!skillTypeMap.containsKey(skillType)) {
            skillTypeMap.put(skillType, 0);
        }
        Integer used = skillTypeMap.get(skillType);
        used++;
        skillTypeMap.put(skillType, used);
        if (!lastUseTime.containsKey(uid)) {
            lastUseTime.put(uid, new HashMap<>());
        }
        Map<ActiveSkillType, Long> LastUseTime = lastUseTime.get(uid);
        LastUseTime.put(skillType, System.currentTimeMillis());
        lock.release();
        return used;
    }

    private void cleanUsed(UUID uid, ActiveSkillType skillType) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }
        Map<ActiveSkillType, Integer> skillTypeMap = UsedMap.get(uid);
        if (skillTypeMap != null) {
            if (skillTypeMap.containsKey(skillType)) {
                skillTypeMap.remove(skillType);
            }
            if (skillTypeMap.isEmpty()) {
                UsedMap.remove(uid);
            }
        }
        Map<ActiveSkillType, Long> LastUseTime = lastUseTime.get(uid);
        if (LastUseTime != null) {
            if (LastUseTime.containsKey(skillType)) {
                LastUseTime.remove(skillType);
            }
            if (LastUseTime.isEmpty()) {
                lastUseTime.remove(uid);
            }
        }
        lock.release();
    }

    private Long getLastUseTime(UUID uid, ActiveSkillType skillType) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return null;
        }
        if (!lastUseTime.containsKey(uid)) {
            lastUseTime.put(uid, new HashMap<>());
        }
        Map<ActiveSkillType, Long> LastUseTime = lastUseTime.get(uid);
        if (LastUseTime.containsKey(skillType)) {
            lock.release();
            return LastUseTime.get(skillType);
        } else {
            lock.release();
            return (long) 0;
        }

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

    private void SetCd(UUID uid, ActiveSkillType skillType, Map cdMap) {
        //??????CD
        cleanUsed(uid, skillType);
        cdMap.put(skillType, System.currentTimeMillis());
        skillCD.put(uid, cdMap);
        MmoComponent mmoComponent = new MmoComponent(ChatColor.GOLD + skillType.getName() + ChatColor.WHITE + " ????????????");
        mmoComponent.showText(getPlugin().getServer().getPlayer(uid), ChatMessageType.ACTION_BAR);
    }



    @EventHandler
    public void useSkill(PlayerInteractEvent playerInteractEvent) {

        AttackListener attackListener = (AttackListener) getPlugin().getListener(MmoListenerType.ATTACK);
        SneakListener sneakListener = (SneakListener) getPlugin().getListener(MmoListenerType.SNEAK);
        Player player = playerInteractEvent.getPlayer();


        // ????????????????????????
        if (!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }


        // ??????????????????????????????
        if (!playerInteractEvent.hasItem()) {
            return;
        }

        // ???????????????????????????
        if (playerInteractEvent.getItem().getType().equals(Material.BOW)){
            return;
        }

        //System.out.println("??????????????????");



        ItemStack itemStack = playerInteractEvent.getItem();
        String skillName = itemUtil.hasLore(itemStack, "[????????????] ");


        ActiveSkillType[] skills = ActiveSkillType.values();
        for (ActiveSkillType skill :
                skills) {
            PlayerCreateSkill(skillName, skill, player);
        }


        return;

    }

    // ??????????????????
    private void PlayerCreateSkill(String skillName, ActiveSkillType skillType, Player player) {
        AttackListener attackListener = (AttackListener) getPlugin().getListener(MmoListenerType.ATTACK);

        if (!skillType.getName().equals(skillName)) {
            return;
        }

        UUID uid = player.getUniqueId();
        Map<ActiveSkillType, Long> cdMap = getCdMap(uid,skillType);
        Long cd = cdMap.get(skillType);

        if ((System.currentTimeMillis() - cd) <= skillType.getCd()) {
            MmoComponent mmoComponent = new MmoComponent("?????? "
                    + ChatColor.GOLD
                    + skillType.getName()
                    + ChatColor.WHITE
                    + " ??????????????? ??????"
                    + ChatColor.RED
                    + (skillType.getCd() - (System.currentTimeMillis() - cd)) / 1000 +
                    "???");
            mmoComponent.showText(player, ChatMessageType.ACTION_BAR);

            return;
        }

        for (ActiveSkillType skilltype :
                ActiveSkillType.values()) {
            if (skilltype != skillType) {
                continue;
            }
            try {
                Object object = null;
                object = skilltype.getClazz().getDeclaredConstructor(MmoPlugin.class).newInstance(getPlugin());


                ActiveSkill activeSkill = (ActiveSkill) object;
                activeSkill.setPlayer(player);

                switch (skilltype.getSingleType()){
                    case ONCE:{
                        if (activeSkill.skillRun() == false) {
                            MmoComponent mmoComponent = new MmoComponent(ChatColor.RED + skillType.getErrorTips());
                            mmoComponent.showText(player, ChatMessageType.ACTION_BAR);
                            return;
                        } else {
                            //MmoComponent mmoComponent = new MmoComponent("???????????? "+skillType.getName());
                            //mmoComponent.showText(player,ChatMessageType.ACTION_BAR);
                        }
                        break;
                    }
                    case MORE: {
                        Long lastUseTime = getLastUseTime(uid, skillType);
                        if (lastUseTime != (long) 0 && System.currentTimeMillis() - lastUseTime < skilltype.getIntervalCd()) {
                            MmoComponent mmoComponent = new MmoComponent(ChatColor.RED + "??????????????????");
                            mmoComponent.showText(player, ChatMessageType.ACTION_BAR);
                            return;
                        }
                        int use = skillUsed(uid, skillType);
                        //System.out.println(use);
                        activeSkill.setUsed(use);
                        if (activeSkill.skillRun() == false) {
                            //MmoComponent mmoComponent = new MmoComponent("???????????? "+skillType.getName());
                            //mmoComponent.showText(player,ChatMessageType.ACTION_BAR);
                            return;
                        } else {
                        }
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

    private void CdCheck() {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }

        Map<UUID, ActiveSkillType> SetCdMap = new HashMap<>();

        for (Map.Entry<UUID, Map<ActiveSkillType, Long>> en :
                lastUseTime.entrySet()) {
            for (Map.Entry<ActiveSkillType, Long> ex :
                    en.getValue().entrySet()) {
                if (ex.getValue() != (long) 0 && System.currentTimeMillis() - ex.getValue() > ex.getKey().getMoreCd()) {
                    SetCdMap.put(en.getKey(), ex.getKey());
                }
            }
        }
        lock.release();

        for (Map.Entry<UUID, ActiveSkillType> en :
                SetCdMap.entrySet()) {
            UUID uid = en.getKey();
            ActiveSkillType skillType = en.getValue();
            Map<ActiveSkillType, Long> cdMap = getCdMap(uid, skillType);
            SetCd(uid, skillType, cdMap);
        }

    }


    @Override
    public Boolean unregisterPlayer(Player player) {
        UUID uid = player.getUniqueId();
        Boolean flag = false;
        if (skillCD.containsKey(uid)) {
            skillCD.remove(uid);
            flag = true;
        }
        if (UsedMap.containsKey(uid)) {
            UsedMap.remove(uid);
            flag = true;
        }
        if (lastUseTime.containsKey(uid)) {
            lastUseTime.remove(uid);
            flag = true;
        }

        return flag;
    }
}

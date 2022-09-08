package com.nana.mmoplugin.mmoplugin.Skill.Listener;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkill;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkillType;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import com.nana.mmoplugin.mmoplugin.util.MmoComponent;
import com.nana.mmoplugin.mmoplugin.util.itemUtil;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PassiveSkillListener extends MmoListener implements PlayerStorageListener {

    private Map<PassiveSkillType, Object> skillMap;
    private ClassLock user = null;

    public PassiveSkillListener(MmoPlugin plugin) {
        super(plugin);
        skillMap = new HashMap<>();
        for (PassiveSkillType skill :
                PassiveSkillType.values()) {

            try {
                Object object = skill.getClazz().getDeclaredConstructor(MmoPlugin.class).newInstance(plugin);
                plugin.getServer().getPluginManager().registerEvents((Listener) object, plugin);
                skillMap.put(skill, object);
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

    @EventHandler
    public void useSkill(PlayerItemHeldEvent playerItemHeldEvent) {
        Player player = playerItemHeldEvent.getPlayer();
        int NewSlot = playerItemHeldEvent.getNewSlot();
        int PreviousSlot = playerItemHeldEvent.getPreviousSlot();

        ItemStack NewitemStack = player.getInventory().getItem(NewSlot);
        ItemStack PreviousStack = player.getInventory().getItem(PreviousSlot);
        //如果之前是空手则直接返回（一定未持有被动）
        if (PreviousStack == null) {
            return;
        }

        // 检测之前是否拥有被动技能
        Set<String> PreviousLore = itemUtil.hasLore(PreviousStack, "[被动技能] ", new HashSet<>());
        //System.out.println(PreviousLore);
        if (!(PreviousLore == null)) {
            for (String skillName :
                    PreviousLore) {
                for (PassiveSkillType skill :
                        PassiveSkillType.values()) {
                    if (CancellSkill(skillName, skill, player)) {
                        break;
                    }
                }
            }

        }

        // 如果现在是空手直接返回（一定无法触发被动）
        if (NewitemStack == null || !NewitemStack.hasItemMeta()) {
            return;
        }


        Set<String> NewLore = itemUtil.hasLore(NewitemStack, "[被动技能] ", new HashSet<>());
        // 如果武器没有被动技能，直接返回
        if (NewLore == null) {
            return;
        }

        // 如果事件被取消，则返回
        if (playerItemHeldEvent.isCancelled()) {
            return;
        }


        // 触发被动
        for (String skillName :
                NewLore) {
            for (PassiveSkillType skill :
                    PassiveSkillType.values()) {
                if (TriggerSkill(skillName, skill, player)) {
                    break;
                }

            }
        }


        return;


    }


    public Boolean CancellSkill(String skillName, PassiveSkillType skillType, Player player) {
        if (!skillName.equals(skillType.getName())) {
            return false;
        }

        PassiveSkill passiveSkill = (PassiveSkill) skillMap.get(skillType);

        if (!passiveSkill.hasTrigger(player)) {
            return false;
        }

        passiveSkill.removeTrigger(player);

        //player.sendMessage(skillType.getName() + " 已失效");
        return true;
    }

    public Boolean TriggerSkill(String skillName, PassiveSkillType skillType, Player player) {
        if (!skillName.equals(skillType.getName())) {
            return false;
        }

        PassiveSkill passiveSkill = (PassiveSkill) skillMap.get(skillType);

        passiveSkill.putTrigger(player);

        //player.sendMessage(skillType.getName() + " 生效中");
        MmoComponent mmoComponent = new MmoComponent(ChatColor.GOLD + skillType.getName() + ChatColor.WHITE + " 生效中");
        mmoComponent.showText(player, ChatMessageType.ACTION_BAR);

        return true;
    }

    @Override
    public Boolean unregisterPlayer(Player player) {
        Boolean flag = false;
        for (Object object :
                skillMap.values()) {
            PassiveSkill passiveSkill = (PassiveSkill) object;
            if (passiveSkill.unregisterPlayer(player)) {
                flag = true;
            }
        }
        return flag;
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

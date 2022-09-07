package com.nana.mmoplugin.mmoplugin.Skill.Passive;


import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkill;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkillType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class IronBody extends PassiveSkill {

    public IronBody(MmoPlugin plugin) {
        super(plugin);
        this.setType(PassiveSkillType.IRON_BODY);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void attackMe(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();

        // 判断实体是否持有该被动
        if (!this.hasTrigger(entity)) {
            return;
        }

        Double Damage = event.getDamage();

        Damage = Damage / 2;

        event.setDamage(Damage);

        if (entity.getType().equals(EntityType.PLAYER)) {
            entity.sendMessage(this.getType().getName() + "已触发");
        }

        return;


    }
}

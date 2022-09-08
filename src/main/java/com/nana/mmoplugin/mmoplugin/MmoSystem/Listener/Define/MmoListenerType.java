package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Bow.Listener.BowAttackListener;
import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Listener.CrossBowAttackListener;
import com.nana.mmoplugin.mmoplugin.Arms.Listener.ArmsTypeListener;
import com.nana.mmoplugin.mmoplugin.Arms.Listener.LeftClickListener;
import com.nana.mmoplugin.mmoplugin.Arms.Staves.Listener.StaveAttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.*;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.BlockIgniteListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.DodgeListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.PlayerMoveListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.SneakListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.PlayerQuitListener;
import com.nana.mmoplugin.mmoplugin.Skill.Listener.ActiveSkillListener;
import com.nana.mmoplugin.mmoplugin.Skill.Listener.PassiveSkillListener;

public enum MmoListenerType implements MmoAttributeType {
    ATTACK(AttackListener.class),
    ACTIVE_SKILL(ActiveSkillListener.class),
    PASSIVE_SKILL(PassiveSkillListener.class),
    NORMAL_ATTACK(NormalAttackListener.class),
    CUTTING_ATTACK(CuttingAttackListener.class),
    MAGIC_ATTACK(MagicAttackListener.class),
    BOW_ATTACK(BowAttackListener.class),
    SNEAK(SneakListener.class),
    DODGE(DodgeListener.class),
    PLAYER_MOVE(PlayerMoveListener.class),
    ARMS_TYPE(ArmsTypeListener.class),
    ATTACK_CD(AttackCdListener.class),
    LEFT_CLICK(LeftClickListener.class),
    STAVE_ATTACK(StaveAttackListener.class),
    BLOCK_IGNITE(BlockIgniteListener.class),
    CROSSBOW_ATTACK(CrossBowAttackListener.class),
    PLAYER_QUIT(PlayerQuitListener.class)
    ;

    private Class<? extends MmoListener> clazz;

    MmoListenerType(Class<? extends MmoListener> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends MmoListener> getClazz() {
        return clazz;
    }


    @Override
    public String getTypeName() {
        return "监听器类型";
    }
}

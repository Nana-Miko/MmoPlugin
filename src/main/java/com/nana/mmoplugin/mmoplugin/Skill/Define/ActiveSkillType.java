package com.nana.mmoplugin.mmoplugin.Skill.Define;



import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import com.nana.mmoplugin.mmoplugin.Skill.Active.ChopSword;
import com.nana.mmoplugin.mmoplugin.Skill.Active.MuMyouSanDanZiKi;
import com.nana.mmoplugin.mmoplugin.Skill.Active.ShadowStrike;
import com.nana.mmoplugin.mmoplugin.Skill.Active.ShootSkill;

public enum ActiveSkillType implements MmoAttributeType {
    SHOOT_SWORD_CONTROL("御物之法", 5.0, 0.0, 0.0, "Error", ShootSkill.class, 0, ActiveSkillSingleType.ONCE),
    SHADOW_STRIKE("御天下大块之形", 5.0, 0.0, 0.0, "周围没有可攻击的对象", ShadowStrike.class, 1, ActiveSkillSingleType.ONCE),
    CHOP_SWORD("巨剑劈砍", 5.0, 0.0, 0.0, "Error", ChopSword.class, 2, ActiveSkillSingleType.ONCE),
    MU_MYO_U_SAN_DAN_ZI_KI("无明三段突", 5.0, 3.0, 0.5, "Error", MuMyouSanDanZiKi.class, 3, ActiveSkillSingleType.MORE),
    ;

    private final String name;
    private final Long cd;
    private final int id;
    private final String ErrorTips;
    private final Class<? extends ActiveSkill> clazz;
    private final ActiveSkillSingleType SingleType;
    // 在使用之后等待进入cd的时间
    private final Long moreCd;
    // 在两段之间使用的间隔
    private final Long intervalCd;

    ActiveSkillType(String name, Double cd, Double moreCD, Double intervalCd, String ErrorTips, Class<? extends ActiveSkill> clazz, int id, ActiveSkillSingleType SingleType) {
        // 此处把三个cd由秒的单位转化成毫秒
        cd = cd * 1000;
        moreCD = moreCD * 1000;
        intervalCd = intervalCd * 1000;
        this.ErrorTips = ErrorTips;
        this.name = name;
        this.cd = cd.longValue();
        this.moreCd = moreCD.longValue();
        this.id = id;
        this.clazz = clazz;
        this.SingleType = SingleType;
        this.intervalCd = intervalCd.longValue();
    }

    public Long getIntervalCd() {
        return intervalCd;
    }

    public ActiveSkillSingleType getSingleType() {
        return SingleType;
    }

    public Long getMoreCd() {
        return moreCd;
    }

    public String getErrorTips() {
        return ErrorTips;
    }

    public String getName() {
        return name;
    }

    public Long getCd() {
        return cd;
    }

    public int getId() {
        return id;
    }

    public Class<? extends ActiveSkill> getClazz() {
        return clazz;
    }


    @Override
    public String getTypeName() {
        return "主动技能类型";
    }
}

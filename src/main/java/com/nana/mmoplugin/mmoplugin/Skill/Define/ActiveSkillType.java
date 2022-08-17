package com.nana.mmoplugin.mmoplugin.Skill.Define;



import com.nana.mmoplugin.mmoplugin.Skill.Active.ChopSword;
import com.nana.mmoplugin.mmoplugin.Skill.Active.MuMyouSanDanZiKi;
import com.nana.mmoplugin.mmoplugin.Skill.Active.ShadowStrike;
import com.nana.mmoplugin.mmoplugin.Skill.Active.ShootSkill;

public enum ActiveSkillType {
    SHOOT_SWORD_CONTROL("御物之法", (long)5,(long)0,(long)0, "Error", ShootSkill.class, 0,ActiveSkillSingleType.ONCE),
    SHADOW_STRIKE("御天下大块之形", (long)5,(long)0,(long)0, "周围没有可攻击的对象", ShadowStrike.class, 1,ActiveSkillSingleType.ONCE),
    CHOP_SWORD("巨剑劈砍",(long)5,(long)0,(long)0,"Error", ChopSword.class,2,ActiveSkillSingleType.ONCE),
    MU_MYO_U_SAN_DAN_ZI_KI("无明三段突",(long)5,(long)3,(long)0.5,"Error", MuMyouSanDanZiKi.class,3,ActiveSkillSingleType.MORE),
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

    ActiveSkillType(String name, Long cd,Long moreCD,Long intervalCd, String ErrorTips, Class<? extends ActiveSkill> clazz, int id,ActiveSkillSingleType SingleType) {
        this.ErrorTips = ErrorTips;
        this.name = name;
        this.cd =cd;
        this.moreCd =moreCD;
        this.id = id;
        this.clazz = clazz;
        this.SingleType = SingleType;
        this.intervalCd =intervalCd;
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



}

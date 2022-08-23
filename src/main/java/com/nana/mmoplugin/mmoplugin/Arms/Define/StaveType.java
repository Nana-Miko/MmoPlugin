package com.nana.mmoplugin.mmoplugin.Arms.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Staves.FireBall;

public enum StaveType {
    FIRE_BALL("火球", 2.0, FireBall.class),
    ;
    private String name;
    private Long cd;
    private Class<? extends StaveActive> clazz;

    StaveType(String name, Double cd, Class<? extends StaveActive> clazz) {
        cd = cd * 1000;
        this.name = name;
        this.cd = cd.longValue();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Long getCd() {
        return cd;
    }

    public Class<? extends StaveActive> getClazz() {
        return clazz;
    }
}

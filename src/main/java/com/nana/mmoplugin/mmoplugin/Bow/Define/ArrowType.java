package com.nana.mmoplugin.mmoplugin.Bow.Define;


import com.nana.mmoplugin.mmoplugin.Bow.Arrow.SonicArrow;
import com.nana.mmoplugin.mmoplugin.Bow.Arrow.SpiltArrow;
import com.nana.mmoplugin.mmoplugin.Bow.Arrow.TrackingArrow;

public enum ArrowType {
    TRACKING("跟踪", TrackingArrow.class),
    SPILT("分裂", SpiltArrow.class),
    SONIC("音爆", SonicArrow.class),
    ;

    private String name;
    private Class<?extends ArrowActive> clazz;

    ArrowType(String name, Class<? extends ArrowActive> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<? extends ArrowActive> getClazz() {
        return clazz;
    }
}

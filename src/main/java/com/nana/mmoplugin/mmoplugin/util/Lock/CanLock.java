package com.nana.mmoplugin.mmoplugin.util.Lock;

public interface CanLock {
    // 存放上锁者
    void setUser(ClassLock locker);
    // 获取上锁者
    ClassLock getUser();


}

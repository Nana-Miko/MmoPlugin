package com.nana.mmoplugin.mmoplugin;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ListenerManager {


    private static Map<MmoListenerType, MmoListener> ListenerMap = new HashMap<>();

    private static ListenerManager listenerManager = null;

    public static ListenerManager getInstance() {
        if (listenerManager == null) {
            listenerManager = new ListenerManager();
        }
        return listenerManager;
    }

    public ListenerManager() {
        addListenerMap();
    }


    private void addListenerMap() {
        for (MmoListenerType mlt :
                MmoListenerType.values()) {
            try {
                MmoListener mmoListener = mlt.getClazz().getDeclaredConstructor().newInstance();
                ListenerMap.put(mlt, mmoListener);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    }

    public Map<MmoListenerType, MmoListener> getListenerMap() {
        return ListenerMap;
    }
}

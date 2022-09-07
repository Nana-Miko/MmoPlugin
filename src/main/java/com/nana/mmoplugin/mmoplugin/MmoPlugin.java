package com.nana.mmoplugin.mmoplugin;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class MmoPlugin extends JavaPlugin {
    private static MmoPlugin instance = null;

    public static MmoPlugin getInstance() {
        return instance;
    }

    private static Map<MmoListenerType, MmoListener> ListenerMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        FileConfiguration config = getConfig();
        addListenerMap();

        this.getCommand("get").setExecutor(new getArms(config));
        this.getCommand("skill").setExecutor(new getSkill());

        for (Map.Entry<MmoListenerType, MmoListener> entry :
                ListenerMap.entrySet()) {
            this.getServer().getPluginManager().registerEvents(entry.getValue(), this);
            getLogger().info("监听器 " + entry.getKey() + " 注册成功！");
        }


        this.saveDefaultConfig();
        getLogger().info("Mmo已经加载！INFO");
        //System.out.println("插件已加载");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void addListenerMap() {
        for (MmoListenerType mlt :
                MmoListenerType.values()) {
            try {
                MmoListener mmoListener = mlt.getClazz().getDeclaredConstructor(MmoPlugin.class).newInstance(this);
                ListenerMap.put(mlt, mmoListener);
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

    public Listener getListener(MmoListenerType mmoListenerType) {
        return ListenerMap.get(mmoListenerType);
    }
}

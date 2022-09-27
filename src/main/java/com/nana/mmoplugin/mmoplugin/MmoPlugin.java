package com.nana.mmoplugin.mmoplugin;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageScoreBoardManager;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.ShowDamage;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class MmoPlugin extends JavaPlugin {
    private static MmoPlugin instance = null;

    private ListenerManager listenerManager = null;

    public static MmoPlugin getInstance() {
        return instance;
    }

    private DamageScoreBoardManager damageScoreBoard = new DamageScoreBoardManager();

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        FileConfiguration config = getConfig();

        listenerManager = ListenerManager.getInstance();

        this.getCommand("get").setExecutor(new getArms(config));
        this.getCommand("skill").setExecutor(new getSkill());
        this.getCommand("showdamage").setExecutor(new ShowDamage(this));

        for (Map.Entry<MmoListenerType, MmoListener> entry :
                listenerManager.getListenerMap().entrySet()) {
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


    public MmoListener getListener(MmoListenerType mmoListenerType) {
        return listenerManager.getListenerMap().get(mmoListenerType);
    }

    public DamageScoreBoardManager getDamageScoreBoardManager() {
        return damageScoreBoard;
    }
}

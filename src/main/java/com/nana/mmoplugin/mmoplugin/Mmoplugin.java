package com.nana.mmoplugin.mmoplugin;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Arms.ArmsTypeListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Arms.LeftClickListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Arms.Staves.StaveAttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.*;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Bow.BowAttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.DodgeListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.PlayerMoveListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.SneakListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Skill.ActiveSkillListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Skill.PassiveSkillListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Mmoplugin extends JavaPlugin {
    private static Mmoplugin instance = null;
    public static Mmoplugin getInstance(){
        return instance;
    }
    private static Map<String,Listener> ListenerMap = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        FileConfiguration config = getConfig();
        addListenerMap();

        this.getCommand("get").setExecutor(new getArms(config));
        this.getCommand("skill").setExecutor(new getSkill());

        for (Map.Entry<String, Listener> entry :
                ListenerMap.entrySet()) {
            this.getServer().getPluginManager().registerEvents(entry.getValue(),this);
            getLogger().info("监听器 "+entry.getKey()+" 注册成功！");
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
        ListenerMap.put("AttackListener", new AttackListener(this));
        ListenerMap.put("ActiveSkillListener", new ActiveSkillListener(this));
        ListenerMap.put("PassiveSkillListener", new PassiveSkillListener(this));
        ListenerMap.put("NormalAttackListener", new NormalAttackListener(this));
        ListenerMap.put("CuttingAttackerListener", new CuttingAttackerListener(this));
        ListenerMap.put("MagicAttackListener", new MagicAttackListener(this));
        ListenerMap.put("BowAttackListener", new BowAttackListener(this));
        ListenerMap.put("SneakListener", new SneakListener(this));
        ListenerMap.put("DodgeListener", new DodgeListener(this));
        ListenerMap.put("PlayerMoveListener", new PlayerMoveListener(this));
        ListenerMap.put("ArmsTypeListener", new ArmsTypeListener(this));
        ListenerMap.put("AttackCdListener", new AttackCdListener(this));
        ListenerMap.put("LeftClickListener", new LeftClickListener(this));
        ListenerMap.put("StaveAttackListener", new StaveAttackListener(this));

    }
    public Listener getListener(String ListenerName){
        if (!ListenerMap.containsKey(ListenerName)){return null;}
        return ListenerMap.get(ListenerName);
    }
}

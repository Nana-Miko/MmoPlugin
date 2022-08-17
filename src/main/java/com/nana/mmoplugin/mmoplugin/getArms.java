package com.nana.mmoplugin.mmoplugin;

import com.nana.mmoplugin.mmoplugin.Arms.CustomArms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class getArms implements CommandExecutor {
    private FileConfiguration config;
    public getArms(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String ArmsName = args[0];
        //System.out.println(config.get("CustomArms").toString()+"啊啊啊啊啊");
        Object tt = config.get("CustomArms");
        //Map CustomArmsMap = (Map) config.get("CustomArms");
        //Map ArmsConfig = (Map) config.get("CustomArms."+ArmsName);
        String path = "CustomArms."+ArmsName;
        CustomArms customArms = new CustomArms(config,path);


        Player player = (Player) sender;
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.addItem(customArms.CreatCustomArms());



        player.setHealth(5.0);
        return true;
    }
}

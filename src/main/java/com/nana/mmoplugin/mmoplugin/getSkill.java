package com.nana.mmoplugin.mmoplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getSkill implements CommandExecutor {
    private MmoPlugin plugin;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin = MmoPlugin.getInstance();

        Player player = (Player) commandSender;


        return true;
    }
}

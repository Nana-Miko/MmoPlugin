package com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowDamage implements CommandExecutor {
    private MmoPlugin plugin;

    public ShowDamage(MmoPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        DamageScoreBoardManager damageScoreBoard = plugin.getDamageScoreBoardManager();
        if (damageScoreBoard.hasDamageScoreBoard(player)) {
            damageScoreBoard.removeDamageScoreBoard(player);
            plugin.getLogger().info(player.getName() + " 关闭了伤害面板");
        } else {
            damageScoreBoard.addDamageScoreBoard(player);
            plugin.getLogger().info(player.getName() + " 开启了伤害面板");
            player.sendMessage("你已打开伤害面板，伤害计算周期为3秒");
        }
        return true;
    }
}

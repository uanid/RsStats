package com.uanid.minecraft.command;

import com.uanid.minecraft.RsStats;
import com.uanid.minecraft.configuration.MessageConfig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            RsStats.sts.onCommand(sender, cmd, label, new String[]{"view"});
        } else {
            sender.sendMessage(MessageConfig.CANT_CONSOLE_USE_THIS_COMMAND);
        }
        return true;
    }
}
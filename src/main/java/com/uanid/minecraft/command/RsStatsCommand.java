package com.uanid.minecraft.command;

import com.uanid.minecraft.RsStats;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class RsStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("§6플러그인 버전: §c" + RsStats.VERSION);
        sender.sendMessage("§6제작자: §cTPsw");
        sender.sendMessage("§6서버 버전: §c" + Bukkit.getVersion());
        return true;
    }
}

package com.uanid.minecraft.command;

import java.util.HashMap;

import com.uanid.minecraft.service.StatsAPI;
import com.uanid.minecraft.RsStats;
import com.uanid.minecraft.configuration.MessageConfig;
import com.uanid.minecraft.domain.entity.RpgStats;
import com.uanid.minecraft.domain.entity.StatsPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rsstats.kr.tpsw.api.bukkit.API;
import rsstats.kr.tpsw.api.bukkit.PlayersAPI;

public class Stsadmin2Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int argslen = args.length;
        if (argslen == 0) {
            MessageConfig.helpMessageList(sender, MessageConfig.stsadmin2, 1, label);
        } else if (API.isInteger(args[0])) {
            MessageConfig.helpMessageList(sender, MessageConfig.stsadmin2, Integer.valueOf(args[0]), label);
        } else if (args[0].equalsIgnoreCase("set") && argslen == 3) {
            if (API.isIntegerPositive(args[2])) {
                if (args[1].equalsIgnoreCase("rpgexpsystem")) {
                    StatsAPI.rpgexpsystem = Integer.valueOf(args[2]);
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SET_BONUS);
                } else if (args[1].equalsIgnoreCase("mclevelup")) {
                    StatsAPI.levelup = Integer.valueOf(args[2]);
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SET_BONUS);
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_BONUS_TYPE);
                }
            } else {
                sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
            }
        } else if (args[0].equalsIgnoreCase("save") && argslen == 2) {
            switch (args[1]) {
                case "config":
                    RsStats.config.saveYaml();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SAVE);
                    return true;

                case "stats":
                    StatsAPI.saveRpgStats();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SAVE);
                    return true;

                case "user":
                    StatsAPI.saveStatsPlayer();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SAVE);
                    return true;

                case "message":
                    RsStats.message.saveYaml();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SAVE);
                    return true;
            }
            sender.sendMessage(MessageConfig.INCORRECT_FILE_NAME);
        } else if (args[0].equalsIgnoreCase("reload") && argslen == 2) {
            switch (args[1]) {
                case "config":
                    RsStats.config.reloadYaml();
                    StatsAPI.loadStatsItem();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_RELOAD);
                    return true;

                case "stats":
                    for (Player player : PlayersAPI.getOnlinePlayers()) {
                        player.kickPlayer(MessageConfig.KICK_MESSAGE);
                    }
                    StatsAPI.rpgstats = new HashMap<String, RpgStats>();
                    StatsAPI.loadRpgStats();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_RELOAD);
                    return true;

                case "user":
                    for (Player player : PlayersAPI.getOnlinePlayers()) {
                        player.kickPlayer(MessageConfig.KICK_MESSAGE);
                    }
                    StatsAPI.statsplyaer = new HashMap<String, StatsPlayer>();
                    StatsAPI.loadStatsPlayer();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_RELOAD);
                    return true;

                case "message":
                    RsStats.message.reloadYaml();
                    MessageConfig.updateMessageAPIs();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_RELOAD);
                    return true;
            }
            sender.sendMessage(MessageConfig.INCORRECT_FILE_NAME);
        } else if (args[0].equalsIgnoreCase("viewconfig") && argslen == 1) {
            sender.sendMessage(MessageConfig.VIEW_STATS_BONUS.replace("<type>", "rpgexpsystem").replace("<bonus>", String.valueOf(StatsAPI.rpgexpsystem)));
            sender.sendMessage(MessageConfig.VIEW_STATS_BONUS.replace("<type>", "mclevelup").replace("<bonus>", String.valueOf(StatsAPI.levelup)));
            sender.sendMessage(MessageConfig.VIEW_STATS_BONUS.replace("<type>", "auto-save-time").replace("<bonus>", String.valueOf(StatsAPI.autosavetime)));
        } else {
            sender.sendMessage(MessageConfig.INCORRECT_MESSAGE.replace("<cmd>", label));
        }
        return true;
    }
}

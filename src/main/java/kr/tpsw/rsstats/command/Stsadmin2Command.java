package kr.tpsw.rsstats.command;

import java.util.HashMap;

import kr.tpsw.rsstats.RsStats;
import kr.tpsw.rsstats.api.MessageAPI;
import kr.tpsw.rsstats.api.RpgStats;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsPlayer;

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
            MessageAPI.helpMessageList(sender, MessageAPI.stsadmin2, 1, label);
        } else if (API.isInteger(args[0])) {
            MessageAPI.helpMessageList(sender, MessageAPI.stsadmin2, Integer.valueOf(args[0]), label);
        } else if (args[0].equalsIgnoreCase("set") && argslen == 3) {
            if (API.isIntegerPositive(args[2])) {
                if (args[1].equalsIgnoreCase("rpgexpsystem")) {
                    StatsAPI.rpgexpsystem = Integer.valueOf(args[2]);
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SET_BONUS);
                } else if (args[1].equalsIgnoreCase("mclevelup")) {
                    StatsAPI.levelup = Integer.valueOf(args[2]);
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SET_BONUS);
                } else {
                    sender.sendMessage(MessageAPI.INCORRECT_BONUS_TYPE);
                }
            } else {
                sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
            }
        } else if (args[0].equalsIgnoreCase("save") && argslen == 2) {
            switch (args[1]) {
                case "config":
                    RsStats.config.saveYaml();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SAVE);
                    return true;

                case "stats":
                    StatsAPI.saveRpgStats();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SAVE);
                    return true;

                case "user":
                    StatsAPI.saveStatsPlayer();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SAVE);
                    return true;

                case "message":
                    RsStats.message.saveYaml();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_SAVE);
                    return true;
            }
            sender.sendMessage(MessageAPI.INCORRECT_FILE_NAME);
        } else if (args[0].equalsIgnoreCase("reload") && argslen == 2) {
            switch (args[1]) {
                case "config":
                    RsStats.config.reloadYaml();
                    StatsAPI.loadStatsItem();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_RELOAD);
                    return true;

                case "stats":
                    for (Player player : PlayersAPI.getOnlinePlayers()) {
                        player.kickPlayer(MessageAPI.KICK_MESSAGE);
                    }
                    StatsAPI.rpgstats = new HashMap<String, RpgStats>();
                    StatsAPI.loadRpgStats();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_RELOAD);
                    return true;

                case "user":
                    for (Player player : PlayersAPI.getOnlinePlayers()) {
                        player.kickPlayer(MessageAPI.KICK_MESSAGE);
                    }
                    StatsAPI.statsplyaer = new HashMap<String, StatsPlayer>();
                    StatsAPI.loadStatsPlayer();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_RELOAD);
                    return true;

                case "message":
                    RsStats.message.reloadYaml();
                    MessageAPI.updateMessageAPIs();
                    sender.sendMessage(MessageAPI.SUCCESSFULLY_RELOAD);
                    return true;
            }
            sender.sendMessage(MessageAPI.INCORRECT_FILE_NAME);
        } else if (args[0].equalsIgnoreCase("viewconfig") && argslen == 1) {
            sender.sendMessage(MessageAPI.VIEW_STATS_BONUS.replace("<type>", "rpgexpsystem").replace("<bonus>", String.valueOf(StatsAPI.rpgexpsystem)));
            sender.sendMessage(MessageAPI.VIEW_STATS_BONUS.replace("<type>", "mclevelup").replace("<bonus>", String.valueOf(StatsAPI.levelup)));
            sender.sendMessage(MessageAPI.VIEW_STATS_BONUS.replace("<type>", "auto-save-time").replace("<bonus>", String.valueOf(StatsAPI.autosavetime)));
        } else {
            sender.sendMessage(MessageAPI.INCORRECT_MESSAGE.replace("<cmd>", label));
        }
        return true;
    }
}

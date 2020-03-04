package com.uanid.minecraft.command;

import com.uanid.minecraft.configuration.MessageConfig;
import com.uanid.minecraft.domain.entity.StatsPlayer;
import com.uanid.minecraft.service.StatsService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rsstats.kr.tpsw.api.bukkit.API;
import rsstats.kr.tpsw.api.bukkit.PlayersAPI;

public class StsgiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 2) {
            String target;
            if ((target = PlayersAPI.findPlayerName(args[0])) != null) {
                StatsPlayer sp = StatsService.getStatsPlayer(target);
                if (API.isIntegerPositive(args[1])) {
                    sp.addAvailablePoint(Integer.valueOf(args[1]));
                    sp.sendMessage(MessageConfig.STATS_GIVE_MESSAGE.replace("<point>", args[1]));
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_USER);
            }
        } else {
            for (String s : MessageConfig.stsgive) {
                sender.sendMessage(s);
            }
        }
        return true;
    }
}

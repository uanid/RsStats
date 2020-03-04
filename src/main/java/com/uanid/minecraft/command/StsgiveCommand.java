package com.uanid.minecraft.command;

import com.uanid.minecraft.api.StatsAPI;
import com.uanid.minecraft.api.MessageAPI;
import com.uanid.minecraft.api.StatsPlayer;

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
                StatsPlayer sp = StatsAPI.getStatsPlayer(target);
                if (API.isIntegerPositive(args[1])) {
                    sp.addAvailablePoint(Integer.valueOf(args[1]));
                    sp.sendMessage(MessageAPI.STATS_GIVE_MESSAGE.replace("<point>", args[1]));
                } else {
                    sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageAPI.CANT_FINT_USER);
            }
        } else {
            for (String s : MessageAPI.stsgive) {
                sender.sendMessage(s);
            }
        }
        return true;
    }
}

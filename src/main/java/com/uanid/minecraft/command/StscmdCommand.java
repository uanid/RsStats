package com.uanid.minecraft.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.uanid.minecraft.service.StatsAPI;
import com.uanid.minecraft.configuration.MessageConfig;
import com.uanid.minecraft.util.StatsDataUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rsstats.kr.tpsw.api.bukkit.API;

public class StscmdCommand implements CommandExecutor {

    public static Set<String> type = new HashSet<String>();

    static {
        type.add("cmd");
        type.add("cmdop");
        type.add("cmdcon");
        type.add("chat");
        type.add("chatop");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int argslen = args.length;
        if (argslen == 0) {
            MessageConfig.helpMessageList(sender, MessageConfig.stscmd, 1, label);
        } else if (API.isInteger(args[0]) && argslen == 1) {
            MessageConfig.helpMessageList(sender, MessageConfig.stscmd, Integer.valueOf(args[0]), label);
        } else if (args[0].equalsIgnoreCase("set") && argslen >= 5) {
            if (StatsAPI.isStat(args[1])) {
                if (API.isIntegerPositive(args[2])) {
                    if (type.contains(args[3].toLowerCase())) {
                        String stats = args[1];
                        int point = Integer.valueOf(args[2]);
                        String cmdtype = args[3].toLowerCase();
                        String cmd = API.mergeArgsUnder(args, 4);
                        Map<Integer, String> smap = StatsDataUtil.map.get(stats);
                        if (smap == null) {
                            smap = new TreeMap<Integer, String>();
                        }
                        smap.put(point, cmdtype + " " + cmd);
                        StatsDataUtil.map.put(stats, smap);
                        sender.sendMessage(MessageConfig.SUCCESSFULLY_CMD_SET);
                    } else {
                        sender.sendMessage(MessageConfig.INCORRECT_CMD_TYPE);
                    }
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("remove") && argslen == 3) {
            if (StatsAPI.isStat(args[1])) {
                if (API.isIntegerPositive(args[2])) {
                    int point = Integer.valueOf(args[2]);
                    String stats = args[1];
                    Map<Integer, String> smap = StatsDataUtil.map.get(stats);
                    if (smap == null) {
                        smap = new TreeMap<Integer, String>();
                    }
                    smap.remove(point);
                    StatsDataUtil.map.put(stats, smap);
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_CMD_REMOVE);
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("list") && argslen <= 2) {
            List<String> list = new ArrayList<String>();
            Map<Integer, String> smap;
            for (String key : StatsDataUtil.map.keySet()) {
                smap = StatsDataUtil.map.get(key);
                list.add(MessageConfig.STSCMD_TYPE.replace("<stats>", key));
                for (Integer i : smap.keySet()) {
                    list.add(MessageConfig.STSCMD_CMD.replace("<index>", String.valueOf(i)).replace("<cmd>", smap.get(i).replace('_', ' ')));
                }
            }
            int index = 1;
            if (argslen != 1) {
                if (API.isIntegerPositive(args[1])) {
                    index = Integer.valueOf(args[1]);
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                    return true;
                }
            }
            MessageConfig.helpMessageList(sender, list, index, label);
        } else {
            sender.sendMessage(MessageConfig.INCORRECT_MESSAGE.replace("<cmd>", label));
        }
        return true;
    }
}

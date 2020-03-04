package com.uanid.minecraft.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.uanid.minecraft.service.StatsService;
import com.uanid.minecraft.configuration.MessageConfig;
import com.uanid.minecraft.domain.entity.RpgStats;
import com.uanid.minecraft.domain.type.StatsType;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsstats.kr.tpsw.api.bukkit.API;

public class StsadminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int argslen = args.length;
        if (argslen == 0) {
            MessageConfig.helpMessageList(sender, MessageConfig.stsadmin, 1, label);
        } else if (API.isInteger(args[0])) {
            MessageConfig.helpMessageList(sender, MessageConfig.stsadmin, Integer.valueOf(args[0]), label);
        } else if (args[0].equalsIgnoreCase("add") && argslen == 6) {
            if (!StatsService.isStat(args[1])) {
                if (API.isIntegerPositive(args[2]) && API.isIntegerPositive(args[3])) {
                    String name = args[1];
                    int x = Integer.valueOf(args[2]);
                    int y = Integer.valueOf(args[3]);
                    if (x >= 1 && x <= 9 && y >= 0 && y <= 3) {
                        if (StatsService.isStatsType(args[4].toUpperCase())) {
                            StatsType type = StatsType.valueOf(args[4].toUpperCase());
                            if (API.isDoublePositive(args[5])) {
                                double coe = Double.valueOf(args[5]);
                                ItemStack is = new ItemStack(Material.DIAMOND);
                                ItemMeta im = is.getItemMeta();
                                im.setLore(new ArrayList<String>());
                                im.setDisplayName("§2[§a " + name + " §2]");
                                is.setItemMeta(im);
                                StatsService.addRpgStats(name, x, y, type, coe, is);
                                StatsService.updateRpgStatsHastSet();
                                sender.sendMessage(MessageConfig.SUCCESSFULLY_ADD_STATS);
                            } else {
                                sender.sendMessage(MessageConfig.INCORRECT_COEFFICIENT);
                            }
                        } else {
                            sender.sendMessage(MessageConfig.INCORRECT_STATS_TYPE);
                        }
                    } else {
                        sender.sendMessage(MessageConfig.PLZ_ENTER_CORRECT_X_Y);
                    }
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_X_Y_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.ALREADY_HAS_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("edit") && argslen == 6) {
            if (StatsService.isStat(args[1])) {
                if (API.isIntegerPositive(args[2]) && API.isIntegerPositive(args[3])) {
                    String name = args[1];
                    int x = Integer.valueOf(args[2]);
                    int y = Integer.valueOf(args[3]);
                    if (x >= 1 && x <= 9 && y >= 0 && y <= 3) {
                        if (StatsService.isStatsType(args[4].toUpperCase())) {
                            StatsType type = StatsType.valueOf(args[4].toUpperCase());
                            if (API.isDoublePositive(args[5])) {
                                double coe = Double.valueOf(args[5]);
                                RpgStats rs = StatsService.getRpgStats(name);
                                rs.x = x;
                                rs.y = y;
                                rs.type = type;
                                rs.coe = coe;
                                StatsService.updateRpgStats();
                                StatsService.updateRpgStatsHastSet();
                                sender.sendMessage(MessageConfig.SUCCESSFULLY_EDIT_STATS);
                            } else {
                                sender.sendMessage(MessageConfig.INCORRECT_COEFFICIENT);
                            }
                        } else {
                            sender.sendMessage(MessageConfig.INCORRECT_STATS_TYPE);
                        }
                    } else {
                        sender.sendMessage(MessageConfig.PLZ_ENTER_CORRECT_X_Y);
                    }
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_X_Y_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("remove") && argslen == 2) {
            if (StatsService.isStat(args[1])) {
                StatsService.removeRpgStats(args[1]);
                sender.sendMessage(MessageConfig.SUCCESSFULLY_REMOVE_STATS);
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("list") && argslen <= 2) {
            int index = 1;
            if (argslen == 2) {
                if (API.isIntegerPositive(args[1])) {
                    index = Integer.valueOf(args[1]);
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                    return true;
                }
            } else {
                index = 1;
            }
            Collection<RpgStats> col = StatsService.rpgstats.values();
            List<String> list = new LinkedList<String>();
            for (RpgStats rs : col) {
                list.add(rs.toString());
            }
            MessageConfig.helpMessageList(sender, list, index, label + " list");
        } else if (args[0].equalsIgnoreCase("addlore") && argslen >= 3) {
            if (StatsService.isStat(args[1])) {
                RpgStats rs = StatsService.getRpgStats(args[1]);
                ItemMeta im = rs.is.getItemMeta();
                List<String> lore = im.getLore();
                if (lore == null) {
                    lore = new ArrayList<String>();
                }
                lore.add(API.mergeArgs(args, 2).replace("&", "§"));
                im.setLore(lore);
                rs.is.setItemMeta(im);
                StatsService.updateRpgStats();
                sender.sendMessage(MessageConfig.SUCCESSFULLY_ADDLORE_STATS);
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("removelore") && argslen == 3) {
            if (StatsService.isStat(args[1])) {
                RpgStats rs = StatsService.getRpgStats(args[1]);
                if (API.isIntegerPositive(args[2])) {
                    int index = Integer.valueOf(args[2]) - 1;
                    ItemMeta im = rs.is.getItemMeta();
                    List<String> list = im.getLore();
                    if (list == null) {
                        list = new ArrayList<String>();
                    }
                    if (list.size() > index) {
                        list.remove(index);
                        im.setLore(list);
                        rs.is.setItemMeta(im);
                        StatsService.updateRpgStats();
                        sender.sendMessage(MessageConfig.SUCCESSFULLY_REMOVELORE_STATS);
                    } else {
                        sender.sendMessage(MessageConfig.INCORRECT_LIST_INDEX);
                    }
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("setitem") && argslen == 3) {
            if (StatsService.isStat(args[1])) {
                RpgStats rs = StatsService.getRpgStats(args[1]);
                int[] ia = API.getItemCode(args[2]);
                if (ia[0] == 0) {
                    sender.sendMessage(MessageConfig.INCORRECT_ITEM_CODE);
                } else {
                    rs.is.setType(Material.getMaterial(String.valueOf(ia[0])));
                    rs.is.setDurability((short) ia[1]);
                    StatsService.updateRpgStats();
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SETITEM_STATS);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("setitemname") && argslen >= 3) {
            if (StatsService.isStat(args[1])) {
                RpgStats rs = StatsService.getRpgStats(args[1]);
                String name = API.mergeArgs(args, 2);
                ItemMeta im = rs.is.getItemMeta();
                im.setDisplayName(name.replace('&', '§'));
                rs.is.setItemMeta(im);
                StatsService.updateRpgStats();
                sender.sendMessage(MessageConfig.SUCCESSFULLY_SETITEMNAME_STATS);
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("setmax") && argslen == 3) {
            if (StatsService.isStat(args[1])) {
                RpgStats rs = StatsService.getRpgStats(args[1]);
                if (API.isIntegerPositive(args[2])) {
                    int v = Integer.valueOf(args[2]);
                    rs.max = v;
                    sender.sendMessage(MessageConfig.SUCCESSFULLY_SETMAX_STATS);
                } else {
                    sender.sendMessage(MessageConfig.INCORRECT_POSITIVE_INTEGER);
                }
            } else {
                sender.sendMessage(MessageConfig.CANT_FINT_STATS_NAME);
            }
        } else if (args[0].equalsIgnoreCase("agwgawgwa") && argslen == 2) {

        } else {
            sender.sendMessage(MessageConfig.INCORRECT_MESSAGE.replace("<cmd>", label));
        }
        return true;
    }
}
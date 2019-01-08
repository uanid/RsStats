package kr.tpsw.rsstats.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import kr.tpsw.rsstats.api.MessageAPI;
import kr.tpsw.rsstats.api.RpgStats;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsPlayer;
import kr.tpsw.rsstats.api.StatsRunAPI;
import kr.tpsw.rsstats.api.StatsAPI.StatsSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rsstats.kr.tpsw.api.bukkit.API;
import rsstats.kr.tpsw.api.bukkit.PlayersAPI;

public class StsuserCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int argslen = args.length;
		if (argslen == 0) {
			MessageAPI.helpMessageList(sender, MessageAPI.stsuser, 1, label);
		} else if (API.isInteger(args[0])) {
			MessageAPI.helpMessageList(sender, MessageAPI.stsuser, Integer.valueOf(args[0]), label);
		} else if (args[0].equalsIgnoreCase("add") && argslen == 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (API.isIntegerPositive(args[2])) {
					StatsPlayer sp = StatsAPI.getStatsPlayer(target);
					sp.addAvailablePoint(Integer.valueOf(args[2]));
					sender.sendMessage(MessageAPI.SUCCESSFULLY_ADD_POINT);
				} else {
					sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("set") && argslen == 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (API.isIntegerPositive(args[2])) {
					StatsPlayer sp = StatsAPI.getStatsPlayer(target);
					sp.setAvailablePoint(Integer.valueOf(args[2]));
					sender.sendMessage(MessageAPI.SUCCESSFULLY_SET_POINT);
				} else {
					sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("view") && argslen <= 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				int i = 1;
				if (argslen == 3 && API.isIntegerPositive(args[2])) {
					i = Integer.valueOf(args[2]);
				}
				StatsPlayer sp = StatsAPI.getStatsPlayer(target);
				Set<String> set = sp.getStats().keySet();
				List<String> list = new LinkedList<String>();
				for (String name : set) {
					list.add(MessageAPI.VIEW_USER_STATS.replace("<stats>", name).replace("<point>", String.valueOf(sp.getStatPoint(name))));
				}
				MessageAPI.helpMessageListPLUSINDEX(sender, list, i, label);
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("dadd") && argslen == 4) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (StatsAPI.isStat(args[2])) {
					if (API.isIntegerPositive(args[3])) {
						StatsPlayer sp = StatsAPI.getStatsPlayer(target);
						sp.addStatsPoint(args[2], Integer.valueOf(args[3]));
						sender.sendMessage(MessageAPI.SUCCESSFULLY_ADD_POINT);
					} else {
						sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
					}
				} else {
					sender.sendMessage(MessageAPI.CANT_FINT_STATS_NAME);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("dset") && argslen == 4) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (StatsAPI.isStat(args[2])) {
					if (API.isIntegerPositive(args[3])) {
						StatsPlayer sp = StatsAPI.getStatsPlayer(target);
						sp.setStatPoint(args[2], Integer.valueOf(args[3]));
						sender.sendMessage(MessageAPI.SUCCESSFULLY_SET_POINT);
					} else {
						sender.sendMessage(MessageAPI.INCORRECT_POSITIVE_INTEGER);
					}
				} else {
					sender.sendMessage(MessageAPI.CANT_FINT_STATS_NAME);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("dview") && argslen == 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (StatsAPI.isStat(args[2])) {
					StatsPlayer sp = StatsAPI.getStatsPlayer(target);
					String name = args[2];
					sender.sendMessage(MessageAPI.VIEW_USER_STATS.replace("<stats>", name).replace("<point>", String.valueOf(sp.getStatPoint(name))));
				} else {
					sender.sendMessage(MessageAPI.CANT_FINT_STATS_NAME);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("reset") && argslen == 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				if (args[2].equalsIgnoreCase("all")) {
					StatsPlayer sp = StatsAPI.getStatsPlayer(target);
					for (RpgStats rs : StatsAPI.rpgstats.values()) {
						int i = sp.getStatPoint(rs.name);
						sp.setStatPoint(rs.name, 0);
						sp.addAvailablePoint(i);
					}
					if (sp.isOnline()) {
						double health = 20;
						for (RpgStats rs : StatsSet.HEALTH) {
							health += StatsRunAPI.PlayerHealth(sp, rs);
						} // 체력 업데이트
						health += StatsRunAPI.getLoreAttributesBonusHp(sp);
						if (health >= 1) {
							sp.getPlayer().setMaxHealth((int) health);
						}
					}
					sender.sendMessage(MessageAPI.SUCCESSFULLY_RESET_POINT);
				} else if (StatsAPI.isStat(args[2])) {
					StatsPlayer sp = StatsAPI.getStatsPlayer(target);
					RpgStats rs = StatsAPI.getRpgStats(args[2]);
					int i = sp.getStatPoint(rs.name);
					sp.setStatPoint(rs.name, 0);
					sp.addAvailablePoint(i);
					sender.sendMessage(MessageAPI.SUCCESSFULLY_RESET_POINT);
					if (sp.isOnline()) {
						double health = 20;
						for (RpgStats rss : StatsSet.HEALTH) {
							health += StatsRunAPI.PlayerHealth(sp, rss);
						} // 체력 업데이트
						health += StatsRunAPI.getLoreAttributesBonusHp(sp);
						if (health >= 1) {
							sp.getPlayer().setMaxHealth((int) health);
						}
					}
				} else {
					sender.sendMessage(MessageAPI.CANT_FINT_STATS_NAME);
				}
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else if (args[0].equalsIgnoreCase("remove") && argslen == 2) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target != null) {
				StatsPlayer sp = StatsAPI.getStatsPlayer(target);
				for (RpgStats rs : StatsAPI.rpgstats.values()) {
					sp.setStatPoint(rs.name, 0);
				}
				sp.setAvailablePoint(0);
				if (sp.isOnline()) {
					double health = 20;
					for (RpgStats rs : StatsSet.HEALTH) {
						health += StatsRunAPI.PlayerHealth(sp, rs);
					} // 체력 업데이트
					health += StatsRunAPI.getLoreAttributesBonusHp(sp);
					if (health >= 1) {
						sp.getPlayer().setMaxHealth((int) health);
					}
				}
				sender.sendMessage(MessageAPI.SUCCESSFULLY_REMOVE_POINT);
			} else {
				sender.sendMessage(MessageAPI.CANT_FINT_USER);
			}
		} else {
			sender.sendMessage(MessageAPI.INCORRECT_MESSAGE.replace("<cmd>", label));
		}
		return true;
	}
}

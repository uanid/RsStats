package kr.tpsw.rsstats.command;

import kr.tpsw.rsstats.api.MessageAPI;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rsstats.kr.tpsw.api.bukkit.API;
import rsstats.kr.tpsw.api.bukkit.PlayersAPI;

public class StsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int argslen = args.length;
		if (sender instanceof Player) {
			if (argslen == 0) {
				MessageAPI.helpMessageList(sender, MessageAPI.sts, 1, label);
			} else if (API.isInteger(args[0]) && argslen == 1) {
				MessageAPI.helpMessageList(sender, MessageAPI.sts, Integer.valueOf(args[0]), label);
			} else if ((args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("view")) && argslen <= 2) {
				String target;
				if (argslen == 2) {
					target = PlayersAPI.findOfflinePlayerName(args[1]);
				} else {
					target = sender.getName();
				}
				
				if (target == null) {
					sender.sendMessage(MessageAPI.CANT_FINT_USER);
				} else {
					StatsPlayer tsp = StatsAPI.getStatsPlayer(target);
					StatsPlayer sp = StatsAPI.getStatsPlayer(sender.getName());
					if (tsp == sp || tsp.isRelease() || sp.getPlayer().isOp()) {
						try {
							tsp.updateInventory();
						} catch (Exception e) {
							System.err.println(sp.getName() + "(이)가 " + tsp.getName() + "의 스텟을 확인할려던 도중 오류가 발생했습니다.");
							String name = MessageAPI.INVENTORY_NAME.replace("<name>", tsp.getName());
							if (name.length() >= 26) {
								System.err.println("사유: 인벤토리 이름 26자 이상 [" + name + "]");
							} else {
								System.err.println("사유: 알 수 없음");
								e.printStackTrace();
							}
						}
						if (tsp == sp) {
							sp.updateInstValue();
						}
						sp.getPlayer().openInventory(tsp.getInventory());
						if (tsp != sp && (tsp.isRelease() || sp.getPlayer().isOp())) {
							tsp.setInventory();
						}
					} else {
						sender.sendMessage(MessageAPI.NO_RELEASE.replace("<name>", tsp.getName()));
					}
				}
			} else if ((args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("release")) && argslen == 2) {
				if (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("true")) {
					boolean release = Boolean.valueOf(args[1].toLowerCase());
					StatsPlayer sp = StatsAPI.getStatsPlayer(sender.getName());
					sp.setRelease(release);
					sender.sendMessage(MessageAPI.SUCCESSFULLY_SET_RELEASE.replace("<release>", String.valueOf(release)));
				}
			} else if (args[0].equalsIgnoreCase("asdfasdf") && argslen == 2) {

			} else {
				sender.sendMessage(MessageAPI.INCORRECT_MESSAGE.replace("<cmd>", label));
			}
		} else {
			sender.sendMessage(MessageAPI.CANT_CONSOLE_USE_THIS_COMMAND);
		}
		return true;
	}
}

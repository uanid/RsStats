package kr.tpsw.rsstats.command;

import kr.tpsw.rsstats.Main;
import kr.tpsw.rsstats.api.MessageAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Main.sts.onCommand(sender, cmd, label, new String[] { "view" });
		} else {
			sender.sendMessage(MessageAPI.CANT_CONSOLE_USE_THIS_COMMAND);
		}
		return true;
	}
}
package kr.tpsw.rsstats.command;

import java.util.List;
import java.util.Map;

import kr.tpsw.rsstats.TPswParsing;
import kr.tpsw.rsstats.api.MessageAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StsupdateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("이 명령어는 더 이상 작동하지 않습니다");
		if (1 != 2) {
			return true;
		}

		Map<String, List<String>> map = TPswParsing.updateLogMap;
		if (map == null) {
			sender.sendMessage(MessageAPI.CANT_FIND_UPDATE_LOG);
			return true;
		}
		for (String key : map.keySet()) {
			sender.sendMessage(MessageAPI.PLUGINLOG1.replace("<version>", key));
			List<String> list = map.get(key);
			for (String s : list) {
				sender.sendMessage(MessageAPI.PLUGINLOG2.replace("<log>", s));
			}
		}
		return true;
	}
}

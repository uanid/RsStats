package kr.tpsw.rsstats.event;

import kr.tpsw.rsstats.api.MessageAPI;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import to.oa.tpsw.rpgexpsystem.event.RpgPlayerLevelChangeEvent;

public class rpgexpsystemEventBase implements Listener {

	@EventHandler
	public void onRpgPlayerLevelup(RpgPlayerLevelChangeEvent event) {
		int max = event.getNewLevel() - event.getOldLevel();
		if (max > 0 && StatsAPI.rpgexpsystem != 0) {
			StatsPlayer sp = StatsAPI.getStatsPlayer(event.getRpgPlayer().getName());
			String message = MessageAPI.STATS_GIVE_MESSAGE.replace("<point>", String.valueOf(StatsAPI.rpgexpsystem));
			for (int i = 0; i < max; i++) {
				sp.sendMessage(message);
				sp.addAvailablePoint(StatsAPI.rpgexpsystem);
			}
		}
	}
}

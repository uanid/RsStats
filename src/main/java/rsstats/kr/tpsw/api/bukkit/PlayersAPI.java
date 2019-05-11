package rsstats.kr.tpsw.api.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayersAPI implements Listener {

	private static final Set<Player> ONLINE = new HashSet<Player>();
	private static final Set<OfflinePlayer> OFFLINE = new HashSet<OfflinePlayer>();

	// PlayersAPI버전 1.1
	// 1.0 - 이름 변경 (BukkitAPI >> PlayersAPI)
	// 1.1 - 생성자 수정
	// 1.2 - 주석 지움

	@SuppressWarnings("unchecked")
	public static void initLoad(Plugin pl) {
		try {
			Object obj = Bukkit.class.getMethod("getOnlinePlayers", null).invoke(null, null);
			if (obj instanceof Player[]) {
				Player[] pp = (Player[]) obj;
				for (Player p : pp) {
					ONLINE.add(p);
				}
				// System.out.println("배열로 달린다");
			} else if (obj instanceof Iterable<?>) {
				Iterator<Player> pp = ((Iterable<Player>) obj).iterator();
				while (pp.hasNext()) {
					ONLINE.add(pp.next());
				}
				// System.out.println("iterable로 달린다");
			} else {

				// for (int i = 0; i < 10; i++) {
				System.out.println("예외발생 빼애애액!");
				// }
			}

			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				OFFLINE.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}// 플레이어 목록 가져오기

		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onPlayerJoin(PlayerJoinEvent event) {
				ONLINE.add(event.getPlayer());
				OFFLINE.add(event.getPlayer());
			}

			@EventHandler
			public void onPlayerQuit(PlayerQuitEvent event) {
				ONLINE.remove(event.getPlayer());
			}

			@EventHandler
			public void onPlayerKick(PlayerKickEvent event) {
				ONLINE.remove(event.getPlayer());
			}// 여기까지 건드리면 안되는 메서드
		}, pl);
	}

	public static boolean isOnline(String name) {
		OfflinePlayer p = getOfflinePlayer(name);
		if (p == null) {
			return false;
		} else {
			return p.isOnline();
		}
	}

	public static boolean isRegister(String name) {
		for (OfflinePlayer p : OFFLINE) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static Player getPlayer(String name) {
		for (Player p : ONLINE) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public static OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer p : OFFLINE) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public static String findPlayerName(String target) {
		List<String> list1 = new ArrayList<String>();
		target = target.toLowerCase();
		for (int i = 0; i < 16; i++) {
			for (Player player : ONLINE) {
				if (player.getName().toLowerCase().indexOf(target) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}

	public static String findOfflinePlayerName(String target) {
		List<String> list1 = new ArrayList<String>();
		target = target.toLowerCase();
		for (int i = 0; i < 16; i++) {
			for (OfflinePlayer player : OFFLINE) {
				if (player.getName().toLowerCase().indexOf(target) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}

	public static Set<Player> getOnlinePlayers() {
		return ONLINE;
	}

	public static Set<OfflinePlayer> getOfflinePlayers() {
		return OFFLINE;
	}

}

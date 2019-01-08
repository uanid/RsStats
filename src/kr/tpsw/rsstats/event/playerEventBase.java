package kr.tpsw.rsstats.event;

import java.util.List;

import kr.tpsw.rsstats.Main;
import kr.tpsw.rsstats.api.MessageAPI;
import kr.tpsw.rsstats.api.RpgStats;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsAPI.StatsSet;
import kr.tpsw.rsstats.api.StatsPlayer;
import kr.tpsw.rsstats.api.StatsRunAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class playerEventBase implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		StatsPlayer sp;
		if (!StatsAPI.statsplyaer.keySet().contains(name)) {
			StatsAPI.addStatsPlayer(name);
			sp = StatsAPI.getStatsPlayer(name);// 등록되지 않은 유저 추가
		} else {
			sp = StatsAPI.getStatsPlayer(name);
			sp.updatePlayer();// 등록된 유저 업데이트
		}

		double health = 20;
		for (RpgStats rs : StatsSet.HEALTH) {
			health += StatsRunAPI.PlayerHealth(sp, rs);
		} // 체력 업데이트
		health += StatsRunAPI.getLoreAttributesBonusHp(sp);
		if (health >= 1) {
			sp.getPlayer().setMaxHealth((int) health);
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		StatsPlayer sp = StatsAPI.getStatsPlayer(event.getPlayer().getName());
		double health = 20;
		for (RpgStats rs : StatsSet.HEALTH) {
			health += StatsRunAPI.PlayerHealth(sp, rs);
		} // 체력 업데이트
		health += StatsRunAPI.getLoreAttributesBonusHp(sp);
		if (health >= 1) {
			sp.getPlayer().setMaxHealth((int) health);
		}
		//System.out.println("리스폰 호출");
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		//System.out.println("데스 호출");
	}

	@EventHandler
	public void onCommandProcess(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().startsWith("/rpgstatssystem")) {
			Player sender = event.getPlayer();
			sender.sendMessage("§6플러그인 버전: §c" + Main.VERSION);
			sender.sendMessage("§6제작자: §cTPsw");
			sender.sendMessage("§6서버 버전: §c" + Bukkit.getVersion());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		StatsAPI.getStatsPlayer(event.getPlayer().getName()).updatePlayer();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		StatsAPI.getStatsPlayer(event.getPlayer().getName()).updatePlayer();
	}

	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event) {
		if (!event.isCancelled()) {
			StatsPlayer sp = StatsAPI.getStatsPlayer(event.getPlayer().getName());
			for (RpgStats rs : StatsSet.FOOD) {
				StatsRunAPI.ItemConsume(sp, rs, event);
			}
		}
	}

	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent event) {
		int max = event.getNewLevel() - event.getOldLevel();
		if (max > 0 && StatsAPI.levelup != 0) {
			StatsPlayer sp = StatsAPI.getStatsPlayer(event.getPlayer().getName());
			String message = MessageAPI.STATS_GIVE_MESSAGE.replace("<point>", String.valueOf(StatsAPI.levelup));
			for (int i = 0; i < max; i++) {
				sp.sendMessage(message);
				sp.addAvailablePoint(StatsAPI.levelup);
			}
		}
	}

	@EventHandler
	public void onInven(InventoryClickEvent event) {
		if (!event.isCancelled()) {
			Inventory inv = event.getInventory();
			if (inv.getName().startsWith(StatsAPI.NAMECODE)) {
				event.setCancelled(true);
				HumanEntity player = event.getWhoClicked();
				if (inv.getName().equals(StatsAPI.NAMECODE + MessageAPI.INVENTORY_NAME.replace("<name>", player.getName()))) {
					// 자기 인벤토리일 경우
					ItemStack is = event.getCurrentItem();
					if (is != null && is.getTypeId() != 0) {
						// 공기가 아니라면
						int slot = event.getRawSlot();
						if (slot == StatsAPI.STATS_POINT_LOC) {
							// 남은 스텟 버튼을 눌렀을 경우
						} else if (slot == StatsAPI.SAVE_STATS_LOC) {
							StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
							sp.exitInstValue();
							player.closeInventory();
							// 스텟 저장 버튼
						} else if (slot == -999) {
							// 인벤창 이외의 버튼을 눌렀을 경우
						} else {
							for (RpgStats rs : StatsAPI.rpgstats.values()) {
								if (slot == (rs.x - 1) + (rs.y - 1) * 9) {
									// 슬롯 위치가 일치할 경우
									StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
									if (sp.instavail >= 1) {
										// 잉여 스텟이 1 이상이라면
										if (rs.max == -1 || rs.max > sp.inststats.get(rs.name)) {
											// 최댓값이 무한이거나 스텟 포인트가 최댓값 이하의 숫자라면
											sp.instavail -= 1;
											int stats = sp.inststats.get(rs.name) + 1;
											sp.inststats.put(rs.name, stats);

											ItemMeta im = is.getItemMeta();
											List<String> list = im.getLore();
											list.set(list.size() - 1, StatsAPI.replaceNowStats(stats, rs.max));
											im.setLore(list);
											is.setItemMeta(im);
											inv.setItem(slot, is);

											is = inv.getItem(StatsAPI.STATS_POINT_LOC);
											is.getItemMeta();
											list = im.getLore();
											list.set(list.size() - 1, MessageAPI.AVILABLESTATS.replace("<point>", String.valueOf(sp.instavail)));
											im.setLore(list);
											is.setItemMeta(im);
											inv.setItem(StatsAPI.STATS_POINT_LOC, is);
										}
									}
									return;
								}
							}
						}
					}
				} else {
					// 남 인벤토리일 경우
				}
			}
		}
	}

	// @EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.isCancelled()) {
			Inventory inv = event.getInventory();
			if (inv.getName().startsWith(StatsAPI.NAMECODE)) {
				event.setCancelled(true);
				// System.out.println("클릭 취소");
				HumanEntity player = event.getWhoClicked();
				if (inv.getName().equals(StatsAPI.NAMECODE + MessageAPI.INVENTORY_NAME.replace("<name>", player.getName()))) {
					ItemStack is = event.getCurrentItem();

					if (is != null && is.getTypeId() != 0) {
						int slot = event.getSlot();
						if (slot == StatsAPI.STATS_POINT_LOC) {
							// 남은 스텟 버튼
						} else if (slot == StatsAPI.SAVE_STATS_LOC) {
							StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
							sp.exitInstValue();
							player.closeInventory();
							// 스텟 저장 버튼
						} else if (slot >= 26 || slot <= -1) {
							// 자기 인벤창을 눌렀을 경우, 이 소스에서는 CTRL+SHIFT+F 누르지 말것
						} else {
							for (RpgStats rs : StatsAPI.rpgstats.values()) {
								if (slot == (rs.x - 1) + (rs.y - 1) * 9) {// 슬롯
																			// 위치가
																			// 일치할
																			// 경우
									String dname;
									if (is.getItemMeta() != null) {
										dname = is.getItemMeta().getDisplayName();
									} else {
										dname = null;
									}// 무슨 소스더라

									if (dname != null && dname.equals(rs.is.getItemMeta().getDisplayName())) {// 스텟
																												// 아이템인가?
										StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
										if (sp.instavail >= 1) { // 잉여 스텟이 1
																	// 이상이라면
											if (rs.max == -1 || rs.max > sp.inststats.get(rs.name)) {
												sp.instavail -= 1;
												int stats = sp.inststats.get(rs.name) + 1;
												sp.inststats.put(rs.name, stats);

												ItemMeta im = is.getItemMeta();
												List<String> list = im.getLore();
												list.set(list.size() - 1, StatsAPI.replaceNowStats(stats, rs.max));
												im.setLore(list);
												is.setItemMeta(im);
												inv.setItem(slot, is);

												is = inv.getItem(StatsAPI.STATS_POINT_LOC);
												is.getItemMeta();
												list = im.getLore();
												list.set(list.size() - 1, MessageAPI.AVILABLESTATS.replace("<point>", String.valueOf(sp.instavail)));
												im.setLore(list);
												is.setItemMeta(im);
												inv.setItem(StatsAPI.STATS_POINT_LOC, is);
											}
										}
										// System.out.println("혼또다!");
									} else {
										// System.out.println("니세모노다!");
									}
									return;
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		HumanEntity player = event.getPlayer();
		StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
		Inventory inv = event.getInventory();
		if (inv.getName().startsWith(StatsAPI.NAMECODE)) {
			if (inv.getName().equals(StatsAPI.NAMECODE + MessageAPI.INVENTORY_NAME.replace("<name>", player.getName()))) {
				sp.resetInstValue();
				sp.setInventory();
			}
		} // 스텟 수정 종료

		double health = 20;
		for (RpgStats rs : StatsSet.HEALTH) {
			health += StatsRunAPI.PlayerHealth(sp, rs);
		} // 체력 업데이트
		health += StatsRunAPI.getLoreAttributesBonusHp(sp);
		if (health >= 1) {
			sp.getPlayer().setMaxHealth((int) health);
		}
	}
}

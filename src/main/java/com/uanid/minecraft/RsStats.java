package com.uanid.minecraft;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.uanid.minecraft.api.MessageAPI;
import com.uanid.minecraft.api.StatsAPI;
import com.uanid.minecraft.api.StatsCmdAPI;
import com.uanid.minecraft.api.StatsType;
import com.uanid.minecraft.event.blockEventBase;
import com.uanid.minecraft.event.entityEventBase;
import com.uanid.minecraft.event.playerEventBase;
import com.uanid.minecraft.command.RsStatsCommand;
import com.uanid.minecraft.command.StatsCommand;
import com.uanid.minecraft.command.StsCommand;
import com.uanid.minecraft.command.Stsadmin2Command;
import com.uanid.minecraft.command.StsadminCommand;
import com.uanid.minecraft.command.StscmdCommand;
import com.uanid.minecraft.command.StsgiveCommand;
import com.uanid.minecraft.command.StsupdateCommand;
import com.uanid.minecraft.command.StsuserCommand;
import com.uanid.minecraft.config.YamlConfiguration;

import com.uanid.minecraft.service.NaverBlogParser;
import com.uanid.minecraft.service.WordPressBlogParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import rsstats.kr.tpsw.api.bukkit.PlayersAPI;

public class RsStats extends JavaPlugin {

	public final static String VERSION = "2.5.0";
	public static RsStats plugin;
	public static final String lang = "kr";

	public static YamlConfiguration config;
	public static YamlConfiguration message;
	public static YamlConfiguration testu;
	public static YamlConfiguration tests;

	public static StsCommand sts = null;

	public static void main(String[] args){

	}

	public void onEnable() {
		boolean b = false;
		try {
			b = NaverBlogParser.enableCheck();
			plugin = this;

			{// RpgStatsSystem에서 RsStats로 이동
				File f = new File("plugins\\RsStats");
				if (f.exists()) {
					for (File file : f.listFiles()) {
						if (file.isFile()) {
							file.renameTo(new File("plugins\\RsStats\\" + file.getName()));
						}
					}
				}// 폴더가 있을 경우
			}

			config = new YamlConfiguration("plugins\\RsStats\\config.yml");
			message = new YamlConfiguration("plugins\\RsStats\\message.yml");
			PlayersAPI.initLoad(this);

			getCommand("rsstats").setExecutor(new RsStatsCommand());
			getCommand("sts").setExecutor((sts = new StsCommand()));
			getCommand("stsadmin").setExecutor(new StsadminCommand());
			getCommand("stsuser").setExecutor(new StsuserCommand());
			getCommand("stsgive").setExecutor(new StsgiveCommand());
			getCommand("stsadmin2").setExecutor(new Stsadmin2Command());
			getCommand("stscmd").setExecutor(new StscmdCommand());
			getCommand("stsupdate").setExecutor(new StsupdateCommand());
			getCommand("스텟").setExecutor(new StatsCommand());

			WordPressBlogParser.initRegister(this, getCommand("stsupdatenow"), this.getFile());
			
			PluginManager pm = Bukkit.getPluginManager();

			pm.registerEvents(new blockEventBase(), this);
			pm.registerEvents(new entityEventBase(), this);
			pm.registerEvents(new playerEventBase(), this);

			this.setDataFile();
			this.loadDataFile();// 데이터 로드, 설정
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[RsStats] " + MessageAPI.PLUGIN_LOAD_ERROR);
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		if (!b) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		} // 인증 실패시 플러그인 종료
		System.out.println("[RsStats] " + MessageAPI.PLUGIN_LOAD_SUCCESSFULLY);
	}

	public void onDisable() {
		try {
			StatsCmdAPI.serialize();
			StatsAPI.saveData();
			StatsAPI.saveStatsItem();
			config.saveYaml();
			message.saveYaml();
		} catch (Exception e) {

		}
	}

	private void loadDataFile() {
		StatsCmdAPI.deserialize();
		StatsCmdAPI.checkNull();
		MessageAPI.updateMessageAPIs();
		StatsAPI.loadStatsItem();
		StatsAPI.loadData();
		StatsAPI.updateRpgStatsHastSet();
	}// 데이터 파일 로드

	private void setDataFile() {
		int version = config.getInt("version");
		if (version <= 0) {
			config.set("version", 1);
			version = 1;
			StatsAPI.saveFirstStatsItem();
			{
				ItemStack is = getItemstack(Material.DIAMOND_SWORD, "§e1포인트당 공격력 1 상승", "공격력");
				StatsAPI.addRpgStats("공격스텟", 2, 2, StatsType.DAMAGE, 1, is);
				is = getItemstack(Material.DIAMOND_CHESTPLATE, "§e1포인트당 방어력 1 상승", "방어력");
				StatsAPI.addRpgStats("방어스텟", 4, 2, StatsType.DEFENSE, 1, is);
				is = getItemstack(Material.APPLE, "§e2포인트당 체력 1 상승", "체력");
				StatsAPI.addRpgStats("체력스텟", 6, 2, StatsType.HEALTH, 0.5, is);
				is = getItemstack(Material.DIAMOND_AXE, "§e1포인트당 치명타 확률 0.1%상승", "치명타");
				StatsAPI.addRpgStats("치명타스텟", 8, 2, StatsType.DAMAGE, 0.1d, is);
			}
		}
		if (version <= 4) {
			config.set("version", 5);
			version = 5;
			StatsCmdAPI.firstSetMap();
		}
		if (version <= 5) {
			config.set("version", 6);
			version = 6;
			config.set("auto_save_time", 600);
		}
		if (version <= 7) {
			config.set("version", 8);
			version = 8;
			MessageAPI.saveMessageConfig();
		}
	}// 데이터파일 버전 확인

	private ItemStack getItemstack(Material mat, String lore, String name) {
		ItemStack is = new ItemStack(mat);
		ItemMeta im = is.getItemMeta();
		List<String> list = new LinkedList<String>();
		list.add(lore);
		im.setLore(list);
		im.setDisplayName("§2[§a " + name + " §2]");
		is.setItemMeta(im);
		return is;
	}
}
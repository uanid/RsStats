package com.uanid.minecraft.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class WordPressBlogParser {
	public final static List<String> updateLogList;
	public final static Map<String, List<String>> updateLogMap;
	public final static List<String> blacklistsIp;
	public final static List<String> blacklistsVersion;

	public final static String PLUGIN_NAME;
	public final static String PLUGIN_NAME_LOWER;
	public final static String PLUGIN_VERSION;

	public final static String PLUGIN_UPDATE_URL;
	public final static String IP;

	public final static boolean PLUGIN_IS_FINAL_VERSION;
	public final static boolean PLUGIN_IS_REGISTERED_BLACKLIST_IP;
	public final static boolean PLUGIN_IS_REGISTERED_BLACKLIST_VERSION;

	// 1.0 버전 표기 없는것
	// 1.1 업데이트 커맨드에 메세지 바꿈
	// 1.2 blacklist ip,version기능 추가
	// 1.3 플러그인 버전 체크 기능 강화
	// 1.4 인터넷 끊긴 경우도 작동하게 수정, 업데이트 기능 강화

	public static void main(String[] args) {
		Map<String, List<String>> map = WordPressBlogParser.updateLogMap;
		System.out.println("업데이트 필요: " + !PLUGIN_IS_FINAL_VERSION);
		if (map == null) {
			System.out.println("못찾았다");
		}
		for (String key : map.keySet()) {
			System.out.println("<version>:".replace("<version>", key));
			List<String> list = map.get(key);
			for (String s : list) {
				System.out.println("  - <log>".replace("<log>", s));
			}
		}
		System.out.println("ip=" + IP);
		System.out.println("update=" + PLUGIN_UPDATE_URL);
		enableCheck();
	}

	static {
		PLUGIN_VERSION = "1.6.3";
		PLUGIN_NAME = "RsStats";
		updateLogList = new LinkedList<String>();
		PLUGIN_NAME_LOWER = PLUGIN_NAME.toLowerCase();

		String postid = "34";
		String posturl = "http://tpsw.or.kr/" + postid;
		StringBuilder sb = new StringBuilder();
		List<String> postlist = new LinkedList<String>();

		int i1 = 0;
		int i2 = 0;
		StringBuilder sb2;
		String tag;
		String[] args;
		boolean isConnected = true;

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(posturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line).append('\n');
			}
		} catch (Exception e) {
			if (e instanceof UnknownHostException) {
				System.out.println("[" + PLUGIN_NAME + "] 업데이트 페이지와 연결할 수 없습니다. UnknownHostException");
			} else {
				e.printStackTrace();
			}
			isConnected = false;
		}
		// stream가져오기

		if (isConnected == true && builder.indexOf("Manager:TPsw") == -1) {
			System.out.println("[" + PLUGIN_NAME + "] 업데이트 페이지가 누락되어 있습니다.");
			isConnected = false;
		}

		if (isConnected) {
			{
				tag = "ip";
				i1 = builder.indexOf("<" + tag + ">");
				i2 = builder.indexOf("</" + tag + ">");
				if (i1 == -1 || i2 == -1) {
					IP = null;
				} else {
					IP = builder.substring(i1 + tag.length() + 2, i2);
				}
			}// ip가져오기

			{
				i1 = builder.indexOf("<div class=\"post-content\">");
				builder.delete(0, i1);
				i1 = builder.indexOf("<p>");
				builder.delete(0, i1 + 3);
				i1 = builder.indexOf("</div>");
				args = builder.substring(0, i1).replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("<p>", "").replace("\n", "").trim().split("</p>");
				for (String line : args) {
					postlist.add(line);
					sb.append(line).append('\n');
				}
			}// 게시물 부분만 자르기

			{
				i1 = sb.indexOf("<" + PLUGIN_NAME_LOWER + ">");
				i2 = sb.indexOf("</" + PLUGIN_NAME_LOWER + ">");
				sb2 = new StringBuilder(sb.substring(i1 + PLUGIN_NAME_LOWER.length() + 3, i2 - 1));
			}// 플러그인 부분 자르기

			{
				tag = "update";
				i1 = sb2.indexOf("<" + tag + ">");
				i2 = sb2.indexOf("</" + tag + ">");
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
				updateLogMap = new LinkedHashMap<String, List<String>>();
				List<String> inst = null;
				String name = null;
				for (String str : args) {
					updateLogList.add(str);
					if (str.contains(":")) {
						inst = new LinkedList<String>();
						name = str.replace(":", "");
						updateLogMap.put(name, inst);
					} else {
						inst.add(str.replace("-", ""));
					}
				}
			}// 업데이트 로그

			{
				tag = "download";
				i1 = sb2.indexOf("<" + tag + ">");
				i2 = sb2.indexOf("</" + tag + ">");
				if (i1 == -1 || i2 == -1) {
					// 넘어간다
					PLUGIN_UPDATE_URL = null;
				} else {
					PLUGIN_UPDATE_URL = sb2.substring(i1 + tag.length() + 3, i2 - 1);
				}
			}// 다운로드 주소

			{
				tag = "blacklist-ip";
				i1 = sb2.indexOf("<" + tag + ">");
				i2 = sb2.indexOf("</" + tag + ">");
				if (i1 == -1 || i2 == -1) {
					blacklistsIp = null;
				} else {
					args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
					blacklistsIp = Arrays.asList(args);
				}
			}// 블랙리스트 아이피 주소

			{
				tag = "blacklist-version";
				i1 = sb2.indexOf("<" + tag + ">");
				i2 = sb2.indexOf("</" + tag + ">");
				if (i1 == -1 || i2 == -1) {
					blacklistsVersion = null;
				} else {
					args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
					blacklistsVersion = Arrays.asList(args);
				}
			}// 블랙리스트 버전

			if (blacklistsIp != null && blacklistsIp.size() > 0) {
				if (blacklistsIp.get(0).equals("*")) {
					PLUGIN_IS_REGISTERED_BLACKLIST_IP = true;
				} else {
					PLUGIN_IS_REGISTERED_BLACKLIST_IP = blacklistsIp.contains(IP);
				}// 아이피 체크
			} else {
				PLUGIN_IS_REGISTERED_BLACKLIST_IP = false;
			}

			if (blacklistsVersion != null && blacklistsVersion.size() > 0) {
				if (blacklistsVersion.get(0).equals("*")) {
					PLUGIN_IS_REGISTERED_BLACKLIST_VERSION = true;
				} else {
					PLUGIN_IS_REGISTERED_BLACKLIST_VERSION = blacklistsVersion.contains(PLUGIN_VERSION);
				}// 버전 체크
			} else {
				PLUGIN_IS_REGISTERED_BLACKLIST_VERSION = false;
			}

			boolean bool = true;
			double plugin_double_version = trasferVersion(PLUGIN_VERSION);
			for (String ver : WordPressBlogParser.updateLogMap.keySet()) {
				double logversion = trasferVersion(ver);
				if (logversion <= plugin_double_version) {
					// bool = true;
				} else {
					bool = false;
				}
			}

			PLUGIN_IS_FINAL_VERSION = bool;
			// 최신 버전 확인
		} else {
			updateLogMap = new HashMap<String, List<String>>();
			blacklistsIp = new ArrayList<String>();
			blacklistsVersion = new ArrayList<String>();
			PLUGIN_UPDATE_URL = null;
			IP = "null";
			PLUGIN_IS_FINAL_VERSION = true;
			PLUGIN_IS_REGISTERED_BLACKLIST_IP = false;
			PLUGIN_IS_REGISTERED_BLACKLIST_VERSION = false;
		}

	}

	public static void wordPressDownload(String surl, File file) {
		try {
			URL url = new URL(surl);
			URLConnection urlc = url.openConnection();
			InputStream is = urlc.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int r;
			byte[] buffer = new byte[1024];
			while ((r = bis.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, r);
			}
			bos.close();
			bis.close();
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	public static double trasferVersion(String version) {
		StringBuilder sb = new StringBuilder();
		for (char c : version.toCharArray()) {
			if (('0' <= c && c <= '9') || c == '.') {
				sb.append(c);
			}
		}
		version = sb.toString();
		if (version.indexOf('.') > -1) {
			int i1 = version.indexOf('.');
			String s = new StringBuilder(version).substring(i1 + 1, version.length()).replace(".", "");
			return Double.valueOf(version.substring(0, i1) + "." + s);
		} else {
			return Double.valueOf(version);
		}
	}

	private static boolean enableCheck() {
		if (PLUGIN_IS_REGISTERED_BLACKLIST_VERSION) {
			System.out.println("[" + PLUGIN_NAME + "] 플러그인이 블랙리스트에 등록되어 있습니다.");
			return false;
		}
		if (PLUGIN_IS_REGISTERED_BLACKLIST_IP) {
			System.out.println("[" + PLUGIN_NAME + "] 서버가 블랙리스트에 등록되어 있습니다.");
			return false;
		}
		if (PLUGIN_IS_FINAL_VERSION) {
			System.out.println("[" + PLUGIN_NAME + "] 플러그인이 최신 버전입니다.");
		} else {
			System.out.println("[" + PLUGIN_NAME + "] 플러그인이 최신 버전이 아닙니다.");
		}
		return true;
	}

	public static boolean initRegister(Plugin pl, final PluginCommand pc, final File file) {
		if (enableCheck() == false) {
			System.out.println("[" + PLUGIN_NAME + "]플러그인을 강제로 비활성화시킵니다.");
			Bukkit.getPluginManager().disablePlugin(pl);
			return false;
		}

		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onJoin(PlayerJoinEvent event) {
				Player player = event.getPlayer();
				if ((player.hasPermission(PLUGIN_NAME_LOWER + ".admin") || player.isOp()) && !PLUGIN_IS_FINAL_VERSION) {
					player.sendMessage("§6" + PLUGIN_NAME + "플러그인의 새 버전이 발견되었습니다. 명령어:" + pc.getLabel());
				}
			}
		}, pl);
		if (PLUGIN_UPDATE_URL != null) {
			pc.setExecutor(new CommandExecutor() {
				@Override
				public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
					if (args.length == 0) {
						sender.sendMessage("§6/" + label + " true");
						sender.sendMessage("§f플러그인의 최신 버전을 자동으로 내려받습니다.");
						sender.sendMessage("§f서버를 재부팅 하게 될 경우 플러그인이 새 버전으로 적용됩니다.");
					} else if (args[0].equals("true")) {
						sender.sendMessage("§f플러그인 다운로드 중...");
						wordPressDownload(PLUGIN_UPDATE_URL, new File(file.getPath()));
						sender.sendMessage("§f플러그인의 최신 버전을 자동으로 내려받았습니다.");
						sender.sendMessage("§f서버를 재부팅 하게 될 경우 플러그인이 새 버전으로 적용됩니다.");
					} else {
						sender.sendMessage("§c올바른 명령어를 입력하세요.");
					}
					return false;
				}
			});
		}
		return true;
	}
}

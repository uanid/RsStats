package kr.tpsw.rsstats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TPswParsing {
	public final static List<String> updateLogList;
	public final static Map<String, List<String>> updateLogMap;
	public static String localip;

	private final static String id;
	private final static String postid;
	private final static String plugin;
	private final static String posturl;
	private static StringBuilder sb;
	private static List<String> postList;

	public final static String PLUGIN_NAME;
	public final static boolean PLUGIN_IS_FINAL_VERSION;
	public final static String PLUGIN_VERSION;

	public static void main(String[] args) {
		Map<String, List<String>> map = TPswParsing.updateLogMap;
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
	}

	static {
		PLUGIN_VERSION = "1.6.3";
		PLUGIN_NAME = "RpgStatsSystem";
		id = "songminwooki";
		postid = "220293914914";
		plugin = PLUGIN_NAME.toLowerCase();
		posturl = "http://blog.naver.com/PostView.nhn?blogId=" + id + "&logNo=" + postid + "&redirect=Dlog&widgetTypeCall=true";
		updateLogList = new LinkedList<String>();
		postList = new LinkedList<String>();
		sb = new StringBuilder();
		int i1 = 0;
		int i2 = 0;
		StringBuilder sb2;

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(posturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line).append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		i1 = builder.indexOf("<div id=\"post-view");
		builder.delete(0, i1);
		i1 = builder.indexOf("<p>");
		builder.delete(0, i1 + 3);
		i1 = builder.indexOf("\n");
		String[] args = builder.substring(0, i1).replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("<p>", "").split("</p>");
		for (String line : args) {
			postList.add(line);
			sb.append(line).append('\n');
		}

		i1 = sb.indexOf("<" + plugin + ">");
		i2 = sb.indexOf("</" + plugin + ">");
		sb2 = new StringBuilder(sb.substring(i1 + plugin.length() + 3, i2 - 1));

		i1 = sb2.indexOf("<update>");
		i2 = sb2.indexOf("</update>");
		args = sb2.substring(i1 + 6 + 3, i2 - 1).split("\n");
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

		Set<String> set = TPswParsing.updateLogMap.keySet();
		PLUGIN_IS_FINAL_VERSION = set.toArray()[set.size() - 1].equals(PLUGIN_VERSION);
	}

	public static boolean enableCheck() {
		if (PLUGIN_IS_FINAL_VERSION) {
			System.out.println("[RpgStatsSystem] 플러그인이 최신 버전입니다.");
		} else {
			System.out.println("[RpgStatsSystem] 플러그인이 최신 버전이 아닙니다.");
		}
		return true;
	}
}

package kr.tpsw.rsstats.api;

import java.util.LinkedList;
import java.util.List;

import kr.tpsw.rsstats.RsStats;
import kr.tpsw.rsstats.RsStats;
import kr.tpsw.rsstats.YamlConfiguration;

import org.bukkit.command.CommandSender;

public class MessageAPI {

	public static List<String> sts;
	public static List<String> stsadmin;
	public static List<String> stsadmin2;
	public static List<String> stscmd;
	public static List<String> stsgive;
	public static List<String> stsuser;

	public static String CANT_FIND_MESSAGELIST;// 목록을 찾을수가 없습니다.
	public static String CANT_FINT_STATS_NAME;// 해당된 이름의 스텟을 찾을수가 없습니다.
	public static String CANT_FINT_USER;// 해당 유저를 찾을수가 없습니다.
	public static String CANT_CONSOLE_USE_THIS_COMMAND;// 콘솔은 해당 명령어의 사용이
	// 불가능합니다.

	public static String NEXT_MESSAGE;// 다음 목록을 보려면<cmd> <index>
	public static String LIST_INDEX_MESSAGE;// <n>개를 찾았습니다. <index>/<all>
	public static String LIST_INDEX;// <index>: <message>
	public static String STATS_LORE_NOW;// 현재 스탯: <point> / <max>
	public static String ALREADY_HAS_STATS_NAME;// 이미 존재하는 스텟의 이름입니다
	public static String PLZ_ENTER_CORRECT_X_Y;// x에는 1~9를 y에는 1~3을 입력하십시오.
	public static String INVENTORY_NAME;// §c<name>§6님의 스텟
	public static String NO_RELEASE;// <target>님의 스텟 공개여부는 off입니다.
	public static String AVILABLESTATS;// 남은 스텟: <point>

	public static String INCORRECT_MESSAGE;// 잘못된 명령어입니다. <cmd>
	public static String INCORRECT_STATS_TYPE;// 올바른 스텟 타입을 입력하십시오.
	public static String INCORRECT_COEFFICIENT;// 올바른 스텟 계수를 입력하십시오.
	public static String INCORRECT_LIST_INDEX;// 해당 번호의 lore는 존재하지 않습니다.
	public static String INCORRECT_ITEM_CODE;// 올바르지 않은 아이템 코드.
	public static String INCORRECT_POSITIVE_INTEGER;// 올바른 정수를 적으십시오.
	public static String INCORRECT_X_Y_INTEGER;// x와 y에 올바른 정수를 적으십시오.

	public static String RUN_CRITICAL; // 크리티컬 데미지!
	public static String RUN_BREAK; // 블럭 드랍 두배!
	public static String RUN_PLACE; // 아이템 복구!
	public static String RUN_ITEM; // 음식 복구!
	public static String INFINITE; // 무한
	public static String AUTO_SAVE; // [RpgStatsSystem] 스텟 데이터 자동 저장 완료

	// stsadmin
	public static String SUCCESSFULLY_ADD_STATS;// 성공적으로 스텟을 추가했습니다.
	public static String SUCCESSFULLY_EDIT_STATS;// 성공적으로 스텟을 수정했습니다.
	public static String SUCCESSFULLY_REMOVE_STATS;// 성공적으로 스텟을 지웠습니다.
	public static String SUCCESSFULLY_ADDLORE_STATS;// 성공적으로 lore를 추가했습니다.
	public static String SUCCESSFULLY_REMOVELORE_STATS;// 성공적으로 lore를 지웠습니다.
	public static String SUCCESSFULLY_SETITEM_STATS;// 성공적으로 아이템 코드를 설정했습니다.
	public static String SUCCESSFULLY_SETITEMNAME_STATS;// 성공적으로 아이템 이름을 설정했습니다.
	public static String SUCCESSFULLY_SETMAX_STATS;// 성공적으로 최대 스텟량을 설정했습니다.

	// sts, stsgive
	public static String SUCCESSFULLY_SET_RELEASE;// 성공적으로 공개 여부를 <release>로
	// 설정했습니다.
	public static String STATS_GIVE_MESSAGE;// 당신은 <point>만큼 스텟을 받았습니다.

	// stsuser
	public static String SUCCESSFULLY_ADD_POINT;// 성공적으로 해당 유저의 스텟 포인트를 추가했습니다.
	public static String SUCCESSFULLY_SET_POINT;// 성공적으로 해당 유저의 스텟 포인트를 설정했습니다.
	public static String VIEW_USER_STATS;// <stats> >> <point>
	public static String SUCCESSFULLY_RESET_POINT;// 성공적으로 해당 유저의 스텟 포인트를 초기화했습니다.
	public static String SUCCESSFULLY_REMOVE_POINT;// 성공적으로 해당 유저의 스텟 포인트를 삭제했습니다.

	// stsadmin2
	public static String INCORRECT_BONUS_TYPE;// 올바른 보너스 이름을 입력하십시오.
	public static String SUCCESSFULLY_SET_BONUS;// 성공적으로 스텟 보너스를 설정했습니다.
	public static String SUCCESSFULLY_RELOAD;// 성공적으로 파일을 리로드했습니다.
	public static String SUCCESSFULLY_SAVE;// 성공적으로 파일을 저장했습니다.
	public static String VIEW_STATS_BONUS;// <type>: <bonus>
	public static String INCORRECT_FILE_NAME;// 올바른 파일 이름을 입력하십시오.
	public static String KICK_MESSAGE;// 데이터 파일 로드시 안정성을 위하여 강제로 추방합니다.

	// stscmd
	public static String SUCCESSFULLY_CMD_SET;// 성공적으로 명령어를 예약했습니다.
	public static String SUCCESSFULLY_CMD_REMOVE;// 성공적으로 예약한 명령어를 지웠습니다.
	public static String INCORRECT_CMD_TYPE;// 올바르지 않은 명령어 타입입니다.

	// stsupdate
	public static String CANT_FIND_UPDATE_LOG;
	public static String PLUGINLOG1;
	public static String PLUGINLOG2;

	// stscmd
	public static String STSCMD_TYPE;
	public static String STSCMD_CMD;

	public static String PLUGIN_SEARCH;
	public static String PLUGIN_CANT_SEARCH;
	public static String PLUGIN_LOAD_ERROR;
	public static String PLUGIN_LOAD_SUCCESSFULLY;

	static {
		if (RsStats.lang.equals("kr")) {
			PLUGIN_SEARCH = "<plugin> 플러그인 발견!";
			PLUGIN_CANT_SEARCH = "<plugin> 플러그인 검색 불가!";
			PLUGIN_LOAD_ERROR = "플러그인 로드 중 오류 발생";
			PLUGIN_LOAD_SUCCESSFULLY = "플러그인 정상 로드 완료";
		} else if (RsStats.lang.equals("us")) {
			PLUGIN_SEARCH = "Found <plugin>!";
			PLUGIN_CANT_SEARCH = "Cant found <plugin>!";
			PLUGIN_LOAD_ERROR = "Error occurred while loading plugin";
			PLUGIN_LOAD_SUCCESSFULLY = "Successfully plugin load";
		}
		if (RsStats.lang.equals("kr")) {
		} else if (RsStats.lang.equals("us")) {
		}
	}

	public static void updateMessageAPIs() {
		YamlConfiguration me = RsStats.message;
		sts = me.getStringList("command.sts.help");
		stsadmin = me.getStringList("command.stsadmin.help");
		stsadmin2 = me.getStringList("command.stsadmin2.help");
		stscmd = me.getStringList("command.stscmd.help");
		stsgive = me.getStringList("command.stsgive.help");
		stsuser = me.getStringList("command.stsuser.help");

		CANT_FIND_MESSAGELIST = me.getString("command.general.cant.CANT_FIND_MESSAGELIST");
		CANT_FINT_STATS_NAME = me.getString("command.general.cant.CANT_FINT_STATS_NAME");
		CANT_FINT_USER = me.getString("command.general.cant.CANT_FINT_USER");
		CANT_CONSOLE_USE_THIS_COMMAND = me.getString("command.general.cant.CANT_CONSOLE_USE_THIS_COMMAND");

		NEXT_MESSAGE = me.getString("command.general.etc.NEXT_MESSAGE");
		LIST_INDEX_MESSAGE = me.getString("command.general.etc.LIST_INDEX_MESSAGE");
		LIST_INDEX = me.getString("command.general.etc.LIST_INDEX");
		STATS_LORE_NOW = me.getString("command.general.etc.STATS_LORE_NOW");
		ALREADY_HAS_STATS_NAME = me.getString("command.general.etc.ALREADY_HAS_STATS_NAME");
		PLZ_ENTER_CORRECT_X_Y = me.getString("command.general.etc.PLZ_ENTER_CORRECT_X_Y");
		INVENTORY_NAME = me.getString("command.general.etc.INVENTORY_NAME");
		NO_RELEASE = me.getString("command.general.etc.NO_RELEASE");
		AVILABLESTATS = me.getString("command.general.etc.AVILABLESTATS");
		RUN_CRITICAL = me.getString("command.general.etc.RUN_CRITICAL");
		RUN_BREAK = me.getString("command.general.etc.RUN_BREAK");
		RUN_PLACE = me.getString("command.general.etc.RUN_PLACE");
		RUN_ITEM = me.getString("command.general.etc.RUN_ITEM");
		INFINITE = me.getString("command.general.etc.INFINITE");
		AUTO_SAVE = me.getString("command.general.etc.AUTO_SAVE");

		INCORRECT_MESSAGE = me.getString("command.general.incorrect.INCORRECT_MESSAGE");
		INCORRECT_STATS_TYPE = me.getString("command.general.incorrect.INCORRECT_STATS_TYPE");
		INCORRECT_COEFFICIENT = me.getString("command.general.incorrect.INCORRECT_COEFFICIENT");
		INCORRECT_LIST_INDEX = me.getString("command.general.incorrect.INCORRECT_LIST_INDEX");
		INCORRECT_ITEM_CODE = me.getString("command.general.incorrect.INCORRECT_ITEM_CODE");
		INCORRECT_POSITIVE_INTEGER = me.getString("command.general.incorrect.INCORRECT_POSITIVE_INTEGER");
		INCORRECT_X_Y_INTEGER = me.getString("command.general.incorrect.INCORRECT_X_Y_INTEGER");

		SUCCESSFULLY_ADD_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_ADD_STATS");
		SUCCESSFULLY_EDIT_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_EDIT_STATS");
		SUCCESSFULLY_REMOVE_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_REMOVE_STATS");
		SUCCESSFULLY_ADDLORE_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_ADDLORE_STATS");
		SUCCESSFULLY_REMOVELORE_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_REMOVELORE_STATS");
		SUCCESSFULLY_SETITEM_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_SETITEM_STATS");
		SUCCESSFULLY_SETITEMNAME_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_SETITEMNAME_STATS");
		SUCCESSFULLY_SETMAX_STATS = me.getString("command.stsadmin.general.SUCCESSFULLY_SETMAX_STATS");

		SUCCESSFULLY_SET_RELEASE = me.getString("command.sts.general.SUCCESSFULLY_SET_RELEASE");
		STATS_GIVE_MESSAGE = me.getString("command.stsgive.general.STATS_GIVE_MESSAGE");

		SUCCESSFULLY_ADD_POINT = me.getString("command.stsuser.general.SUCCESSFULLY_ADD_POINT");
		SUCCESSFULLY_SET_POINT = me.getString("command.stsuser.general.SUCCESSFULLY_SET_POINT");
		VIEW_USER_STATS = me.getString("command.stsuser.general.VIEW_USER_STATS");
		SUCCESSFULLY_REMOVE_POINT = me.getString("command.stsuser.general.SUCCESSFULLY_REMOVE_POINT");
		SUCCESSFULLY_RESET_POINT = me.getString("command.stsuser.general.SUCCESSFULLY_RESET_POINT");

		INCORRECT_BONUS_TYPE = me.getString("command.stsadmin2.general.INCORRECT_BONUS_TYPE");
		SUCCESSFULLY_SET_BONUS = me.getString("command.stsadmin2.general.SUCCESSFULLY_SET_BONUS");
		SUCCESSFULLY_RELOAD = me.getString("command.stsadmin2.general.SUCCESSFULLY_RELOAD");
		SUCCESSFULLY_SAVE = me.getString("command.stsadmin2.general.SUCCESSFULLY_SAVE");
		VIEW_STATS_BONUS = me.getString("command.stsadmin2.general.VIEW_STATS_BONUS");
		INCORRECT_FILE_NAME = me.getString("command.stsadmin2.general.INCORRECT_FILE_NAME");
		KICK_MESSAGE = me.getString("command.stsadmin2.general.KICK_MESSAGE");

		SUCCESSFULLY_CMD_SET = me.getString("command.stscmd.general.SUCCESSFULLY_CMD_SET");
		SUCCESSFULLY_CMD_REMOVE = me.getString("command.stscmd.general.SUCCESSFULLY_CMD_REMOVE");
		INCORRECT_CMD_TYPE = me.getString("command.stscmd.general.INCORRECT_CMD_TYPE");

		CANT_FIND_UPDATE_LOG = me.getString("command.stsupdate.general.CANT_FIND_UPDATE_LOG");
		PLUGINLOG1 = me.getString("command.stsupdate.general.PLUGINLOG1");
		PLUGINLOG2 = me.getString("command.stsupdate.general.PLUGINLOG2");

		STSCMD_TYPE = me.getString("command.stscmd.general.STSCMD_TYPE");
		STSCMD_CMD = me.getString("command.stscmd.general.STSCMD_CMD");

	}

	public static void helpMessageList(CommandSender sender, List<String> list, int i, String label) {
		if (i * 10 - 9 > list.size() || i == 0) {
			sender.sendMessage(CANT_FIND_MESSAGELIST);
		} else {
			for (int j = (i - 1) * 10; j < i * 10; j++) {
				sender.sendMessage(list.get(j).replace('&', '§'));
				if (list.size() == (j + 1)) {
					break;
				}
				if (((i * 10) - 1) == j && list.size() > (j + 1)) {
					sender.sendMessage(NEXT_MESSAGE.replace("<cmd>", label).replace("<index>", String.valueOf(i + 1)));
				}
			}
		}
	}

	public static void helpMessageListPLUSINDEX(CommandSender sender, List<String> list, int i, String label) {
		if (i * 10 - 9 > list.size() || i == 0) {
			sender.sendMessage(CANT_FIND_MESSAGELIST);
		} else {
			if (list.size() % 10 == 0) {
				sender.sendMessage(MessageAPI.LIST_INDEX_MESSAGE.replace("<n>", String.valueOf(list.size())).replace("<index>", String.valueOf(i)).replace("<all>", String.valueOf(list.size() / 10)));
			} else {
				sender.sendMessage(MessageAPI.LIST_INDEX_MESSAGE.replace("<n>", String.valueOf(list.size())).replace("<index>", String.valueOf(i)).replace("<all>", String.valueOf(list.size() / 10 + 1)));
			}
			for (int j = (i - 1) * 10; j < i * 10; j++) {
				sender.sendMessage(LIST_INDEX.replace("<index>", String.valueOf(j + 1)).replace("<message>", list.get(j).replaceAll("&", "§")));
				if (list.size() == (j + 1)) {
					break;
				}
				if (((i * 10) - 1) == j && list.size() > (j + 1)) {
					sender.sendMessage(NEXT_MESSAGE.replace("<cmd>", label).replace("<index>", String.valueOf(i + 1)));
				}
			}
		}
	}

	public static void saveMessageConfig() {
		YamlConfiguration me = RsStats.message;
		List<String> sts = new LinkedList<String>();
		List<String> stsadmin = new LinkedList<String>();
		List<String> stsadmin2 = new LinkedList<String>();
		List<String> stscmd = new LinkedList<String>();
		List<String> stsgive = new LinkedList<String>();
		List<String> stsuser = new LinkedList<String>();
		sts.add("§6/sts (v|view)");
		sts.add("§6/sts (v|view) <name>");
		sts.add("§6/sts (r|release) (true|false)");

		stsadmin.add("§6/stsadmin add <stats> <x> <y> <type> <coefficient>");
		stsadmin.add("§6/stsadmin edit <stats> <x> <y> <type> <coefficient>");
		stsadmin.add("§6/stsadmin addlore <stats> <message>");
		stsadmin.add("§6/stsadmin removelore <stats> <index>");
		stsadmin.add("§6/stsadmin setitem <stats> <item-code>");
		stsadmin.add("§6/stsadmin setitemname <stats> <displayname>");
		stsadmin.add("§6/stsadmin setmax <stats> <value>");
		stsadmin.add("§6/stsadmin remove <stats>");
		stsadmin.add("§6/stsadmin list");
		if (RsStats.lang.equals("kr")) {
			stsadmin.add("§cHEALTH, ORE, BREAK, PLACE, DAMAGE, PLANTS, DEFENSE, FOOD, CRITICAL, ATTACK_RESIST, ARROW, LIFESTEAL등의 타입이 존재합니다.");
		} else if (RsStats.lang.equals("us")) {
			stsadmin.add("§ctype is exists such as HEALTH, ORE, BREAK, PLACE, DAMAGE, PLANTS, DEFENSE, FOOD, CRITICAL, ATTACK_RESIST, ARROW, LIFESTEAL");
		}

		stsadmin2.add("§6/stsadmin2 set (rpgexpsystem|mclevelup) <value>");
		stsadmin2.add("§6/stsadmin2 reload (config|stats|user|message)");
		stsadmin2.add("§6/stsadmin2 save (config|stats|user|message)");
		stsadmin2.add("§6/stsadmin2 viewconfig");

		stscmd.add("§6/stscmd set <stats> <point> <cmd type> (command)");
		stscmd.add("§6/stscmd remove <stats> <point>");
		stscmd.add("§6/stscmd list");
		if (RsStats.lang.equals("kr")) {
			stscmd.add("§cCMD, CMDOP, CMDCON, CHAT, CHATOP등의 타입이 존재합니다.");
		} else if (RsStats.lang.equals("us")) {
			stscmd.add("§ccmdtype is exists such as CMD, CMDOP, CMDCON, CHAT, CHATOP");
		}

		stsuser.add("§6/stsuser dset <user> <stats> <point>");
		stsuser.add("§6/stsuser dadd <user> <stats> <point>");
		stsuser.add("§6/stsuser dview <user> <stats>");
		stsuser.add("§6/stsuser set <user> <value>");
		stsuser.add("§6/stsuser add <user> <value>");
		stsuser.add("§6/stsuser view <user>");
		stsuser.add("§6/stsuser reset <user> <stats>");
		stsuser.add("§6/stsuser remove <user>");

		stsgive.add("§6/stsgive <name> <value>");

		me.set("command.sts.help", sts);
		me.set("command.stsadmin.help", stsadmin);
		me.set("command.stsadmin2.help", stsadmin2);
		me.set("command.stscmd.help", stscmd);
		me.set("command.stsuser.help", stsuser);
		me.set("command.stsgive.help", stsgive);

		if (RsStats.lang.equals("kr")) {
			me.set("command.general.incorrect.INCORRECT_MESSAGE", "§6잘못된 명령어입니다. §c/<cmd> §6명령어를 사용하십시오.");
			me.set("command.general.incorrect.INCORRECT_STATS_TYPE", "§6올바른 스텟 §c타입§6을 입력하십시오.");
			me.set("command.general.incorrect.INCORRECT_COEFFICIENT", "§6올바른 스텟 §c계수§6를 입력하십시오.");
			me.set("command.general.incorrect.INCORRECT_LIST_INDEX", "§6해당 번호의 §clore§c6는 존재하지 않습니다.");
			me.set("command.general.incorrect.INCORRECT_ITEM_CODE", "§6올바르지 않은 §c아이템 코드§6입니다.");
			me.set("command.general.incorrect.INCORRECT_POSITIVE_INTEGER", "§6올바른 §c정수§6를 적으십시오.");
			me.set("command.general.incorrect.INCORRECT_X_Y_INTEGER", "§cX§6와 §cY§6에 올바른 §c정수§6를 적으십시오.");

			me.set("command.general.cant.CANT_FIND_MESSAGELIST", "§c해당 목록은 존재하지 않습니다.");
			me.set("command.general.cant.CANT_FINT_STATS_NAME", "§c해당된 이름의 스텟을 찾을수가 없습니다.");
			me.set("command.general.cant.CANT_FINT_USER", "§c해당 유저를 찾을수가 없습니다.");
			me.set("command.general.cant.CANT_CONSOLE_USE_THIS_COMMAND", "§c콘솔은 해당 명령어의 사용이 불가능합니다.");

			me.set("command.general.etc.NEXT_MESSAGE", "§6다음 목록을 보려면 §c/<cmd> <index>");
			me.set("command.general.etc.LIST_INDEX_MESSAGE", "§c<n>§6개의 목록을 찾았습니다. §c<index>§6/§c<all>");
			me.set("command.general.etc.LIST_INDEX", "§c<index>: §6<message>");
			me.set("command.general.etc.STATS_LORE_NOW", "§6현재 스텟: §c<point>§6/§c<max>");
			me.set("command.general.etc.ALREADY_HAS_STATS_NAME", "§c이미 존재하는 스텟의 이름입니다.");
			me.set("command.general.etc.PLZ_ENTER_CORRECT_X_Y", "§cX§6에는 §c1~9§6를 §cY§6에는 §c1~3§6을 입력하십시오.");
			me.set("command.general.etc.INVENTORY_NAME", "§c<name>§6님의 스텟");
			me.set("command.general.etc.NO_RELEASE", "§c<name>§6님의 스텟 공개여부는 §cFalse§6입니다.");
			me.set("command.general.etc.AVILABLESTATS", "§6남은 스텟: §c<point>");
			me.set("command.general.etc.RUN_CRITICAL", "§c크리티컬 §6데미지!");
			me.set("command.general.etc.RUN_BREAK", "§6블럭 §c드랍 두배!");
			me.set("command.general.etc.RUN_PLACE", "§6아이템 §c복구!");
			me.set("command.general.etc.RUN_ITEM", "§6음식 §c복구!");
			me.set("command.general.etc.INFINITE", "Infinite");
			me.set("command.general.etc.AUTO_SAVE", "§c[RsStats] §6[스텟, 유저] 데이터 자동 저장 완료");

			me.set("command.stsadmin.general.SUCCESSFULLY_ADD_STATS", "§6성공적으로 §c스텟§6을 추가했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_EDIT_STATS", "§6성공적으로 §c스텟§6을 수정했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_REMOVE_STATS", "§6성공적으로 §c스텟§6을 삭제했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_ADDLORE_STATS", "§6성공적으로 §clore§6를 추가했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_REMOVELORE_STATS", "§6성공적으로 §clore§6를 지웠습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_SETITEM_STATS", "§6성공적으로 §c아이템 코드§6를 설정했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_SETITEMNAME_STATS", "§6성공적으로 §c아이템 이름§6을 설정했습니다.");
			me.set("command.stsadmin.general.SUCCESSFULLY_SETMAX_STATS", "§6성공적으로 §c최대 스텟량§6을 설정했습니다.");

			me.set("command.sts.general.SUCCESSFULLY_SET_RELEASE", "§6성공적으로 스텟 공개 여부를 §c<release>§6로 설정했습니다.");
			me.set("command.stsgive.general.STATS_GIVE_MESSAGE", "§6당신은 §c<point>§6만큼 스텟 포인트를 받았습니다.");

			me.set("command.stsuser.general.SUCCESSFULLY_ADD_POINT", "§6성공적으로 해당 유저의 스텟 포인트를 추가했습니다.");
			me.set("command.stsuser.general.SUCCESSFULLY_SET_POINT", "§6성공적으로 해당 유저의 스텟 포인트를 설정했습니다.");
			me.set("command.stsuser.general.VIEW_USER_STATS", "§c<stats> §6>> §c<point>");
			me.set("command.stsuser.general.SUCCESSFULLY_RESET_POINT", "§6성공적으로 해당 유저의 스텟 포인트를 초기화했습니다.");
			me.set("command.stsuser.general.SUCCESSFULLY_REMOVE_POINT", "§6성공적으로 해당 유저의 모든 스텟 포인트를 삭제했습니다.");

			me.set("command.stsadmin2.general.INCORRECT_BONUS_TYPE", "§c올바른 보너스 이름을 입력하십시오.");
			me.set("command.stsadmin2.general.SUCCESSFULLY_SET_BONUS", "§6성공적으로 스텟 보너스를 입력했습니다.");
			me.set("command.stsadmin2.general.SUCCESSFULLY_RELOAD", "§6성공적으로 파일을 리로드했습니다.");
			me.set("command.stsadmin2.general.SUCCESSFULLY_SAVE", "§6성공적으로 파일을 저장했습니다.");
			me.set("command.stsadmin2.general.VIEW_STATS_BONUS", "§6<type>: §c<bonus>");
			me.set("command.stsadmin2.general.INCORRECT_FILE_NAME", "§c올바른 파일 이름을 입력하십시오.");
			me.set("command.stsadmin2.general.KICK_MESSAGE", "§c[RsStats] §6데이터 파일 로드시 안정성을 위하여 강제로 추방합니다.");

			me.set("command.stscmd.general.SUCCESSFULLY_CMD_SET", "§6성공적으로 명령어를 예약했습니다.");
			me.set("command.stscmd.general.SUCCESSFULLY_CMD_REMOVE", "§6성공적으로 예약한 명령어를 삭제했습니다.");
			me.set("command.stscmd.general.INCORRECT_CMD_TYPE", "§c올바르지 않은 명령어 타입입니다.");

			me.set("command.stsupdate.general.CANT_FIND_UPDATE_LOG", "§c업데이트 로그를 찾을수가 없습니다.");
		} else if (RsStats.lang.equals("us")) {

		}
		me.set("command.stsupdate.general.PLUGINLOG1", "§6<version>:");
		me.set("command.stsupdate.general.PLUGINLOG2", "  - §c<log>");

		me.set("command.stscmd.general.STSCMD_TYPE", " - §6<stats>");
		me.set("command.stscmd.general.STSCMD_CMD", "§c<index>: §6<cmd>");

		me.saveYaml();
	}
}
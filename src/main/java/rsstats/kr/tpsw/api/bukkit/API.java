package rsstats.kr.tpsw.api.bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class API {

    //TODO : 최신버전에선 작동안함... (item code가 string으로 변경됨)
    public static int[] getItemCode(String str) {
        String[] ids = str.split(":");
        int[] ii = {0, 0};
        try {
            if (API.isIntegerPositive(ids[0]) && Material.getMaterial(Integer.valueOf(ids[0])) != null) {
                ii[0] = Integer.valueOf(ids[0]);
                if (ids.length == 2 && API.isIntegerPositive(ids[1])) {
                    ii[1] = Short.valueOf(ids[1]);
                }
            }
        } catch (Exception e) {
            int[] i = {0, 0};
            return i;
        }
        return ii;
    }

    public static float getDotSecond(float value) {
        return (float) getDotSecond(value);
    }

    public static double getDotSecond(double value) {
        return Double.valueOf(Math.round(value * 100D)) / 100D;
    }

    public static String mergeArgs(String[] args, int start, int end, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(args[i]).append(c);
        }
        return sb.toString().trim();
    }

    public static String mergeArgs(String[] args, int start) {
        return mergeArgs(args, start, args.length, ' ');
    }

    public static String mergeArgsUnder(String[] args, int start) {
        return mergeArgs(args, start, args.length, '_');
    }

    public static void sendMessageList(CommandSender sender, List<String> list, int i, String label) {
        if (i * 10 - 9 > list.size() && list.size() == 0) {
            sender.sendMessage("§c해당 목록은 존재하지 않습니다.");
            return;
        }
        if (list.size() * 10 == 0) {
            sender.sendMessage("§6" + list.size() + "개를 찾았습니다 §c" + i + "§6/§c" + (list.size() / 10));
        } else {
            sender.sendMessage("§6" + list.size() + "개를 찾았습니다 §c" + i + "§6/§c" + (list.size() / 10 + 1));
        }
        for (int j = (i - 1) * 10; j < i * 10; j++) {
            sender.sendMessage(list.get(j).replaceAll("&", "§"));
            if (list.size() == (j + 1)) {
                break;
            }
            if (((i * 10) - 1) == j && list.size() > (j + 1)) {
                sender.sendMessage("§6다음 목록을 보려면§c/" + label + " " + (i + 1));
            }
        }
    }

    public static boolean isInteger(String string) {
        return string.matches("[-]?[0-9]+");
    }

    public static boolean isIntegerPositive(String string) {
        return string.matches("[0-9]+");
    }

    public static boolean isDouble(String string) {
        return string.matches("[-]?([0-9]+|[0-9]+[.][0-9]+)");
    }

    public static boolean isDoublePositive(String string) {
        return string.matches("([0-9]+|[0-9]+[.][0-9]+)");
    }

    public static boolean isEnglist(String string) {
        return string.matches("^[a-zA-Z]+");
    }

    public static boolean isHangle(String string) {
        return string.matches("^[ㄱ-하-ㅣ가-힣]+");
    }

    public static boolean containsColorCode(String string) {
        return string.indexOf("&") > -1;
    }

    public static boolean containsChatColor(String string) {
        return string.indexOf("§") > -1;
    }

    public static String replaceChatColorToChatCode(String string) {
        return string.replace("§", "&");
    }

    public static String replaceColorCodeToChatColor(String string) {
        return string.replace("&", "§");
    }

    public static String replaceColorCodeToEmpthy(String string) {
        return string.replaceAll("(&)[a-zA-Z0-9]", "");
    }

    public static String replaceChatColorToEmpthy(String string) {
        return string.replaceAll("(§)[a-zA-Z0-9]", "");
    }

    public static void runCommand(String cmd, String type, Player target) {
        switch (type) {
            case "cmd":
                Bukkit.dispatchCommand(target, cmd);
                break;
            case "cmdop":
                if (target.isOp()) {
                    Bukkit.dispatchCommand(target, cmd);
                } else {
                    target.setOp(true);
                    Bukkit.dispatchCommand(target, cmd);
                    target.setOp(false);
                }
                break;
            case "cmdcon":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                break;
            case "chat":
                target.chat("/" + cmd);
                break;
            case "chatop":
                if (target.isOp()) {
                    target.chat("/" + cmd);
                } else {
                    target.setOp(true);
                    target.chat("/" + cmd);
                    target.setOp(false);
                }
                break;
        }
    }
}

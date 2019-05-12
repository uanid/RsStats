package kr.tpsw.rsstats.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.tpsw.rsstats.RsStats;
import kr.tpsw.rsstats.YamlConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsstats.kr.tpsw.api.bukkit.API;

public class StatsAPI {

    public static Map<String, RpgStats> rpgstats = new HashMap<String, RpgStats>();
    public static Map<String, StatsPlayer> statsplyaer = new HashMap<String, StatsPlayer>();
    public final static String NAMECODE = "§1§1§r";
    public static ItemStack STATS_POINT;
    public static ItemStack SAVE_STATS;
    public static int STATS_POINT_LOC = 0;// 남은 스텟 버튼 위치
    public static int SAVE_STATS_LOC = 0;// 저장 버튼 위치
    public static int rpgexpsystem = 0;
    public static int levelup = 0;
    public static int autosavetime = 0;
    public static int autosavecode = -1;

    //@TODO: Material 변경 된것 반영해야함
    public static void loadStatsItem() {
        YamlConfiguration config = RsStats.config;
        {
            String code = config.getString("config.availablestats.item");
            String name = config.getString("config.availablestats.name");
            List<String> list = config.getStringList("config.availablestats.lore");
            int x = config.getInt("config.availablestats.x");
            int y = config.getInt("config.availablestats.y");
            STATS_POINT_LOC = (x - 1) + (y - 1) * 9;
            STATS_POINT = new ItemStack(0);
            int[] ia = API.getItemCode(code);
            STATS_POINT.setType(Material.getMaterial(ia[0]));
            STATS_POINT.setDurability((short) ia[1]);
            ItemMeta im = STATS_POINT.getItemMeta();
            im.setDisplayName(name.replace('&', '§'));
            im.setLore(list);
            STATS_POINT.setItemMeta(im);
            //System.out.println("[디버그] 남은스텟 확인" + STATS_POINT.toString());
        } // 남은 스텟 버튼 로드

        {
            String code = config.getString("config.save.item");
            String name = config.getString("config.save.name");
            List<String> list = config.getStringList("config.save.lore");
            int x = config.getInt("config.save.x");
            int y = config.getInt("config.save.y");
            SAVE_STATS_LOC = (x - 1) + (y - 1) * 9;
            SAVE_STATS = new ItemStack(0);
            int[] ia = API.getItemCode(code);
            SAVE_STATS.setType(Material.getMaterial(ia[0]));
            SAVE_STATS.setDurability((short) ia[1]);
            ItemMeta im = SAVE_STATS.getItemMeta();
            im.setDisplayName(name.replace('&', '§'));
            im.setLore(list);
            SAVE_STATS.setItemMeta(im);
        } // 저장 버튼 로드

        rpgexpsystem = config.getInt("config.rpgexpsystem");
        levelup = config.getInt("config.levelup");
        // 기타 설정 로드

        autosavetime = config.getInt("auto_save_time");
        if (autosavecode != -1) {
            Bukkit.getScheduler().cancelTask(autosavecode);
        }
        autosavecode = Bukkit.getScheduler().scheduleSyncRepeatingTask(RsStats.plugin, new Runnable() {
            public void run() {
                new Thread(new Runnable() {
                    public void run() {
                        saveData();
                        Bukkit.broadcastMessage(MessageAPI.AUTO_SAVE);
                    }
                }).start();
            }
        }, autosavetime * 20, autosavetime * 20);// 자동 저장 주기 설정
    }

    public static void saveStatsItem() {
        RsStats.config.set("config.rpgexpsystem", rpgexpsystem);
        RsStats.config.set("config.levelup", levelup);
    }

    public static void saveFirstStatsItem() {
        List<String> list = new LinkedList<>();

        list.add("§8남은 스텟 포인트를 확인합니다.");
        RsStats.config.set("config.availablestats.item", "280");
        RsStats.config.set("config.availablestats.name", "§2[ §a남은 스텟 §2]");
        RsStats.config.set("config.availablestats.lore", list);
        RsStats.config.set("config.availablestats.x", 1);
        RsStats.config.set("config.availablestats.y", 3);

        list = new LinkedList<>();
        list.add("§8설정한 스텟을 저장합니다.");
        RsStats.config.set("config.save.item", "369");
        RsStats.config.set("config.save.name", "§2[ §a스텟 저장§2 ]");
        RsStats.config.set("config.save.lore", list);
        RsStats.config.set("config.save.x", 9);
        RsStats.config.set("config.save.y", 3);

        RsStats.config.set("config.rpgexpsystem", 0);
        RsStats.config.set("config.levelup", 1);//기본 설정 1업당 1스텟
        RsStats.config.set("auto_save_time", 600);
        RsStats.config.saveYaml();
    }// 첫 콘피그 저장

    public static void updateRpgStatsHastSet() {
        StatsSet.BREAK.clear();
        StatsSet.DAMAGE.clear();
        StatsSet.DEFENSE.clear();
        StatsSet.FOOD.clear();
        StatsSet.HEALTH.clear();
        StatsSet.ORE.clear();
        StatsSet.PLACE.clear();
        StatsSet.PLANTS.clear();
        StatsSet.LIFESTEAL.clear();
        StatsSet.CRITICAL.clear();
        StatsSet.ATTACK_RESIST.clear();
        StatsSet.ARROW.clear();
        for (RpgStats rs : rpgstats.values()) {
            switch (rs.type) {
                case BREAK:
                    StatsSet.BREAK.add(rs);
                    break;

                case DAMAGE:
                    StatsSet.DAMAGE.add(rs);
                    break;

                case DEFENSE:
                    StatsSet.DEFENSE.add(rs);
                    break;

                case FOOD:
                    StatsSet.FOOD.add(rs);
                    break;

                case HEALTH:
                    StatsSet.HEALTH.add(rs);
                    break;

                case ORE:
                    StatsSet.ORE.add(rs);
                    break;

                case PLACE:
                    StatsSet.PLACE.add(rs);
                    break;

                case PLANTS:
                    StatsSet.PLANTS.add(rs);
                    break;
                case CRITICAL:
                    StatsSet.CRITICAL.add(rs);
                    break;

                case LIFESTEAL:
                    StatsSet.LIFESTEAL.add(rs);
                    break;

                case ARROW:
                    StatsSet.ARROW.add(rs);
                    break;

                case ATTACK_RESIST:
                    StatsSet.ATTACK_RESIST.add(rs);
                    break;

                case SPEED:
                    break;
                default:
                    break;
            }
        }
        if (StatsSet.HEALTH.size() == 0) {
            StatsRunAPI.RegisterLoreAttributes();
        } else {
            StatsRunAPI.unRegisterLoreAttributes();
        }
    }

    public static boolean isStat(String statname) {
        return rpgstats.keySet().contains(statname);
    }

    public static StatsPlayer getStatsPlayer(String name) {
        return statsplyaer.get(name);
    }

    public static void addStatsPlayer(String name) {
        statsplyaer.put(name, new StatsPlayer(name));
    }

    public static RpgStats getRpgStats(String name) {
        return rpgstats.get(name);
    }

    public static void addRpgStats(String name, int x, int y, StatsType type, double coe, ItemStack is) {
        rpgstats.put(name, new RpgStats(name, x, y, type, coe, is));
        updateRpgStats();
    }

    public static void updateRpgStats() {
        for (StatsPlayer sp : statsplyaer.values()) {
            sp.updateStatsPoint();
        }
    }

    public static void removeRpgStats(String name) {
        rpgstats.remove(name);
        updateRpgStats();
    }

    public static boolean isStatsType(String type) {
        try {
            StatsType.valueOf(type);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String replaceNowStats(int point, int max) {
        return MessageAPI.STATS_LORE_NOW.replace("<point>", String.valueOf(point)).replace("<max>", (max == -1 ? MessageAPI.INFINITE : String.valueOf(max)));
    }

    public static Inventory getStatsInventory(StatsPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 27, NAMECODE + MessageAPI.INVENTORY_NAME.replace("<name>", player.getName()));
        for (RpgStats rs : rpgstats.values()) {
            rs.setItem(inv, player.getStatPoint(rs.name));
        }
        ItemStack is = STATS_POINT.clone();
        ItemMeta im = is.getItemMeta();
        List<String> list = im.getLore();
        if (list == null) {
            list = new LinkedList<String>();
        }
        list.add(MessageAPI.AVILABLESTATS.replace("<point>", String.valueOf(player.getAvailablePoint())));
        im.setLore(list);
        is.setItemMeta(im);
        inv.setItem(STATS_POINT_LOC, is);
        inv.setItem(SAVE_STATS_LOC, SAVE_STATS);
        return inv;
    }

    public static void loadData() {
        File f = new File("plugins\\RsStats\\rpgstats.ser");
        File f2 = new File("plugins\\RsStats\\statsplayer.ser");
        try {
            if (f.exists()) {
                loadRpgStats();
            }
            if (f2.exists()) {
                loadStatsPlayer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        saveRpgStats();
        saveStatsPlayer();
    }

    public static void saveRpgStats() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("plugins\\RsStats\\rpgstats.ser"));
            for (RpgStats rs : rpgstats.values()) {
                rs.serialize();
                oos.writeObject(rs);
            }
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStatsPlayer() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("plugins\\RsStats\\statsplayer.ser"));
            for (StatsPlayer sp : statsplyaer.values()) {
                oos.writeObject(sp);
            }
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRpgStats() {
        try {
            ObjectInputStream bois = new ObjectInputStream(new FileInputStream("plugins\\RsStats\\rpgstats.ser"));
            try {
                RpgStats rs;
                while (true) {
                    rs = (RpgStats) bois.readObject();
                    rpgstats.put(rs.name, rs);
                    rs.deSerialize();
                }
            } catch (Exception e) {

            }
            bois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStatsPlayer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("plugins\\RsStats\\statsplayer.ser"));
            try {
                StatsPlayer sp;
                while (true) {
                    sp = (StatsPlayer) ois.readObject();
                    statsplyaer.put(sp.getName(), sp);
                    sp.updateStatsPoint();
                }
            } catch (Exception e) {

            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class StatsSet {
        public static Set<RpgStats> HEALTH = new HashSet<RpgStats>();
        public static Set<RpgStats> ORE = new HashSet<RpgStats>();
        public static Set<RpgStats> BREAK = new HashSet<RpgStats>();
        public static Set<RpgStats> PLACE = new HashSet<RpgStats>();
        public static Set<RpgStats> DAMAGE = new HashSet<RpgStats>();
        public static Set<RpgStats> PLANTS = new HashSet<RpgStats>();
        public static Set<RpgStats> DEFENSE = new HashSet<RpgStats>();
        public static Set<RpgStats> FOOD = new HashSet<RpgStats>();
        public static Set<RpgStats> LIFESTEAL = new HashSet<RpgStats>();
        public static Set<RpgStats> CRITICAL = new HashSet<RpgStats>();
        public static Set<RpgStats> ATTACK_RESIST = new HashSet<RpgStats>();
        public static Set<RpgStats> ARROW = new HashSet<RpgStats>();
    }

}

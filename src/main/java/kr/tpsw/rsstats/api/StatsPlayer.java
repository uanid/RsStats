package kr.tpsw.rsstats.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import rsstats.kr.tpsw.api.bukkit.API;

public class StatsPlayer implements Serializable {
    private static final long serialVersionUID = -8753869250779895631L;

    transient private Player player;
    private String name;
    private Map<String, Integer> statspoint = new HashMap<String, Integer>();
    boolean isonline = false;
    boolean release = false;
    transient private Inventory inv;
    int availablepoint = 0;
    public transient Integer instavail;
    public transient Map<String, Integer> inststats;

    public StatsPlayer(String name) {
        this.player = Bukkit.getPlayerExact(name);
        this.isonline = (player != null);
        this.name = name;
        this.release = false;
        this.updateStatsPoint();
    }

    public void updateInstValue() {
        instavail = availablepoint;
        inststats = new HashMap<String, Integer>(statspoint);
    }

    public void exitInstValue() {
        int delta = 0;
        Map<Integer, String> smap;
        String cmd;
        int point = 0;
        String[] args;
        for (String key : inststats.keySet()) {
            point = statspoint.get(key);
            delta = inststats.get(key) - statspoint.get(key);
            smap = StatsCmdAPI.map.get(key);
            if (smap != null) {
                for (int i = 0; i < delta; i++) {
                    cmd = smap.get(i + point + 1);
                    if (cmd != null) {
                        args = cmd.split(" ");
                        API.runCommand(args[1].replace('_', ' ').replace("<player>", player.getName()).trim(), args[0], player);
                    }
                }
            }
        }
        availablepoint = instavail;
        statspoint = inststats;
        instavail = null;
        inststats = null;
    }

    public void resetInstValue() {
        instavail = null;
        inststats = null;
    }

    public void saveToConfig() {
        // RsStats.testu.set(name + ".release", release);
        // RsStats.testu.set(name + ".stats", statspoint);
    }

    public void updateInventory() {
        inv = StatsAPI.getStatsInventory(this);
    }

    public Inventory getInventory() {
        return inv;
    }

    public void setInventory() {
        this.inv = null;
    }

    public void setAvailablePoint(int i) {
        this.availablepoint = i;
    }

    public void addAvailablePoint(int i) {
        this.availablepoint += i;
    }

    public int getAvailablePoint() {
        return availablepoint;
    }

    public void updateStatsPoint() {
        List<String> list = new LinkedList<String>();
        Iterator<String> it = statspoint.keySet().iterator();
        String v;
        while (it.hasNext()) {
            v = it.next();
            if (!StatsAPI.isStat(v)) {
                it.remove();
            }
        }
        for (String s : StatsAPI.rpgstats.keySet()) {
            if (statspoint.get(s) == null) {
                list.add(s);
            }
        }
        for (String s : list) {
            statspoint.put(s, 0);
        }
    }

    public void updatePlayer() {
        this.player = Bukkit.getPlayerExact(name);
        this.isonline = (player != null);
    }

    public Map<String, Integer> getStats() {
        return this.statspoint;
    }

    public boolean isRelease() {
        return this.release;
    }

    public void setRelease(boolean bool) {
        this.release = bool;
    }

    public int getStatPoint(String statname) {
        return this.statspoint.get(statname);
    }

    public void addStatsPoint(String name, int point) {
        Integer i = this.statspoint.get(name);
        this.statspoint.put(name, i + point);
    }

    public String getName() {
        return name;
    }

    public void setStatPoint(String stats, int value) {
        this.statspoint.put(stats, value);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOnline() {
        return this.isonline;
    }

    public void sendMessage(String message) {
        if (isonline) {
            this.player.sendMessage(message.replace('@', '§'));
        }
    }
}

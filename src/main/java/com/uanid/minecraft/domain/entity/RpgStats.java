package com.uanid.minecraft.domain.entity;

import com.uanid.minecraft.domain.type.StatsType;
import com.uanid.minecraft.service.StatsService;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RpgStats implements Serializable {
    private static final long serialVersionUID = 3393968338041653565L;

    public String name;
    public int x;
    public int y;
    public StatsType type;
    public double coe;
    transient public ItemStack is;
    private Map<String, Object> map;
    private Map<String, Object> map2;
    public int max = -1;

    public RpgStats(String name, int x, int y, StatsType type, double coe, ItemStack is) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.coe = coe;
        this.is = is;
    }

    public double calStats(int point) {
        return point * coe;
    }

    public void serialize() {
        this.map = is.serialize();
        ItemMeta im = (ItemMeta) map.get("meta");
        this.map2 = new LinkedHashMap<String, Object>();
        map.remove("meta");
        map2.put("lore", im.getLore());
        map2.put("displayname", im.getDisplayName());
    }

    @SuppressWarnings("unchecked")
    public void deSerialize() {
        this.is = ItemStack.deserialize(map);
        ItemMeta im = Bukkit.getItemFactory().getItemMeta(is.getType());
        im.setDisplayName((String) map2.get("displayname"));
        Object obj = map2.get("lore");
        if (obj != null)
            im.setLore((List<String>) obj);
        try {
            Class<ItemStack> cls = ItemStack.class;
            Field f = cls.getDeclaredField("meta");
            f.setAccessible(true);
            f.set(is, im);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setItem(Inventory inv, int point) {
        ItemStack is = this.is.clone();
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        lore.add(StatsService.replaceNowStats(point, max));
        im.setLore(lore);
        is.setItemMeta(im);
        try {
            inv.setItem((x - 1) + (y - 1) * 9, is);
        } catch (Exception e) {
            System.out.println("[디버그] 아이템 슬롯 넘버 오류 발생 개행");
            System.out.println("[디버그] " + is.toString());
            System.out.println("[디버그] 아이템 슬롯 넘버 오류 발생 종행");
        }
    }

    /*
     * public void saveToConfig() { Main.tests.set(name + ".x", x);
     * Main.tests.set(name + ".y", y); Main.tests.set(name + ".type",
     * type.toString()); Main.tests.set(name + ".coe", coe); Main.tests.set(name
     * + ".is.lore", is.getItemMeta().getLore()); Main.tests.set(name +
     * ".is.name", is.getItemMeta().getDisplayName()); Main.tests.set(name +
     * ".is.material", is.getType().toString()); Main.tests.set(name +
     * ".is.durability", is.getDurability()); }
     */

    public String toString() {
        return "§6Name: §c" + this.name + " §6Type: §c" + this.type.toString() + " §6x,y: §c" + x + "," + y + " §6Coe: §c" + this.coe + " §6ItemCode: §c" + this.is.getType().name() + ":"
                + this.is.getDurability();
    }
}

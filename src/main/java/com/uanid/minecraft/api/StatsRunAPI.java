package com.uanid.minecraft.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class StatsRunAPI {

    public static void BlockBreak(StatsPlayer sp, RpgStats rs, BlockEvent event) {
        if (sp.getStatPoint(rs.name) * rs.coe > Math.random() * 100D) {
            Location loc = event.getBlock().getLocation();
            for (ItemStack is : event.getBlock().getDrops()) {
                loc.getWorld().dropItem(loc, is);
            }
            sp.sendMessage(MessageAPI.RUN_BREAK);
        }
    }

    public static void BlockPlace(StatsPlayer sp, RpgStats rs, BlockPlaceEvent event) {
        if (sp.getStatPoint(rs.name) * rs.coe > Math.random() * 100D) {
            ItemStack is = event.getItemInHand().clone();
            is.setAmount(1);
            // sp.getPlayer().getInventory().addItem(new ItemStack[] { is });
            sp.getPlayer().getInventory().addItem(is);
            sp.getPlayer().updateInventory();
            sp.sendMessage(MessageAPI.RUN_PLACE);
        }
    }

    public static double PlayerHealth(StatsPlayer sp, RpgStats rs) {
        return Math.round(sp.getStatPoint(rs.name) * rs.coe);
    }

    public static void EntityDamage(StatsPlayer sp, RpgStats rs, EntityDamageByEntityEvent event) {
        event.setDamage((int) Math.round(sp.getStatPoint(rs.name) * rs.coe) + event.getDamage());
    }

    public static void PlayerDefense(StatsPlayer sp, RpgStats rs, EntityDamageByEntityEvent event) {
        int d = (int) (event.getDamage() - (int) Math.round(sp.getStatPoint(rs.name) * rs.coe));
        if (d < 0) {
            d = 0;
        }
        event.setDamage(d);
    }

    public static void attackResist(StatsPlayer sp, RpgStats rs, EntityDamageByEntityEvent event) {
        event.setDamage((int) (event.getDamage() * ((100D - sp.getStatPoint(rs.name) * rs.coe) / 100D)));
    }

    @SuppressWarnings("deprecation")
    public static void ItemConsume(StatsPlayer sp, RpgStats rs, PlayerItemConsumeEvent event) {
        if (sp.getStatPoint(rs.name) * rs.coe > Math.random() * 100D) {
            ItemStack is = event.getItem().clone();
            is.setAmount(1);
            sp.getPlayer().getInventory().addItem(is);
            sp.getPlayer().updateInventory();
            sp.sendMessage(MessageAPI.RUN_ITEM);
        }
    }

    public static void DamageCritical(StatsPlayer sp, RpgStats rs, EntityDamageByEntityEvent event) {
        if (sp.getStatPoint(rs.name) * rs.coe > Math.random() * 100D) {
            event.setDamage(event.getDamage() * 2);
            sp.sendMessage(MessageAPI.RUN_CRITICAL);
        }
    }

    public static void LifeSteal(StatsPlayer sp, RpgStats rs, EntityDamageByEntityEvent event) {
        int hp = (int) Math.round(event.getDamage() * (sp.getStatPoint(rs.name) * rs.coe / 100D));
        Player p = sp.getPlayer();
        if (p.getHealth() + hp >= p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        } else {
            p.setHealth(p.getHealth() + hp);
        }
    }
}

package kr.tpsw.rsstats.event;

import kr.tpsw.rsstats.RsStats;
import kr.tpsw.rsstats.api.RpgStats;
import kr.tpsw.rsstats.api.StatsAPI;
import kr.tpsw.rsstats.api.StatsAPI.StatsSet;
import kr.tpsw.rsstats.api.StatsPlayer;
import kr.tpsw.rsstats.api.StatsRunAPI;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class entityEventBase implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        //System.out.println("개행");
        //System.out.println(event.getDamager().toString() + " " + event.getDamage() + " " + event.isCancelled());
        if (!event.isCancelled()) {
            Entity entity = event.getDamager();
            String attacker = null;
            String defenser = null;
            if (entity instanceof Player) {// 공격자 [데미지, 크리티컬, 화살]
                attacker = ((Player) entity).getName();
                if (true) {
                    StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                    if (sp != null) {
                        for (RpgStats rs : StatsSet.DAMAGE) {
                            StatsRunAPI.EntityDamage(sp, rs, event);
                        }
                        for (RpgStats rs : StatsSet.CRITICAL) {
                            StatsRunAPI.DamageCritical(sp, rs, event);
                        }
                    } else {
                        return;
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
            } else if (entity instanceof Arrow) {
                Arrow ar = (Arrow) entity;
                if (ar.getShooter() != null && ar.getShooter() instanceof Player) {
                    attacker = ((HumanEntity) ar.getShooter()).getName();
                    StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                    for (RpgStats rs : StatsSet.ARROW) {
                        StatsRunAPI.EntityDamage(sp, rs, event);
                    }
                    for (RpgStats rs : StatsSet.CRITICAL) {
                        StatsRunAPI.DamageCritical(sp, rs, event);
                    }
                }
            } else if (entity instanceof Fireball) {
                Fireball fb = (Fireball) entity;
                if (fb.getShooter() != null && fb.getShooter() instanceof Player) {
                    attacker = ((HumanEntity) fb.getShooter()).getName();
                    StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                    for (RpgStats rs : StatsSet.ARROW) {
                        StatsRunAPI.EntityDamage(sp, rs, event);
                    }
                    for (RpgStats rs : StatsSet.CRITICAL) {
                        StatsRunAPI.DamageCritical(sp, rs, event);
                    }
                }
            }//공격자 종료

            entity = event.getEntity();
            if (entity instanceof Player) {// 피해자 [방어력, 공격저항력]
                defenser = ((Player) entity).getName();
                //System.out.println("공격 받음 damage:" + event.getDamage() + ", health:" + PlayersAPI.getPlayer(defenser).getHealth());
                StatsPlayer sp = StatsAPI.getStatsPlayer(defenser);
                if (sp != null) {
                    for (RpgStats rs : StatsSet.DEFENSE) {
                        StatsRunAPI.PlayerDefense(sp, rs, event);
                    }
                    for (RpgStats rs : StatsSet.ATTACK_RESIST) {
                        StatsRunAPI.attackResist(sp, rs, event);
                    }
                }
                //System.out.println("공격 받음2 damage:" + event.getDamage() + ", health:" + PlayersAPI.getPlayer(defenser).getHealth());
            }//피해자 종료

            entity = event.getDamager();
            if (entity instanceof Player) {// 공격자 [체력흡수]
                StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                for (RpgStats rs : StatsSet.LIFESTEAL) {
                    StatsRunAPI.LifeSteal(sp, rs, event);
                }
            } else if (entity instanceof Arrow) {
                Arrow ar = (Arrow) entity;
                if (ar.getShooter() != null && ar.getShooter() instanceof Player) {
                    StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                    for (RpgStats rs : StatsSet.LIFESTEAL) {
                        StatsRunAPI.LifeSteal(sp, rs, event);
                    }
                }
            } else if (entity instanceof Fireball) {
                Fireball fb = (Fireball) entity;
                if (fb.getShooter() != null && fb.getShooter() instanceof Player) {
                    attacker = ((HumanEntity) fb.getShooter()).getName();
                    StatsPlayer sp = StatsAPI.getStatsPlayer(attacker);
                    for (RpgStats rs : StatsSet.LIFESTEAL) {
                        StatsRunAPI.LifeSteal(sp, rs, event);
                    }
                }
            }
        }
        //System.out.println(event.getDamager().toString() + " " + event.getDamage() + " " + event.isCancelled());
        //System.out.println("종행");
    }
}

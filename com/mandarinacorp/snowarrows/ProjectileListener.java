package com.mandarinacorp.snowarrows;

import com.mandarinacorp.snowarrows.nms.NmsUtil;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import org.bukkit.util.Vector;
import org.bukkit.Sound;

import java.util.Random;

public final class ProjectileListener implements Listener
{ private final SnowArrows plugin;
  private final Random random;

  ProjectileListener(SnowArrows p)
  { plugin = p;
    random = new Random();
  }

  void register()
  { plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  void unregister()
  { HandlerList.unregisterAll(this);
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onEntityShootBow(EntityShootBowEvent e)
  { //I don't think this can be other than arrow, but better be sure anyway
    if(e.getProjectile() instanceof Arrow)
    { Arrow arrow = (Arrow)e.getProjectile();
      //Make a new snowball and fill it with the arrow data, then replace it
      Projectile snowball = e.getEntity().getWorld().spawn(arrow.getLocation(), Snowball.class);
      snowball.setFireTicks(arrow.getFireTicks());
      snowball.setVelocity(arrow.getVelocity());
      snowball.setShooter(arrow.getShooter());
      snowball.setBounce(arrow.doesBounce());
      e.setProjectile(snowball);
      //Attach the data that snowballs can't have so we can use it later
      snowball.setMetadata("knockback", new FixedMetadataValue(plugin, arrow.getKnockbackStrength()));
      snowball.setMetadata("damage", new FixedMetadataValue(plugin, NmsUtil.getArrowDamage(arrow)));
      snowball.setMetadata("critical", new FixedMetadataValue(plugin, arrow.isCritical()));
      //And attach another metadata just to recognice this is a custom snowball
      snowball.setMetadata("snowarrow", new FixedMetadataValue(plugin, true));
    }
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
  { Entity damager = e.getDamager();
    //If the damager contains the metadata, we are sure it's one of our snowballs
    if(damager.hasMetadata("snowarrow"))
    { boolean isCritical = damager.getMetadata("critical").get(0).asBoolean();
      int knockback = damager.getMetadata("knockback").get(0).asInt();
      double damage = damager.getMetadata("damage").get(0).asDouble();
      //This is the damage calculation, taken from the EntityArrow class
      double velocityLength = damager.getVelocity().length();
      double realDamage = Math.ceil(velocityLength * damage);
      if(isCritical)
        realDamage += random.nextInt(((int)realDamage) / 2 + 2);
      e.setDamage(realDamage);
      //Entities always burn "5" seconds for Flame enchantments
      //Since this is hard coded I might change it later to use the EntityCombustByEntityEvent
      if(damager.getFireTicks() > 0)
        e.getEntity().setFireTicks(100);
      //Now calculate knockback if necessary, taken from EntityArrow aswell
      if(knockback > 0)
      { Vector vector = damager.getVelocity();
        double horizontalSpeed = Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
        Vector velocity = e.getEntity().getVelocity();
        velocity.setX(velocity.getX() + vector.getX() * knockback * 0.6 / horizontalSpeed);
        velocity.setY(velocity.getY() + 0.1);
        velocity.setZ(velocity.getZ() + vector.getZ() * knockback * 0.6 / horizontalSpeed);
        e.getEntity().setVelocity(velocity);
      }
      //Play the "ding" sound like normal arrows
      if(((Projectile)damager).getShooter() instanceof Player)
      { Player shooter = (Player)((Projectile)e.getDamager()).getShooter();
        shooter.playSound(shooter.getLocation(), Sound.SUCCESSFUL_HIT, 0.5f, 0.5f); //Sound volume pitch
      }
    }
  }
}

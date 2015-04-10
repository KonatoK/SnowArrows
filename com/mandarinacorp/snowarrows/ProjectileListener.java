package com.mandarinacorp.snowarrows;

import com.mandarinacorp.snowarrows.nms.NmsUtil;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Arrow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

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

  @EventHandler(ignoreCancelled = true)
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
}

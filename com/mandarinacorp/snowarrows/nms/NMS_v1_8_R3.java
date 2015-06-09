package com.mandarinacorp.snowarrows.nms;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;

import org.bukkit.entity.Arrow;

final class NMS_v1_8_R3 implements NmsInterface
{ public void multiplyArrowDamage(final Arrow projectile, double multiplier)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(arrow.j() * multiplier);
  }

  public void addArrowDamage(final Arrow projectile, final double add)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(arrow.j() + add);
  }

  public void setArrowDamage(final Arrow projectile, final double set)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(set);
  }

  public double getArrowDamage(final Arrow projectile)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    return arrow.j();
  }
}

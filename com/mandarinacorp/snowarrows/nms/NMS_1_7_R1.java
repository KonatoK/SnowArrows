package com.mandarinacorp.snowarrows.nms;

import org.bukkit.craftbukkit.v1_7_R1.entity.CraftArrow;
import net.minecraft.server.v1_7_R1.EntityArrow;

import org.bukkit.entity.Arrow;

final class NMS_1_7_R1 implements NmsInterface
{ void multiplyArrowDamage(final Arrow projectile, double multiplier)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(arrow.e() * multiplier);
  }

  void addArrowDamage(final Arrow projectile, final double add)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(arrow.e() + add);
  }

  void setArrowDamage(final Arrow projectile, final double set)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    arrow.b(set);
  }

  double getArrowDamage(final Arrow projectile)
  { EntityArrow arrow = ((CraftArrow)projectile).getHandle();
    return arrow.e();
  }
}

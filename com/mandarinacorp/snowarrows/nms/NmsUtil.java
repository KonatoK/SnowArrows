package com.mandarinacorp.snowarrows.nms;

import org.bukkit.entity.Arrow;
import org.bukkit.Bukkit;

public final class NmsUtil
{ private static NmsInterface nms;
  private static final boolean isCompatible;

  private NmsUtil() {}

  static
  { String version = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
    try
    { nms = (NmsInterface)Class.forName("com.mandarinacorp.snowarrows.nms.NMS_" + version).newInstance();
    }
    catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
    { e.printStackTrace();
    }
    finally
    { isCompatible = nms != null;
    }
  }

  public static boolean isCompatible()
  { return isCompatible;
  }

  public static void multiplyArrowDamage(final Arrow arrow, final double multiplier)
  { nms.multiplyArrowDamage(arrow, multiplier);
  }

  public static void addArrowDamage(final Arrow arrow, final double add)
  { nms.addArrowDamage(arrow, add);
  }

  public static void setArrowDamage(final Arrow arrow, final double set)
  { nms.setArrowDamage(arrow, set);
  }

  public static double getArrowDamage(final Arrow arrow)
  { return nms.getArrowDamage(arrow);
  }
}

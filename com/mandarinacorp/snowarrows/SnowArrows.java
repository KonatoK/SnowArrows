package com.mandarinacorp.snowarrows;

import org.bukkit.plugin.java.*;

public class SnowArrows extends JavaPlugin
{ private ProjectileListener listener = new ProjectileListener(this);

  @Override
  public void onEnable()
  { listener.register();
  }

  @Override
  public void onDisable()
  { listener.unregister();
  }
}

package com.mandarinacorp.snowarrows;

import com.mandarinacorp.snowarrows.nms.NmsUtil;

import org.bukkit.plugin.java.*;

public class SnowArrows extends JavaPlugin
{ private ProjectileListener listener = new ProjectileListener(this);

  @Override
  public void onEnable()
  { if(NmsUtil.isCompatible())
      listener.register();
  }

  @Override
  public void onDisable()
  { if(NmsUtil.isCompatible())
      listener.unregister();
  }
}

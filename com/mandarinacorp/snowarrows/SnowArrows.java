package com.mandarinacorp.snowarrows;

import com.mandarinacorp.snowarrows.nms.NmsUtil;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;

public class SnowArrows extends JavaPlugin
{ private final ProjectileListener listener = new ProjectileListener(this);
  private boolean isRegistered;

  @Override
  public void onEnable()
  { if(NmsUtil.isCompatible())
    { listener.register();
      isRegistered = true;
    }
    else
    { getLogger().severe("This version of CraftBukkit is not compatible");
      getLogger().severe("Arrows will stay normal and boring :/");
    }
  }

  @Override
  public void onDisable()
  { if(isRegistered)
    { listener.unregister();
      isRegistered = false;
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
  { if(command.getName().equals("snowarrows"))
    { if(args.length >= 1 && args[0].equalsIgnoreCase("toggle"))
      { if(!NmsUtil.isCompatible())
          sender.sendMessage(ChatColor.YELLOW + "Sorry, your version of CraftBukkit doesn't seem to be compatible :/");
        else if(isRegistered)
        { listener.unregister();
          isRegistered = false;
          sender.sendMessage(ChatColor.GREEN + "Arrows will be back to normal (and boring)");
        }
        else
        { listener.register();
          isRegistered = true;
          sender.sendMessage(ChatColor.GREEN + "Arrows will now be replaced with snowballs!");
        }
      }
      else
      { sender.sendMessage(ChatColor.GREEN + "SnowArrows " + getDescription().getVersion() + " by Konato_K");
        sender.sendMessage("Did you find a bug? Please contact me at " + ChatColor.YELLOW + "konato@bunnycarrot.net");
        sender.sendMessage("\nAlso you can disable the snowballs with " + ChatColor.YELLOW + "/snowarrows toggle");
      }
    }
    return true;
  }
}

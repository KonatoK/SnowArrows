package com.mandarinacorp.snowarrows.nms;

import org.bukkit.entity.Arrow;

public interface NmsInterface
{ /**
  * Multiplies the damage of an arrow
  */
  public abstract void multiplyArrowDamage(final Arrow arrow, final double multiplier);

  /**
  * Increases damage of an arrow
  */
  public abstract void addArrowDamage(final Arrow arrow, final double add);

  /**
  * Sets the damage of an arrow
  */
  public abstract void setArrowDamage(final Arrow arrow, final double set);

  /**
  * Gets the damage of an arrow
  */
  public abstract double getArrowDamage(final Arrow arrow);
}
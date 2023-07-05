package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.agents.EntityClassification;

/**
 * Mob selection.
 * @author DAM
 */
public class MobSelection
{
  private MobLocation _where;
  private EntityClassification _what;

  /**
   * Get the location of mobs.
   * @return a location string (internal game code).
   */
  public MobLocation getWhere()
  {
    return _where;
  }

  /**
   * Set the location of mob.
   * @param where the location to set.
   */
  public void setWhere(MobLocation where)
  {
    _where=where;
  }

  /**
   * Get the mob kind.
   * @return a mob kind description (genus/species/subspecies).
   */
  public EntityClassification getWhat()
  {
    return _what;
  }

  /**
   * Set the mob kind.
   * @param what the mob kind to set.
   */
  public void setWhat(EntityClassification what)
  {
    _what=what;
  }

  @Override
  public String toString()
  {
    return _what + " in " + _where;
  }
}

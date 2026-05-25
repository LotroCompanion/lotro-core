package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.lore.agents.AgentClassification;

/**
 * Mob selection.
 * @author DAM
 */
public class MobSelection
{
  private MobLocation _where;
  private AgentClassification _what;

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
   * Get the mob classification.
   * @return a mob classification (class/alignment ; genus/species/subspecies).
   */
  public AgentClassification getWhat()
  {
    return _what;
  }

  /**
   * Set the mob classification.
   * @param what the mob classification to set.
   */
  public void setWhat(AgentClassification what)
  {
    _what=what;
  }

  @Override
  public String toString()
  {
    return _what + " in " + _where;
  }
}

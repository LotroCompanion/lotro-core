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
   * Constructor.
   * @param where Mob location.
   * @param what Mob classification.
   */
  public MobSelection(MobLocation where, AgentClassification what)
  {
    _where=where;
    _what=what;
  }

  /**
   * Get the location of mobs.
   * @return a location string (internal game code).
   */
  public MobLocation getWhere()
  {
    return _where;
  }

  /**
   * Get the mob classification.
   * @return a mob classification (class/alignment ; genus/species/subspecies).
   */
  public AgentClassification getWhat()
  {
    return _what;
  }

  @Override
  public String toString()
  {
    return _what + " in " + _where;
  }
}

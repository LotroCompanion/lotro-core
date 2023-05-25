package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.AgentDescription;

/**
 * Mob description.
 * @author DAM
 */
public class MobDescription extends AgentDescription
{
  private AgentClassification _classification;
  private MobLoot _loot;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public MobDescription(int id, String name)
  {
    super(id,name);
    _classification=new AgentClassification();
  }

  /**
   * Get the mob classification.
   * @return a classification.
   */
  public AgentClassification getClassification()
  {
    return _classification;
  }

  /**
   * Get the loot data for this mob.
   * @return Some loot data or <code>null</code> if not set.
   */
  public MobLoot getLoot()
  {
    return _loot;
  }

  /**
   * Set the mob loot data.
   * @param loot Data to set.
   */
  public void setMobLoot(MobLoot loot)
  {
    _loot=loot;
  }
}

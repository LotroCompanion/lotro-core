package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.common.action.ActionTables;
import delta.games.lotro.common.enums.MobDivision;
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
  private MobDivision _division;
  private ActionTables _actionTables;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public MobDescription(int id, String name)
  {
    super(id,name);
    _classification=new AgentClassification();
    _division=null;
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

  /**
   * Get the associated land division.
   * @return a land division.
   */
  public MobDivision getDivision()
  {
    return _division;
  }

  /**
   * Set the land division.
   * @param division Land division to set.
   */
  public void setDivision(MobDivision division)
  {
    _division=division;
  }

  /**
   * Get the action tables.
   * @return Action tables or <code>null</code>.
   */
  public ActionTables getActionTables()
  {
    return _actionTables;
  }

  /**
   * Set the action tables.
   * @param actionTables Tables to set (may be <code>null</code>).
   */
  public void setActionTables(ActionTables actionTables)
  {
    _actionTables=actionTables;
  }

  @Override
  public String toString()
  {
    return "Mob "+getName()+" (ID="+getIdentifier()+')';
  }
}

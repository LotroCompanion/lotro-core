package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestDescription.FACTION;

/**
 * Filter for quests of a faction.
 * @author DAM
 */
public class QuestFactionFilter implements Filter<QuestDescription>
{
  private FACTION _faction;

  /**
   * Constructor.
   * @param faction Quest faction to select (may be <code>null</code>).
   */
  public QuestFactionFilter(FACTION faction)
  {
    _faction=faction;
  }

  /**
   * Get the quest faction to use.
   * @return A quest faction or <code>null</code>.
   */
  public FACTION getFaction()
  {
    return _faction;
  }

  /**
   * Set the quest faction to select.
   * @param faction Quest faction to use, may be <code>null</code>.
   */
  public void setFaction(FACTION faction)
  {
    _faction=faction;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_faction==null)
    {
      return true;
    }
    FACTION faction=quest.getFaction();
    return (_faction==faction);
  }
}

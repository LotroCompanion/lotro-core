package delta.games.lotro.lore.items.filters;

import delta.games.lotro.character.status.reputation.FactionStatus;
import delta.games.lotro.character.status.reputation.ReputationStatus;
import delta.games.lotro.common.requirements.FactionRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Filter items that can be used by a character, because of reputation.
 * @author DAM
 */
public class ItemCharacterReputationFilter implements ItemFilter
{
  private boolean _enabled;
  private ReputationStatus _status;

  /**
   * Constructor.
   */
  public ItemCharacterReputationFilter()
  {
    _status=null;
    _enabled=true;
  }

  /**
   * Set the reputation status.
   * @param status the reputation status to use.
   */
  public void setReputationStatus(ReputationStatus status)
  {
    _status=status;
  }

  /**
   * Indicates if this filter is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Enable this filter or not.
   * @param enabled <code>true</code> to enable filtering, <code>false</code> to disable filtering.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
  }

  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    UsageRequirement requirements=item.getUsageRequirements();
    FactionRequirement factionRequirement=requirements.getFactionRequirement();
    if (factionRequirement==null)
    {
      return true;
    }
    if (_status==null)
    {
      return false;
    }
    Faction faction=factionRequirement.getFaction();
    FactionStatus status=_status.getFactionStatus(faction);
    if (status==null)
    {
      return false;
    }
    FactionLevel currentLevel=status.getFactionLevel();
    if (currentLevel==null)
    {
      return false;
    }
    int currentTier=currentLevel.getTier();
    int tier=factionRequirement.getTier();
    return (currentTier>=tier);
  }
}

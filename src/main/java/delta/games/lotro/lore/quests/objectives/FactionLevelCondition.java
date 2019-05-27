  package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.utils.Proxy;

/**
 * Faction level condition.
 * @author DAM
 */
public class FactionLevelCondition extends ObjectiveCondition
{
  private Proxy<Faction> _faction;
  private int _tier;

  /**
   * Constructor.
   */
  public FactionLevelCondition()
  {
    _faction=null;
    _tier=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.FACTION_LEVEL;
  }

  /**
   * Get the proxy to the targeted faction.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Faction> getProxy()
  {
    return _faction;
  }

  /**
   * Set the proxy to the targeted faction.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<Faction> proxy)
  {
    _faction=proxy;
  }

  /**
   * Get the tier.
   * @return a tier.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the tier.
   * @param tier the tier to set.
   */
  public void setTier(int tier)
  {
    _tier=tier;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    if (_faction!=null)
    {
      sb.append(": Get Tier ").append(_tier);
      sb.append("reputation with: ").append(_faction);
    }
    return sb.toString();
  }
}

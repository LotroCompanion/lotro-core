package delta.games.lotro.common.requirements;

import delta.common.utils.NumericTools;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Faction requirement.
 * @author DAM
 */
public class FactionRequirement
{
  private static final String SEPARATOR=";";
  private Faction _faction;
  private int _tier;

  /**
   * Constructor.
   * @param faction Faction.
   * @param tier Required tier.
   */
  public FactionRequirement(Faction faction, int tier)
  {
    _faction=faction;
    _tier=tier;
  }

  /**
   * Get the targeted faction.
   * @return a faction.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Get the required tier.
   * @return a tier.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_faction.getIdentifier());
    sb.append(SEPARATOR);
    sb.append(_tier);
    return sb.toString();
  }

  /**
   * Build a faction requirement from a string.
   * @param input Input string ("factionKey;tier").
   * @return A faction requirement or <code>null</code> if none.
   */
  public static FactionRequirement fromString(String input)
  {
    FactionRequirement ret=null;
    if ((input!=null) && (input.length()>0))
    {
      String[] parts=input.split(SEPARATOR);
      if (parts.length==2)
      {
        int factionId=NumericTools.parseInt(parts[0],0);
        Faction faction=FactionsRegistry.getInstance().getById(factionId);
        if (faction!=null)
        {
          int tier=NumericTools.parseInt(parts[1],1);
          ret=new FactionRequirement(faction,tier);
        }
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    if (_faction!=null)
    {
      FactionLevel level=_faction.getLevelByTier(_tier);
      if (level!=null)
      {
        String tierName=level.getName();
        return _faction.getName()+":"+tierName;
      }
    }
    return "";
  }
}

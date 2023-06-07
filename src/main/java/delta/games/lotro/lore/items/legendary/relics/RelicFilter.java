package delta.games.lotro.lore.items.legendary.relics;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.RunicTier;

/**
 * Filter for relics.
 * @author DAM
 */
public class RelicFilter implements Filter<Relic>
{
  private RelicType _type;
  private RunicTier _tier;
  private String _nameContains;
  private String _statsContains;

  /**
   * Constructor.
   */
  public RelicFilter()
  {
    _type=null;
    _tier=null;
    _nameContains=null;
    _statsContains=null;
  }

  /**
   * Get the name filter.
   * @return A name filter or <code>null</code> for no filter.
   */
  public String getNameFilter()
  {
    return _nameContains;
  }

  /**
   * Set the pattern used to filter items on their name.
   * @param nameContains A pattern or <code>null</code>.
   */
  public void setNameFilter(String nameContains)
  {
    if (nameContains!=null)
    {
      nameContains=nameContains.toLowerCase();
    }
    _nameContains=nameContains;
  }

  /**
   * Get the stats filter.
   * @return A stats filter or <code>null</code> for no filter.
   */
  public String getStatsFilter()
  {
    return _statsContains;
  }

  /**
   * Set the pattern used to filter items on their stats.
   * @param statsContains A pattern or <code>null</code>.
   */
  public void setStatsFilter(String statsContains)
  {
    if (statsContains!=null)
    {
      statsContains=statsContains.toLowerCase();
    }
    _statsContains=statsContains;
  }

  /**
   * Set relic category.
   * @param tier A tier or <code>null</code> to accept all.
   */
  public void setRelicTier(RunicTier tier)
  {
    _tier=tier;
  }

  /**
   * Get the relic tier.
   * @return a tier or <code>null</code>.
   */
  public RunicTier getRelicTier()
  {
    return _tier;
  }

  /**
   * Set relic type.
   * @param type A relic type or <code>null</code> to accept all.
   */
  public void setRelicType(RelicType type)
  {
    _type=type;
  }

  /**
   * Get the relic type.
   * @return a type or <code>null</code>.
   */
  public RelicType getRelicType()
  {
    return _type;
  }

  /**
   * Filter a relic.
   * @param relic Relic to test.
   * @return <code>true</code> if it passes the filter, <code>false</code> otherwise.
   */
  public boolean accept(Relic relic)
  {
    boolean ret=true;
    if (_type!=null)
    {
      if (!relic.hasType(_type)) return false;
    }
    if (_tier!=null)
    {
      RunicTier tier=relic.getTier();
      if (tier!=_tier)
      {
        return false;
      }
    }
    if (_nameContains!=null)
    {
      String label=relic.getName().toLowerCase();
      ret=label.contains(_nameContains);
      if (!ret) return false;
    }
    if (_statsContains!=null)
    {
      String stats=relic.getStats().toString().toLowerCase();
      ret=stats.contains(_statsContains);
      if (!ret) return false;
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    boolean empty=true;
    if (_type!=null)
    {
      sb.append("Type: ").append(_type);
      empty=false;
    }
    if (_tier!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Tier: ").append(_tier);
      empty=false;
    }
    if (_nameContains!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Name contains: [").append(_nameContains).append(']');
      empty=false;
    }
    if (_statsContains!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Stats contains: [").append(_statsContains).append(']');
    }
    return sb.toString();
  }
}

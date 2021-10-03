package delta.games.lotro.lore.items.legendary2;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Legendary item instance (reloaded).
 * @author DAM
 */
public class LegendaryItemInstance2 extends ItemInstance<LegendaryItem2> implements LegendaryInstance2
{
  // Legendary attributes.
  private LegendaryInstanceAttrs2 _attrs;

  /**
   * Constructor.
   * @param attrs Legendary attributes.
   */
  public LegendaryItemInstance2(LegendaryAttrs2 attrs)
  {
    super();
    _attrs=new LegendaryInstanceAttrs2(attrs.getSockets());
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryItemInstance2(LegendaryItemInstance2 source)
  {
    super();
    copyFrom(source);
  }

  /**
   * Get the legendary attributes.
   * @return the legendary attributes.
   */
  public LegendaryInstanceAttrs2 getLegendaryAttributes()
  {
    return _attrs;
  }

  /**
   * Set the legendary attributes.
   * @param attrs Attributes to set.
   */
  public void setLegendaryAttributes(LegendaryInstanceAttrs2 attrs)
  {
    _attrs=attrs;
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @Override
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    super.copyFrom(itemInstance);
    if (itemInstance instanceof LegendaryItemInstance2)
    {
      LegendaryItemInstance2 legendaryItemInstance=(LegendaryItemInstance2)itemInstance;
      _attrs=new LegendaryInstanceAttrs2(legendaryItemInstance._attrs);
    }
  }

  /**
   * Get the stats for this item.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStats(super.getStats());
    BasicStatsSet legendaryStats=_attrs.getStats();
    ret.addStats(legendaryStats);
    return ret;
  }

  /**
   * Dump the contents of this legendary item instance as a string.
   * @return A readable string.
   */
  @Override
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    String itemDump=super.dump();
    sb.append(itemDump);
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Legendary attrs: ");
    sb.append(_attrs.dump());
    return sb.toString().trim();
  }
}

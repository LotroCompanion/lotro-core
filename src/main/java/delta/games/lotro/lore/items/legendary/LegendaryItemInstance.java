package delta.games.lotro.lore.items.legendary;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Legendary item instance.
 * @author DAM
 */
public class LegendaryItemInstance extends ItemInstance<LegendaryItem> implements LegendaryInstance
{
  // Legendary attributes.
  private LegendaryInstanceAttrs _attrs;

  /**
   * Constructor.
   */
  public LegendaryItemInstance()
  {
    super();
    _attrs=new LegendaryInstanceAttrs();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryItemInstance(LegendaryItemInstance source)
  {
    this();
    copyFrom(source);
  }

  /**
   * Get the legendary attributes.
   * @return the legendary attributes.
   */
  public LegendaryInstanceAttrs getLegendaryAttributes()
  {
    return _attrs;
  }

  /**
   * Set the legendary attributes.
   * @param attrs Attributes to set.
   */
  public void setLegendaryAttributes(LegendaryInstanceAttrs attrs)
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
    if (itemInstance instanceof LegendaryItemInstance)
    {
      LegendaryItemInstance legendaryItemInstance=(LegendaryItemInstance)itemInstance;
      _attrs=new LegendaryInstanceAttrs(legendaryItemInstance._attrs);
    }
  }

  /**
   * Get the stats for this item, including:
   * <ul>
   * <li>passives,
   * <li>title stats,
   * <li>relics stats.
   * </ul>
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStats(super.getStats());
    Integer itemLevel=getItemLevel();
    int itemLevelValue=(itemLevel!=null)?itemLevel.intValue():0;
    BasicStatsSet legendaryStats=_attrs.getRawStats(itemLevelValue);
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
    return sb.toString();
  }
}

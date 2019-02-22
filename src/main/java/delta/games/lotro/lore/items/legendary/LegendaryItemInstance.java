package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Legendary item instance.
 * @author DAM
 */
public class LegendaryItemInstance extends ItemInstance<LegendaryItem> implements LegendaryInstance
{
  // Legendary attributes.
  private LegendaryAttrs _attrs;

  /**
   * Constructor.
   */
  public LegendaryItemInstance()
  {
    super();
    _attrs=new LegendaryAttrs();
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
  public LegendaryAttrs getLegendaryAttributes()
  {
    return _attrs;
  }

  /**
   * Set the legendary attributes.
   * @param attrs Attributes to set.
   */
  public void setLegendaryAttributes(LegendaryAttrs attrs)
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
      _attrs=new LegendaryAttrs(legendaryItemInstance._attrs);
    }
  }

  /**
   * Get the total stats for this item, including:
   * <ul>
   * <li>passives,
   * <li>title stats,
   * <li>relics stats.
   * </ul>
   * @return a set of stats.
   */
  public BasicStatsSet getTotalStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    Integer itemLevel=getItemLevel();
    int itemLevelValue=(itemLevel!=null)?itemLevel.intValue():0;
    BasicStatsSet legendaryStats=_attrs.getRawStats(itemLevelValue);
    ret.addStats(legendaryStats);
    return ret;
  }
}

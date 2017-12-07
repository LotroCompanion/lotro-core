package delta.games.lotro.lore.items.legendary;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;

/**
 * Legendary item description.
 * @author DAM
 */
public class LegendaryItem extends Item implements Legendary
{
  private LegendaryAttrs _attrs;

  /**
   * Constructor.
   */
  public LegendaryItem()
  {
    setCategory(ItemCategory.LEGENDARY_ITEM);
    _attrs=new LegendaryAttrs();
  }

  /**
   * Copy constructor.
   * @param item Source.
   */
  public LegendaryItem(LegendaryItem item)
  {
    this();
    _attrs=new LegendaryAttrs(item._attrs);
    copyFrom(item);
  }

  /**
   * Get passive stats.
   * @return a set of stats.
   */
  public BasicStatsSet getPassives()
  {
    return getStats();
  }

  /**
   * Get the legendary attributes.
   * @return the legendary attributes.
   */
  public LegendaryAttrs getLegendaryAttrs()
  {
    return _attrs;
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
  @Override
  public BasicStatsSet getTotalStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    BasicStatsSet legendaryStats=_attrs.getRawStats();
    ret.addStats(legendaryStats);
    ret.addStats(getPassives());
    return ret;
  }

  /**
   * Dump the contents of this weapon as a string.
   * @return A readable string.
   */
  @Override
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    String itemDump=super.dump();
    sb.append(itemDump);
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append(_attrs.dump());
    return sb.toString();
  }
}

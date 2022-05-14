package delta.games.lotro.lore.items.legendary;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;

/**
 * Instance of the legendary weapon.
 * @author DAM
 */
public class LegendaryWeaponInstance extends WeaponInstance<LegendaryWeapon> implements LegendaryInstance
{
  // Legendary attributes.
  private LegendaryInstanceAttrs _attrs;

  /**
   * Constructor.
   */
  public LegendaryWeaponInstance()
  {
    super();
    _attrs=new LegendaryInstanceAttrs();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryWeaponInstance(LegendaryWeaponInstance source)
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
    if (itemInstance instanceof LegendaryWeaponInstance)
    {
      LegendaryWeaponInstance weapon=(LegendaryWeaponInstance)itemInstance;
      _attrs=new LegendaryInstanceAttrs(weapon._attrs);
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
  @Override
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStats(super.getStats());
    int itemLevel=getApplicableItemLevel();
    BasicStatsSet legendaryStats=_attrs.getRawStats(itemLevel);
    ret.addStats(legendaryStats);
    return ret;
  }

  /**
   * Dump the contents of this legendary weapon instance as a string.
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

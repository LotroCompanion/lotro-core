package delta.games.lotro.lore.items.legendary;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.Weapon;

/**
 * Legendary weapon description.
 * @author DAM
 */
public class LegendaryWeapon extends Weapon implements Legendary
{
  private LegendaryAttrs _attrs;
  // TODO compute DPS based on stars

  /**
   * Constructor.
   */
  public LegendaryWeapon()
  {
    super();
    _attrs=new LegendaryAttrs();
    setCategory(ItemCategory.LEGENDARY_WEAPON);
  }

  /**
   * Copy constructor.
   * @param weapon Source weapon.
   */
  public LegendaryWeapon(LegendaryWeapon weapon)
  {
    super(weapon);
    _attrs=new LegendaryAttrs(weapon._attrs);
    setCategory(ItemCategory.LEGENDARY_WEAPON);
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
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    String weaponDump=super.dump();
    sb.append(weaponDump);
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append(_attrs.dump());
    return sb.toString();
  }
}

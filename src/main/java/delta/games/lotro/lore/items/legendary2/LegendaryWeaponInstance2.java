package delta.games.lotro.lore.items.legendary2;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;

/**
 * Instance of the legendary weapon (reloaded).
 * @author DAM
 */
public class LegendaryWeaponInstance2 extends WeaponInstance<LegendaryWeapon2> implements LegendaryInstance2
{
  // Legendary attributes.
  private LegendaryInstanceAttrs2 _attrs;

  /**
   * Constructor.
   * @param attrs Legendary attributes.
   */
  public LegendaryWeaponInstance2(LegendaryAttrs2 attrs)
  {
    super();
    _attrs=new LegendaryInstanceAttrs2(attrs.getSockets());
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryWeaponInstance2(LegendaryWeaponInstance2 source)
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
    if (itemInstance instanceof LegendaryWeaponInstance2)
    {
      LegendaryWeaponInstance2 weapon=(LegendaryWeaponInstance2)itemInstance;
      _attrs=new LegendaryInstanceAttrs2(weapon._attrs);
    }
  }

  @Override
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStats(super.getStats());
    // Legendary stats
    int itemLevel=getApplicableItemLevel();
    int characterLevel=getWearerLevel();
    BasicStatsSet legendaryStats=_attrs.getStats(itemLevel,characterLevel);
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

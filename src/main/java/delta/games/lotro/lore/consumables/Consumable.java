package delta.games.lotro.lore.consumables;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Consumable.
 * @author DAM
 */
public class Consumable implements Identifiable
{
  private int _identifier;
  private String _name;
  private String _icon;
  private ItemClass _category;
  private StatsProvider _provider;

  /**
   * Constructor.
   * @param id Item identifier.
   * @param name Item name.
   * @param icon Item icon.
   * @param category Item category.
   */
  public Consumable(int id, String name, String icon, ItemClass category)
  {
    _identifier=id;
    _name=name;
    _icon=icon;
    _category=category;
    _provider=new StatsProvider();
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the consumable name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the consumable icon.
   * @return an icon name.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Get the consumable class.
   * @return an item class.
   */
  public ItemClass getItemClass()
  {
    return _category;
  }

  /**
   * Get the stats provider for this consumable.
   * @return a stats provider.
   */
  public StatsProvider getProvider()
  {
    return _provider;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Consumable: ID=").append(_identifier);
    sb.append(", name=").append(_name);
    sb.append(", category=").append(_category);
    sb.append(", iconID=").append(_icon);
    sb.append(", stats=").append(_provider);
    return sb.toString();
  }
}

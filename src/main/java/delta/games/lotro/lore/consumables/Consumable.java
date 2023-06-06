package delta.games.lotro.lore.consumables;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;

/**
 * Consumable.
 * @author DAM
 */
public class Consumable implements Identifiable
{
  private Item _item;
  private StatsProvider _provider;

  /**
   * Constructor.
   * @param item Parent item.
   */
  public Consumable(Item item)
  {
    _item=item;
    _provider=new StatsProvider();
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _item.getIdentifier();
  }

  /**
   * Get the consumable name.
   * @return a name.
   */
  public String getName()
  {
    return _item.getName();
  }

  /**
   * Get the consumable icon.
   * @return an icon name.
   */
  public String getIcon()
  {
    return _item.getIcon();
  }

  /**
   * Get the consumable class.
   * @return an item class.
   */
  public ItemClass getItemClass()
  {
    return _item.getItemClass();
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
    sb.append("Consumable: ID=").append(getIdentifier());
    sb.append(", name=").append(getName());
    sb.append(", category=").append(getItemClass());
    sb.append(", iconID=").append(getIcon());
    sb.append(", stats=").append(_provider);
    return sb.toString();
  }
}

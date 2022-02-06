package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Container-specific data.
 * @author DAM
 */
public class Container implements Identifiable,Named
{
  private Item _item;

  /**
   * Constructor.
   * @param item Container item.
   */
  public Container(Item item)
  {
    _item=item;
  }

  @Override
  public int getIdentifier()
  {
    return _item.getIdentifier();
  }

  @Override
  public String getName()
  {
    return _item.getName();
  }
}

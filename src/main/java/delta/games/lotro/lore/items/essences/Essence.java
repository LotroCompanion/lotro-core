package delta.games.lotro.lore.items.essences;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;

/**
 * Essence.
 * @author DAM
 */
public class Essence implements Identifiable,Named
{
  private Item _item;
  private SocketType _type;

  /**
   * Constructor.
   * @param item Associated item.
   * @param type Socket type.
   */
  public Essence(Item item, SocketType type)
  {
    _item=item;
    _type=type;
  }

  @Override
  public int getIdentifier()
  {
    return (_item!=null)?_item.getIdentifier():0;
  }

  /**
   * Get the name of this essence.
   * @return a name.
   */
  public String getName()
  {
    return (_item!=null)?_item.getName():"";
  }

  /**
   * Get the associated item.
   * @return the associated item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the socket type.
   * @return a socket type.
   */
  public SocketType getType()
  {
    return _type;
  }

  /**
   * Get the tier of this essence.
   * @return A tier or <code>null</code> if none.
   */
  public Integer getTier()
  {
    return _item.getTier();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Essence ID=").append(getIdentifier());
    sb.append(", name=").append(getName());
    sb.append(", type=").append(_type);
    Integer tier=getTier();
    if (tier!=null)
    {
      sb.append(", tier=").append(tier);
    }
    return sb.toString();
  }
}

package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;

/**
 * Tracery.
 * @author DAM
 */
public class Tracery implements Identifiable,Named
{
  private Item _item;
  private SocketType _type;
  private int _minItemLevel;
  private int _maxItemLevel;

  /**
   * Constructor.
   * @param item Associated item.
   * @param type Socket type.
   * @param minItemLevel Minimum item level.
   * @param maxItemLevel Maximum item level.
   */
  public Tracery(Item item, SocketType type, int minItemLevel, int maxItemLevel)
  {
    _item=item;
    _type=type;
    _minItemLevel=minItemLevel;
    _maxItemLevel=maxItemLevel;
  }

  @Override
  public int getIdentifier()
  {
    return _item.getIdentifier();
  }

  /**
   * Get the name of this tracery.
   * @return a name.
   */
  public String getName()
  {
    return _item.getName();
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
   * Get the minimum item level.
   * @return an item level.
   */
  public int getMinItemLevel()
  {
    return _minItemLevel;
  }

  /**
   * Get the maximum item level.
   * @return an item level.
   */
  public int getMaxItemLevel()
  {
    return _maxItemLevel;
  }
}

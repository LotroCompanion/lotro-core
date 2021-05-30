package delta.games.lotro.lore.items;

/**
 * Interface of an item provider.
 * @author DAM
 */
public interface ItemProvider
{
  /**
   * Get the managed item.
   * @return an item or <code>null</code>.
   */
  Item getItem();
}

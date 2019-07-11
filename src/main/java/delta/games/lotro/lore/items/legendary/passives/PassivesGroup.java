package delta.games.lotro.lore.items.legendary.passives;

import java.util.HashSet;
import java.util.Set;

/**
 * Group of passives.
 * @author DAM
 */
public class PassivesGroup
{
  private Set<Integer> _itemIds;
  private Set<Integer> _passiveIds;

  /**
   * Constructor.
   */
  public PassivesGroup()
  {
    _itemIds=new HashSet<Integer>();
    _passiveIds=new HashSet<Integer>();
  }

  /**
   * Add an item identifier.
   * @param itemId Identifier to add.
   */
  public void addItem(int itemId)
  {
    _itemIds.add(Integer.valueOf(itemId));
  }

  /**
   * Add a passive identifier.
   * @param passiveId Identifier to add.
   */
  public void addPassive(int passiveId)
  {
    _passiveIds.add(Integer.valueOf(passiveId));
  }

  /**
   * Get the managed item identifiers.
   * @return a set of item identifiers.
   */
  public Set<Integer> getItemIds()
  {
    return _itemIds;
  }

  /**
   * Get the managed passive identifiers.
   * @return a set of passive identifiers.
   */
  public Set<Integer> getPassiveIds()
  {
    return _passiveIds;
  }
}

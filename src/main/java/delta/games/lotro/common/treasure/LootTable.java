package delta.games.lotro.common.treasure;

import java.util.Set;

import delta.games.lotro.common.Identifiable;

/**
 * Base class for loot tables.
 * @author DAM
 */
public abstract class LootTable implements Identifiable
{
  private int _id;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public LootTable(int id)
  {
    _id=id;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Indicates if this loot table may contain the given item.
   * @param itemId Identifier of the item to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public abstract boolean contains(int itemId);

  /**
   * Get the identifiers of the reachable items.
   * @return A set of item identifiers.
   */
  public abstract Set<Integer> getItemIds();

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public abstract void dump(StringBuilder sb, int level);
}

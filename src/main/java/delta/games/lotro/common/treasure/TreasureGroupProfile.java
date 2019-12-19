package delta.games.lotro.common.treasure;

import delta.games.lotro.common.Identifiable;

/**
 * Treasure group profile:
 * <ul>
 * <li>items table, OR
 * <li>treasure list
 * </ul>
 * @author DAM
 */
public class TreasureGroupProfile implements Identifiable
{
  private int _id;
  private ItemsTable _itemsTable;
  private TreasureList _treasureList;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param itemsTable Items table.
   * @param treasureList Treasure list.
   */
  public TreasureGroupProfile(int identifier, ItemsTable itemsTable, TreasureList treasureList)
  {
    _id=identifier;
    _itemsTable=itemsTable;
    _treasureList=treasureList;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * @return the itemsTable
   */
  public ItemsTable getItemsTable()
  {
    return _itemsTable;
  }

  /**
   * @return the treasureList
   */
  public TreasureList getTreasureList()
  {
    return _treasureList;
  }

}

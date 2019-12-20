package delta.games.lotro.common.treasure;

import delta.common.utils.text.EndOfLine;
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


  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Treasure group profile ID=").append(_id).append(EndOfLine.NATIVE_EOL);
    if (_itemsTable!=null)
    {
      _itemsTable.dump(sb,level+1);
    }
    if (_treasureList!=null)
    {
      _treasureList.dump(sb,level+1);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}

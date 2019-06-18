package delta.games.lotro.common.id;

import delta.common.utils.NumericTools;

/**
 * Item instance in-game identifier.
 * @author DAM
 */
public class ItemInstanceId extends InternalGameId
{
  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public ItemInstanceId(int id1, int id2)
  {
    super(id1,id2);
  }

  @Override
  public String asString()
  {
    return _id1+InternalGameId.VALUE_SEPARATOR+_id2;
  }

  @Override
  public String toString()
  {
    return "Item instance ID: "+_id1+"/"+_id2;
  }

  /**
   * Build an item instance identifier from a string.
   * @param itemInstanceIdStr Input string.
   * @return An item instance ID or <code>null</code> if not valid.
   */
  public static ItemInstanceId fromString(String itemInstanceIdStr)
  {
    int index=itemInstanceIdStr.indexOf(VALUE_SEPARATOR);
    if (index!=-1)
    {
      Integer id1=NumericTools.parseInteger(itemInstanceIdStr.substring(0,index));
      Integer id2=NumericTools.parseInteger(itemInstanceIdStr.substring(index+1));
      if ((id1!=null) && (id2!=null))
      {
        return new ItemInstanceId(id1.intValue(),id2.intValue());
      }
    }
    return null;
  }
}

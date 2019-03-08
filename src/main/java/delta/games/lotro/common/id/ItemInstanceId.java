package delta.games.lotro.common.id;

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
  public String toString()
  {
    return "Item instance ID: "+_id1+"/"+_id2;
  }
}

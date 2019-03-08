package delta.games.lotro.common.id;

/**
 * Account in-game identifier.
 * @author DAM
 */
public class AccountId extends EntityId
{
  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public AccountId(int id1, int id2)
  {
    super(id1,id2);
  }

  @Override
  public String toString()
  {
    return "Account ID: "+_id1+"/"+_id2;
  }
}

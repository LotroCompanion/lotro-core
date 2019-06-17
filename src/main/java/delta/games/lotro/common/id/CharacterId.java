package delta.games.lotro.common.id;

/**
 * Character in-game identifier.
 * @author DAM
 */
public class CharacterId extends EntityId
{
  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public CharacterId(int id1, int id2)
  {
    super(id1,id2);
  }

  @Override
  public String asString()
  {
    return EntityId.characterType+EntityId.TYPE_SEPARATOR+_id1+EntityId.VALUE_SEPARATOR+_id2;
  }

  @Override
  public String toString()
  {
    return "Character ID: "+_id1+"/"+_id2;
  }
}

package delta.games.lotro.common.id;

/**
 * Base class for entity identifiers (characters, accounts).
 * @author DAM
 */
public class EntityId extends InternalGameId
{
  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public EntityId(int id1, int id2)
  {
    super(id1,id2);
  }
}

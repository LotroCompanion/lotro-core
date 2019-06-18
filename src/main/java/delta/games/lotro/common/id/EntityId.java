package delta.games.lotro.common.id;

import delta.common.utils.NumericTools;

/**
 * Base class for entity identifiers (characters, accounts).
 * @author DAM
 */
public abstract class EntityId extends InternalGameId
{
  protected static final String characterType="character";
  protected static final String accountType="account";

  protected static final char TYPE_SEPARATOR=':';

  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public EntityId(int id1, int id2)
  {
    super(id1,id2);
  }

  /**
   * Build an entity identifier from a string.
   * @param entityStr Input string.
   * @return An entity or <code>null</code> if not valid.
   */
  public static EntityId fromString(String entityStr)
  {
    int index=entityStr.indexOf(TYPE_SEPARATOR);
    if (index!=-1)
    {
      String type=entityStr.substring(0,index);
      String value=entityStr.substring(index+1);
      int index2=value.indexOf(VALUE_SEPARATOR);
      if (index2!=-1)
      {
        Integer id1=NumericTools.parseInteger(value.substring(0,index2));
        Integer id2=NumericTools.parseInteger(value.substring(index2+1));
        if ((id1!=null) && (id2!=null))
        {
          if (characterType.contentEquals(type))
          {
            return new CharacterId(id1.intValue(),id2.intValue());
          }
          else if (accountType.contentEquals(type))
          {
            return new AccountId(id1.intValue(),id2.intValue());
          }
        }
      }
    }
    return null;
  }
}

package delta.games.lotro.common.id;

import delta.common.utils.NumericTools;

/**
 * Base class for entity identifiers (characters, accounts).
 * @author DAM
 */
public class EntityId extends InternalGameId
{
  protected static final char TYPE_SEPARATOR=':';

  private EntityType _type;

  /**
   * Constructor.
   * @param type Entity type.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public EntityId(EntityType type, int id1, int id2)
  {
    super(id1,id2);
    _type=type;
  }

  /**
   * Get the entity type.
   * @return the entity type.
   */
  public EntityType getType()
  {
    return _type;
  }

  @Override
  public String asString()
  {
    return _type.name()+EntityId.TYPE_SEPARATOR+_id1+EntityId.VALUE_SEPARATOR+_id2;
  }

  @Override
  public String toString()
  {
    return _type+": "+_id1+"/"+_id2;
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
          EntityType entityType=EntityType.fromString(type);
          return new EntityId(entityType,id1.intValue(),id2.intValue());
        }
      }
    }
    return null;
  }
}

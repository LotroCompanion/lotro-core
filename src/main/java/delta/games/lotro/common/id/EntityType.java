package delta.games.lotro.common.id;

import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Entity type.
 * @author DAM
 */
public enum EntityType
{
  /**
   * Account.
   */
  ACCOUNT("Account"),
  /**
   * Character.
   */
  CHARACTER("Character"),
  /**
   * Unknown.
   */
  UNKNOWN("Unknown");

  private static final Logger LOGGER=Logger.getLogger(EntityType.class);

  private String _label;

  private EntityType(String label)
  {
    _label=label;
  }

  /**
   * Build an entity type from a string.
   * @param entityStr Input string.
   * @return An entity type or <code>UNKNOWN</code> if not known...
   */
  public static EntityType fromString(String entityStr)
  {
    EntityType ret=EntityType.UNKNOWN;
    if (entityStr!=null)
    {
      try
      {
        ret=EntityType.valueOf(entityStr.toUpperCase(Locale.ENGLISH));
      }
      catch(Exception e)
      {
        LOGGER.warn("Unknown entity type ["+entityStr+"]", e);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}

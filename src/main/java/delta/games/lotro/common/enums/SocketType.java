package delta.games.lotro.common.enums;

/**
 * Socket Type.
 * @author DAM
 */
public class SocketType extends LotroEnumEntry
{
  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get a socket type using its key.
   * @param key Key of socket type.
   * @return A socket type instance or <code>null</code> if not found.
   */
  public static SocketType getSocketTypeByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(SocketType.class).getByKey(key);
  }
}

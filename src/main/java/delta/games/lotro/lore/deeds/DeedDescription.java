package delta.games.lotro.lore.deeds;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.quests.Achievable;

/**
 * LOTRO deed description.
 * @author DAM
 */
public class DeedDescription extends Achievable
{
  /**
   * Deed legacy key (lotro-wiki key).
   */
  private String _key;
  /**
   * Deed type.
   */
  private DeedType _type;

  /**
   * Constructor.
   */
  public DeedDescription()
  {
    super();
    _key=null;
    _type=DeedType.SLAYER;
  }

  /**
   * Get the key of this deed.
   * @return the key of this deed.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key of this deed.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get an identifying key for this deed.
   * @return an identifying key.
   */
  public String getIdentifyingKey()
  {
    if (_key!=null) return _key;
    return String.valueOf(getIdentifier());
  }

  /**
   * Get the type of this deed.
   * @return the type of this deed.
   */
  public DeedType getType()
  {
    return _type;
  }

  /**
   * Set the type of this deed. 
   * @param type the type to set.
   */
  public void setType(DeedType type)
  {
    _type=type;
  }

  /**
   * Dump the contents of this deed as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    super.dumpFirstLine(sb);
    if (_key!=null)
    {
      sb.append(" (");
      sb.append(_key);
      sb.append(')');
    }
    if (_type!=null)
    {
      sb.append(" (");
      sb.append(_type);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }
}

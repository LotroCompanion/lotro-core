package delta.games.lotro.common.stats;

import delta.games.lotro.common.Identifiable;

/**
 * Stat description.
 * @author DAM
 */
public class StatDescription implements Identifiable
{
  private int _id;
  private String _key;
  private String _legacyKey;
  private String _name;
  private boolean _isPercentage;

  /**
   * Constructor.
   * @param id
   */
  public StatDescription(int id)
  {
    _id=id;
  }

  /**
   * Get the stat identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the stat key.
   * @return a string key or <code>null</code> if none.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the stat string key.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the legacy key for this stat.
   * @return a string key or <code>null</code> if none.
   */
  public String getLegacyKey()
  {
    return _legacyKey;
  }

  /**
   * Set the legacy key for this stat.
   * @param legacyKey the legacy key to set (may be <code>null</code>).
   */
  public void setLegacyKey(String legacyKey)
  {
    _legacyKey=legacyKey;
  }

  /**
   * Get the persistence key for this stat.
   * @return a persistence key string.
   */
  public String getPersistenceKey()
  {
    if (_legacyKey!=null) return _legacyKey;
    return /*_name + " / " + */_key;
  }

  /**
   * Get the displayable name for this stat.
   * @return a name.
   */
  public String getName()
  {
    return _name==null?"":_name;
  }

  /**
   * Set the name of this stat.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Indicates if this stat represents a percentage or not.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean isPercentage()
  {
    return _isPercentage;
  }

  /**
   * Set the 'is percentage' flag.
   * @param isPercentage Value to set.
   */
  public void setPercentage(boolean isPercentage)
  {
    _isPercentage=isPercentage;
  }

  @Override
  public String toString()
  {
    return "STAT: id="+_id+",key="+_key+", legacyKey="+_legacyKey+", name="+_name;
  }
}

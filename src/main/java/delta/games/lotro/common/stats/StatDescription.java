package delta.games.lotro.common.stats;

import delta.games.lotro.common.Identifiable;

/**
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
   * @return the id
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * @return the key
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * @param key the key to set
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * @return the legacyKey
   */
  public String getLegacyKey()
  {
    return _legacyKey;
  }

  /**
   * @param legacyKey the legacyKey to set
   */
  public void setLegacyKey(String legacyKey)
  {
    _legacyKey=legacyKey;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return _name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * @return the isPercentage
   */
  public boolean isPercentage()
  {
    return _isPercentage;
  }

  /**
   * @param isPercentage the isPercentage to set
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

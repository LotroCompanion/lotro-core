package delta.games.lotro.character.storage.currencies;

import delta.games.lotro.common.Scope;

/**
 * Currency.
 * @author DAM
 */
public class Currency
{
  /**
   * Internal identifying key.
   */
  private String _key;
  /**
   * Optional legacy key.
   */
  private String _legacyKey;
  /**
   * Displayable name.
   */
  private String _name;
  /**
   * Scope of currency.
   */
  private Scope _scope;
  /**
   * Category of currency.
   */
  private String _category;
  /**
   * Factor between real value and internal value (for gold).
   */
  private int _factor;

  /**
   * Constructor.
   * @param key Identifying key.
   * @param name Displayable name.
   * @param scope Scope.
   * @param category Category.
   */
  public Currency(String key, String name, Scope scope, String category)
  {
    _key=key;
    _name=name;
    _scope=scope;
    _category=category;
    _factor=1;
  }

  /**
   * Get the identifying key for this currency.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the legacy key.
   * @return A legacy key or <code>null</code>.
   */
  public String getLegacyKey()
  {
    return _legacyKey;
  }

  /**
   * Set the legacy key.
   * @param legacyKey Legacy key to set.
   */
  public void setLegacyKey(String legacyKey)
  {
    _legacyKey=legacyKey;
  }

  /**
   * Get the key to use for persistence.
   * @return A key.
   */
  public String getPersistenceKey()
  {
    if (_legacyKey!=null)
    {
      return _legacyKey;
    }
    return _key;
  }

  /**
   * Get the displayable name for this currency.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the scope of this currency.
   * @return a scope.
   */
  public Scope getScope()
  {
    return _scope;
  }

  /**
   * Get the category of this currency.
   * @return a category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Get the factor between real value and internal value.
   * @return A factor.
   */
  public int getFactor()
  {
    return _factor;
  }

  /**
   * Set the factor value.
   * @param factor Factor to set.
   */
  public void setFactor(int factor)
  {
    _factor=factor;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Currency: key=").append(_key);
    sb.append(", name=").append(_name);
    sb.append(", scope=").append(_scope);
    sb.append(", category=").append(_category);
    if (_factor!=1)
    {
      sb.append(", factor=");
      sb.append(_factor);
    }
    return sb.toString();
  }
}

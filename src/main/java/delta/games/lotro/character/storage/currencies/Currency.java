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

  @Override
  public String toString()
  {
    return "Currency: key="+_key+", name="+_name+", scope="+_scope+", category="+_category;
  }
}

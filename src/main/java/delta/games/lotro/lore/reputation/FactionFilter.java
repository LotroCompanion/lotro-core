package delta.games.lotro.lore.reputation;

import delta.common.utils.collections.filters.Filter;

/**
 * Filter for faction.
 * @author DAM
 */
public class FactionFilter implements Filter<Faction>
{
  private String _category;

  /**
   * Constructor.
   */
  public FactionFilter()
  {
    _category=null;
  }

  /**
   * Get the category of selected items.
   * @return A category or <code>null</code> for no filter.
   */
  public String getCategory()
  {
    return _category;
  }
  
  /**
   * Set the category of selected items.
   * @param category A category or <code>null</code>.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  public boolean accept(Faction faction)
  {
    boolean ret=true;
    if (_category!=null)
    {
      String category=faction.getCategory();
      ret=_category.equals(category);
      if (!ret) return false;
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Faction filter: ");
    if (_category!=null)
    {
      sb.append(" Category: ").append(_category);
    }
    return sb.toString();
  }
}

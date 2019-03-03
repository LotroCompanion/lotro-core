package delta.games.lotro.lore.items.legendary.titles;

import delta.common.utils.collections.filters.Filter;

/**
 * Filter for legendary titles.
 * @author DAM
 */
public class LegendaryTitleFilter implements Filter<LegendaryTitle>
{
  private String _category;
  private String _nameContains;
  private String _statsContains;

  /**
   * Constructor.
   */
  public LegendaryTitleFilter()
  {
    _category=null;
    _nameContains=null;
    _statsContains=null;
  }

  /**
   * Get the name filter.
   * @return A name filter or <code>null</code> for no filter.
   */
  public String getNameFilter()
  {
    return _nameContains;
  }

  /**
   * Set the pattern used to filter items on their name.
   * @param nameContains A pattern or <code>null</code>.
   */
  public void setNameFilter(String nameContains)
  {
    if (nameContains!=null)
    {
      nameContains=nameContains.toLowerCase();
    }
    _nameContains=nameContains;
  }

  /**
   * Get the stats filter.
   * @return A stats filter or <code>null</code> for no filter.
   */
  public String getStatsFilter()
  {
    return _statsContains;
  }

  /**
   * Set the pattern used to filter items on their stats.
   * @param statsContains A pattern or <code>null</code>.
   */
  public void setStatsFilter(String statsContains)
  {
    if (statsContains!=null)
    {
      statsContains=statsContains.toLowerCase();
    }
    _statsContains=statsContains;
  }

  /**
   * Set category.
   * @param category A category or <code>null</code> to accept all.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  /**
   * Get the category.
   * @return a category or <code>null</code>.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Filter a legendary title.
   * @param title Legendary title to test.
   * @return <code>true</code> if it passes the filter, <code>false</code> otherwise.
   */
  public boolean accept(LegendaryTitle title)
  {
    boolean ret=true;
    if (_category!=null)
    {
      if (!_category.equals(title.getCategory())) return false;
    }
    if (_nameContains!=null)
    {
      String label=title.getName().toLowerCase();
      ret=label.contains(_nameContains);
      if (!ret) return false;
    }
    if (_statsContains!=null)
    {
      String stats=title.getStats().toString().toLowerCase();
      ret=stats.contains(_statsContains);
      if (!ret) return false;
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    boolean empty=true;
    if (_category!=null)
    {
      sb.append("Category: ").append(_category);
      empty=false;
    }
    if (_nameContains!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Name contains: [").append(_nameContains).append(']');
      empty=false;
    }
    if (_statsContains!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Stats contains: [").append(_statsContains).append(']');
      empty=false;
    }
    return sb.toString();
  }
}

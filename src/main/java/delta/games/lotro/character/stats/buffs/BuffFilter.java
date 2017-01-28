package delta.games.lotro.character.stats.buffs;

import delta.common.utils.collections.filters.Filter;

/**
 * Filter for buffs.
 * @author DAM
 */
public class BuffFilter implements Filter<Buff>
{
  private String _category;
  private String _nameContains;

  /**
   * Constructor.
   */
  public BuffFilter()
  {
    _category=null;
    _nameContains=null;
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
   * Filter a buff.
   * @param buff Buff to test.
   * @return <code>true</code> if it passes the filter, <code>false</code> otherwise.
   */
  public boolean accept(Buff buff)
  {
    boolean ret=true;
    if (_category!=null)
    {
      if (!_category.equals(buff.getCategory())) return false;
    }
    if (_nameContains!=null)
    {
      String label=buff.getLabel().toLowerCase();
      ret=label.contains(_nameContains);
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
      if (!empty) sb.append(", ");
      sb.append("Category: ").append(_category);
      empty=false;
    }
    if (_nameContains!=null)
    {
      if (!empty) sb.append(", ");
      sb.append("Name contains: [").append(_nameContains).append(']');
      empty=false;
    }
    return sb.toString();
  }
}

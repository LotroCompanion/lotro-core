package delta.games.lotro.crafting;

import delta.common.utils.collections.filters.Filter;

/**
 * Filter for profession.
 * @author DAM
 */
public class ProfessionFilter implements Filter<Profession>
{
  private Profession _profession;

  /**
   * Constructor.
   */
  public ProfessionFilter()
  {
    _profession=null;
  }

  /**
   * Get the profession of selected items.
   * @return A profession or <code>null</code> for no filter.
   */
  public Profession getProfession()
  {
    return _profession;
  }
  
  /**
   * Set the profession of selected items.
   * @param profession A profession or <code>null</code>.
   */
  public void setProfession(Profession profession)
  {
    _profession=profession;
  }

  public boolean accept(Profession profession)
  {
    if (_profession!=null)
    {
      return _profession.equals(profession);
    }
    return true;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Profession filter: ");
    if (_profession!=null)
    {
      sb.append(" Profession: ").append(_profession);
    }
    return sb.toString();
  }
}

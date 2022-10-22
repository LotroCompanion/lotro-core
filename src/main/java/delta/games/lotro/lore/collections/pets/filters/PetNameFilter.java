package delta.games.lotro.lore.collections.pets.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;

/**
 * Filter on pet name.
 * @author DAM
 */
public class PetNameFilter implements Filter<CosmeticPetDescription>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public PetNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public PetNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true, true);
    _pattern=pattern;
  }

  /**
   * Get the pattern to use to filter name.
   * @return A pattern string.
   */
  public String getPattern()
  {
    return _pattern;
  }

  /**
   * Set the string pattern.
   * @param pattern Pattern to set.
   */
  public void setPattern(String pattern)
  {
    if (pattern==null)
    {
      pattern="";
    }
    _pattern=pattern;
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true, true);
  }

  public boolean accept(CosmeticPetDescription pet)
  {
    String name=pet.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}

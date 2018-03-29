package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Filter for deed using the class requirement.
 * @author DAM
 */
public class DeedClassRequirementFilter implements Filter<DeedDescription>
{
  private CharacterClass _characterClass;

  /**
   * Constructor.
   * @param characterClass Class to select (may be <code>null</code>).
   */
  public DeedClassRequirementFilter(CharacterClass characterClass)
  {
    _characterClass=characterClass;
  }

  /**
   * Get the character class to use.
   * @return A character class or <code>null</code>.
   */
  public CharacterClass getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Set the character class to select.
   * @param characterClass Character class to use, may be <code>null</code>.
   */
  public void setCharacterClass(CharacterClass characterClass)
  {
    _characterClass=characterClass;
  }

  public boolean accept(DeedDescription deed)
  {
    if (_characterClass==null)
    {
      return true;
    }
    return deed.getRequiredClass()==_characterClass;
  }
}

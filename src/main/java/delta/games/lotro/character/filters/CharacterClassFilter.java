package delta.games.lotro.character.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.common.CharacterClass;

/**
 * Filter for characters of a given class.
 * @author DAM
 */
public class CharacterClassFilter implements Filter<BaseCharacterSummary>
{
  private CharacterClass _class;

  /**
   * Constructor.
   * @param characterClass Class to select (may be <code>null</code>).
   */
  public CharacterClassFilter(CharacterClass characterClass)
  {
    _class=characterClass;
  }

  /**
   * Get the class to use.
   * @return A class or <code>null</code>.
   */
  public CharacterClass getCharacterClass()
  {
    return _class;
  }

  /**
   * Set the class to select.
   * @param characterClass Class to use, may be <code>null</code>.
   */
  public void setCharacterClass(CharacterClass characterClass)
  {
    _class=characterClass;
  }

  @Override
  public boolean accept(BaseCharacterSummary summary)
  {
    if (_class==null)
    {
      return true;
    }
    return summary.getCharacterClass()==_class;
  }
}

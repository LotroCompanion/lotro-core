package delta.games.lotro.character.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.CharacterReference;
import delta.games.lotro.character.classes.ClassDescription;

/**
 * Filter for characters of a given class.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class CharacterClassFilter<T extends CharacterReference> implements Filter<T>
{
  private ClassDescription _class;

  /**
   * Constructor.
   * @param characterClass Class to select (may be <code>null</code>).
   */
  public CharacterClassFilter(ClassDescription characterClass)
  {
    _class=characterClass;
  }

  /**
   * Get the class to use.
   * @return A class or <code>null</code>.
   */
  public ClassDescription getCharacterClass()
  {
    return _class;
  }

  /**
   * Set the class to select.
   * @param characterClass Class to use, may be <code>null</code>.
   */
  public void setCharacterClass(ClassDescription characterClass)
  {
    _class=characterClass;
  }

  @Override
  public boolean accept(T summary)
  {
    if (_class==null)
    {
      return true;
    }
    return summary.getCharacterClass()==_class;
  }
}

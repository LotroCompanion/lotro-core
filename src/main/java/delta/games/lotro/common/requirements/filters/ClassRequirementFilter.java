package delta.games.lotro.common.requirements.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.common.requirements.ClassRequirement;

/**
 * Filter for a class requirement.
 * @author DAM
 */
public class ClassRequirementFilter implements Filter<ClassRequirement>
{
  private ClassDescription _characterClass;

  /**
   * Constructor.
   */
  public ClassRequirementFilter()
  {
    _characterClass=null;
  }

  /**
   * Get the character class to use.
   * @return A character class or <code>null</code>.
   */
  public ClassDescription getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Set the character class to select.
   * @param characterClass Character class to use, may be <code>null</code>.
   */
  public void setCharacterClass(ClassDescription characterClass)
  {
    _characterClass=characterClass;
  }

  @Override
  public boolean accept(ClassRequirement classRequirement)
  {
    if (_characterClass==null)
    {
      return true;
    }
    if (classRequirement==null)
    {
      return true;
    }
    return classRequirement.accept(_characterClass);
  }
}

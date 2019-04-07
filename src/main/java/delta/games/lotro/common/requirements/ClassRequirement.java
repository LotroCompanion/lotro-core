package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.CharacterClass;

/**
 * Class requirement.
 * @author DAM
 */
public class ClassRequirement
{
  private static final String SEPARATOR=";";
  private List<CharacterClass> _allowedClasses;

  /**
   * Constructor.
   */
  public ClassRequirement()
  {
    _allowedClasses=new ArrayList<CharacterClass>();
  }

  /**
   * Add an allowed character class.
   * @param characterClass Character class to add.
   */
  public void addAllowedClass(CharacterClass characterClass)
  {
    _allowedClasses.add(characterClass);
  }

  /**
   * Get the allowed classes.
   * @return A possibly empty, but not <code>null</code> list of classes.
   */
  public List<CharacterClass> getAllowedClasses()
  {
    return _allowedClasses;
  }

  /**
   * Indicates if this requirement accepts the given class or not.
   * @param characterClass Character class to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean accept(CharacterClass characterClass)
  {
    return _allowedClasses.contains(characterClass);
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    for(CharacterClass characterClass : _allowedClasses)
    {
      if (sb.length()>0)
      {
        sb.append(SEPARATOR);
      }
      sb.append(characterClass.getKey());
    }
    return sb.toString();
  }

  /**
   * Build a class requirement from a string.
   * @param input Input string (";" separated list of class keys).
   * @return A class requirement or <code>null</code> if none.
   */
  public static ClassRequirement fromString(String input)
  {
    ClassRequirement ret=null;
    if ((input!=null) && (input.length()>0))
    {
      ret=new ClassRequirement();
      String[] classStrs=input.split(SEPARATOR);
      for(String classStr : classStrs)
      {
        CharacterClass characterClass=CharacterClass.getByKey(classStr);
        if (characterClass!=null)
        {
          ret.addAllowedClass(characterClass);
        }
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _allowedClasses.toString();
  }
}

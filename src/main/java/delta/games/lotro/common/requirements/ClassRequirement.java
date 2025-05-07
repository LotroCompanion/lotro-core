package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassesManager;

/**
 * Class requirement.
 * @author DAM
 */
public class ClassRequirement implements Requirement
{
  private static final String SEPARATOR=";";
  private List<AbstractClassDescription> _allowedClasses;

  /**
   * Constructor.
   */
  public ClassRequirement()
  {
    _allowedClasses=new ArrayList<AbstractClassDescription>();
  }

  /**
   * Add an allowed character class.
   * @param characterClass Character class to add.
   */
  public void addAllowedClass(AbstractClassDescription characterClass)
  {
    if (!_allowedClasses.contains(characterClass))
    {
      _allowedClasses.add(characterClass);
    }
  }

  /**
   * Get the allowed classes.
   * @return A possibly empty, but not <code>null</code> list of classes.
   */
  public List<AbstractClassDescription> getAllowedClasses()
  {
    return _allowedClasses;
  }

  /**
   * Remove all allowed classes.
   */
  public void removeAll()
  {
    _allowedClasses.clear();
  }

  /**
   * Indicates if this requirement accepts the given class or not.
   * @param characterClass Character class to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean accept(AbstractClassDescription characterClass)
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
    for(AbstractClassDescription characterClass : _allowedClasses)
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
    if ((input!=null) && (!input.isEmpty()))
    {
      ret=new ClassRequirement();
      String[] classStrs=input.split(SEPARATOR);
      ClassesManager classesMgr=ClassesManager.getInstance();
      for(String classStr : classStrs)
      {
        AbstractClassDescription abstractClass=classesMgr.getClassByKey(classStr);
        if (abstractClass!=null)
        {
          ret.addAllowedClass(abstractClass);
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

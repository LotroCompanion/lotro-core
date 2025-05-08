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
   * @param characterClass Single class.
   */
  public ClassRequirement(AbstractClassDescription characterClass)
  {
    _allowedClasses=new ArrayList<AbstractClassDescription>();
    if (characterClass!=null)
    {
      _allowedClasses.add(characterClass);
    }
  }

  /**
   * Constructor.
   * @param classes Allowed classes.
   */
  public ClassRequirement(List<AbstractClassDescription> classes)
  {
    _allowedClasses=new ArrayList<AbstractClassDescription>();
    for(AbstractClassDescription characterClass : classes)
    {
      if (characterClass!=null)
      {
        _allowedClasses.add(characterClass);
      }
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
      String[] classStrs=input.split(SEPARATOR);
      ClassesManager classesMgr=ClassesManager.getInstance();
      List<AbstractClassDescription> classes=new ArrayList<AbstractClassDescription>();
      for(String classStr : classStrs)
      {
        AbstractClassDescription abstractClass=classesMgr.getClassByKey(classStr);
        if (abstractClass!=null)
        {
          classes.add(abstractClass);
        }
      }
      ret=new ClassRequirement(classes);
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _allowedClasses.toString();
  }
}

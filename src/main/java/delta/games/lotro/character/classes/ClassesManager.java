package delta.games.lotro.character.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to class descriptions.
 * @author DAM
 */
public class ClassesManager
{
  private static ClassesManager _instance=null;

  private SimpleClassesManager<ClassDescription> _characterClasses;
  private SimpleClassesManager<MonsterClassDescription> _monsterClasses;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ClassesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ClassesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private ClassesManager()
  {
    _characterClasses=new SimpleClassesManager<ClassDescription>();
    _monsterClasses=new SimpleClassesManager<MonsterClassDescription>();
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    // Character classes
    File classesFile=cfg.getFile(DataFiles.CLASSES);
    _characterClasses.loadFromFile(classesFile,"classes");
    // Monster classes
    File monsterClassesFile=cfg.getFile(DataFiles.MONSTER_CLASSES);
    _monsterClasses.loadFromFile(monsterClassesFile,"monsterClasses");
  }

  /**
   * Get all character classes.
   * @return a list of all character classes.
   */
  public List<ClassDescription> getAllCharacterClasses()
  {
    return _characterClasses.getAll();
  }

  /**
   * Get all classes (both character and monster).
   * @return A list of classes, sorted by name.
   */
  public List<AbstractClassDescription> getAllClasses()
  {
    List<AbstractClassDescription> ret=new ArrayList<AbstractClassDescription>();
    ret.addAll(_characterClasses.getAll());
    ret.addAll(_monsterClasses.getAll());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Get a class description using its code.
   * @param code Code to use.
   * @return A class description or <code>null</code> if not found.
   */
  public ClassDescription getCharacterClassByCode(int code)
  {
    return _characterClasses.getByCode(code);
  }

  /**
   * Get a class description using its key.
   * @param key Key to use.
   * @return A class description or <code>null</code> if not found.
   */
  public ClassDescription getCharacterClassByKey(String key)
  {
    return _characterClasses.getByKey(key);
  }

  /**
   * Get a class by code (character or monster).
   * @param code Code of the class to get.
   * @return A class or <code>null</code> if not found.
   */
  public AbstractClassDescription getClassByCode(int code)
  {
    AbstractClassDescription ret=getCharacterClassByCode(code);
    if (ret==null)
    {
      ret=_monsterClasses.getByCode(code);
    }
    return ret;
  }

  /**
   * Get a class by key (character or monster).
   * @param key Key of the class to get.
   * @return A class or <code>null</code> if not found.
   */
  public AbstractClassDescription getClassByKey(String key)
  {
    AbstractClassDescription ret=getCharacterClassByKey(key);
    if (ret==null)
    {
      ret=_monsterClasses.getByKey(key);
    }
    return ret;
  }
}

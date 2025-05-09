package delta.games.lotro.character.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.classes.io.xml.ClassDescriptionXMLParser;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to class descriptions.
 * @author DAM
 * @param <T> 
 */
public class SimpleClassesManager<T extends AbstractClassDescription>
{
  private HashMap<String,T> _mapByKey;
  private HashMap<Integer,T> _mapByCode;

  /**
   * Constructor.
   */
  public SimpleClassesManager()
  {
    _mapByKey=new HashMap<String,T>(10);
    _mapByCode=new HashMap<Integer,T>(10);
  }

  /**
   * Load all classes from a file.
   * @param classesFile File to read from.
   * @param labels Name of the labels file to use.
   */
  @SuppressWarnings("unchecked")
  public void loadFromFile(File classesFile, String labels)
  {
    _mapByKey.clear();
    _mapByCode.clear();
    long now=System.currentTimeMillis();
    List<AbstractClassDescription> classDescriptions=new ClassDescriptionXMLParser(labels).parseClassDescriptionsFile(classesFile);
    for(AbstractClassDescription classDescription : classDescriptions)
    {
      _mapByKey.put(classDescription.getKey(),(T)classDescription);
      Integer codeKey=Integer.valueOf(classDescription.getCode());
      _mapByCode.put(codeKey,(T)classDescription);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_mapByKey.size(),"classes",duration);
  }

  /**
   * Get all classes.
   * @return a list of all character classes.
   */
  public List<T> getAll()
  {
    List<T> ret=new ArrayList<T>();
    ret.addAll(_mapByKey.values());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Get a class description using its code.
   * @param code Code to use.
   * @return A class description or <code>null</code> if not found.
   */
  public T getByCode(int code)
  {
    return _mapByCode.get(Integer.valueOf(code));
  }

  /**
   * Get a class description using its key.
   * @param key Key to use.
   * @return A class description or <code>null</code> if not found.
   */
  public T getByKey(String key)
  {
    T ret=_mapByKey.get(key);
    return ret;
  }
}

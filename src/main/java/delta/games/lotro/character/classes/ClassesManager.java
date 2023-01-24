package delta.games.lotro.character.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.classes.io.xml.ClassDescriptionXMLParser;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to class descriptions.
 * @author DAM
 */
public class ClassesManager
{
  private static final Logger LOGGER=Logger.getLogger(ClassesManager.class);

  private static ClassesManager _instance=null;

  private HashMap<String,ClassDescription> _mapByKey;
  private HashMap<Integer,ClassDescription> _mapByCode;

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
    _mapByKey=new HashMap<String,ClassDescription>(10);
    _mapByCode=new HashMap<Integer,ClassDescription>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _mapByKey.clear();
    _mapByCode.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File classesFile=cfg.getFile(DataFiles.CLASSES);
    long now=System.currentTimeMillis();
    List<ClassDescription> classDescriptions=ClassDescriptionXMLParser.parseClassDescriptionsFile(classesFile);
    for(ClassDescription classDescription : classDescriptions)
    {
      _mapByKey.put(classDescription.getKey(),classDescription);
      Integer codeKey=Integer.valueOf(classDescription.getCode());
      _mapByCode.put(codeKey,classDescription);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_mapByKey.size()+" character classes in "+duration+"ms.");
  }

  /**
   * Get all classes.
   * @return a list of all character classes.
   */
  public List<ClassDescription> getAll()
  {
    List<ClassDescription> ret=new ArrayList<ClassDescription>();
    ret.addAll(_mapByKey.values());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Get a class description using its code.
   * @param code Code to use.
   * @return A class description or <code>null</code> if not found.
   */
  public ClassDescription getByCode(int code)
  {
    return _mapByCode.get(Integer.valueOf(code));
  }

  /**
   * Get a class description using its key.
   * @param key Key to use.
   * @return A class description or <code>null</code> if not found.
   */
  public ClassDescription getByKey(String key)
  {
    ClassDescription ret=_mapByKey.get(key);
    return ret;
  }
}

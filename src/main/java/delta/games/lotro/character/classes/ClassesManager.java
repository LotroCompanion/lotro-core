package delta.games.lotro.character.classes;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.classes.io.xml.ClassDescriptionXMLParser;
import delta.games.lotro.common.CharacterClass;
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

  private HashMap<CharacterClass,ClassDescription> _cache;

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
    _cache=new HashMap<CharacterClass,ClassDescription>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File classesFile=cfg.getFile(DataFiles.CLASSES);
    long now=System.currentTimeMillis();
    List<ClassDescription> classDescriptions=ClassDescriptionXMLParser.parseClassDescriptionsFile(classesFile);
    for(ClassDescription classDescription : classDescriptions)
    {
      _cache.put(classDescription.getCharacterClass(),classDescription);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" character classes in "+duration+"ms.");
  }

  /**
   * Get a class description using its key.
   * @param characterClass Class to get.
   * @return A class description or <code>null</code> if not found.
   */
  public ClassDescription getClassDescription(CharacterClass characterClass)
  {
    ClassDescription ret=null;
    ret=_cache.get(characterClass);
    return ret;
  }
}

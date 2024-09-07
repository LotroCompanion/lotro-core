package delta.games.lotro.lore.items.legendary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.io.xml.LegacyXMLParser;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Facade for access to legacies.
 * @author DAM
 */
public class LegaciesManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(LegaciesManager.class);

  private static LegaciesManager _instance=null;

  private HashMap<Integer,ImbuedLegacy> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LegaciesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new LegaciesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LegaciesManager()
  {
    _cache=new HashMap<Integer,ImbuedLegacy>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File legaciesFile=cfg.getFile(DataFiles.LEGACIES);
    long now=System.currentTimeMillis();
    SingleLocaleLabelsManager i18n=I18nFacade.getLabelsMgr("legacies");
    List<AbstractLegacy> legacies=new LegacyXMLParser(i18n).parseLegaciesFile(legaciesFile);
    for(AbstractLegacy legacy : legacies)
    {
      registerLegacy((ImbuedLegacy)legacy);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" legacies in "+duration+"ms.");
  }

  /**
   * Register a new legacy.
   * @param legacy Legacy to register.
   */
  public void registerLegacy(ImbuedLegacy legacy)
  {
    _cache.put(Integer.valueOf(legacy.getIdentifier()),legacy);
  }

  /**
   * Get a list of all legacies, sorted by identifier.
   * @return A list of legacies.
   */
  public List<ImbuedLegacy> getAll()
  {
    ArrayList<ImbuedLegacy> legacies=new ArrayList<ImbuedLegacy>();
    legacies.addAll(_cache.values());
    Collections.sort(legacies,new IdentifiableComparator<ImbuedLegacy>());
    return legacies;
  }

  /**
   * Get all legacies for a given character class and equipment slot.
   * @param characterClass Character class.
   * @param slot Equipment slot.
   * @param type Legacy type (<code>null</code> to skip this filter).
   * @return A possibly empty but not <code>null</code> list of legacies.
   */
  public List<ImbuedLegacy> get(ClassDescription characterClass, EquipmentLocation slot, LegacyType type)
  {
    List<ImbuedLegacy> ret=new ArrayList<ImbuedLegacy>();
    ClassAndSlot classAndSlot=new ClassAndSlot(characterClass,slot);
    for(ImbuedLegacy legacy : _cache.values())
    {
      Filter<ClassAndSlot> constraint=legacy.getClassAndSlotFilter();
      boolean okType=((type==null) || (type==legacy.getType()));
      if (okType)
      {
        boolean okClassAndSlot=((constraint==null) || (constraint.accept(classAndSlot)));
        if (okClassAndSlot)
        {
          ret.add(legacy);
        }
      }
    }
    return ret;
  }


  /**
   * Get a legacy using its identifier.
   * @param id Legacy identifier.
   * @return A legacy or <code>null</code> if not found.
   */
  public ImbuedLegacy getLegacy(int id)
  {
    ImbuedLegacy ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}

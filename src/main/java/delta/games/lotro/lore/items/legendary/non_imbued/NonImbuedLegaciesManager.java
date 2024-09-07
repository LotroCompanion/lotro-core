package delta.games.lotro.lore.items.legendary.non_imbued;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatDescriptionComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.EquipmentLocations;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;
import delta.games.lotro.lore.items.legendary.io.xml.LegacyXMLParser;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Manager for non-imbued legacies.
 * @author DAM
 */
public class NonImbuedLegaciesManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(NonImbuedLegaciesManager.class);

  private static NonImbuedLegaciesManager _instance=null;

  private Map<StatDescription,TieredNonImbuedLegacy> _tieredLegacies;
  private Map<Integer,DefaultNonImbuedLegacy> _defaultLegacies;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static NonImbuedLegaciesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new NonImbuedLegaciesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public NonImbuedLegaciesManager()
  {
    _tieredLegacies=new HashMap<StatDescription,TieredNonImbuedLegacy>();
    _defaultLegacies=new HashMap<Integer,DefaultNonImbuedLegacy>();
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File legaciesFile=cfg.getFile(DataFiles.NON_IMBUED_LEGACIES);
    long now=System.currentTimeMillis();
    SingleLocaleLabelsManager i18n=I18nFacade.getLabelsMgr("nonImbuedLegacies");
    List<AbstractLegacy> legacies=new LegacyXMLParser(i18n).parseLegaciesFile(legaciesFile);
    for(AbstractLegacy legacy : legacies)
    {
      if (legacy instanceof TieredNonImbuedLegacy)
      {
        addTieredLegacy((TieredNonImbuedLegacy)legacy);
      }
      if (legacy instanceof DefaultNonImbuedLegacy)
      {
        addDefaultLegacy((DefaultNonImbuedLegacy)legacy);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+legacies.size()+" non-imbued legacies in "+duration+"ms.");
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addTieredLegacy(TieredNonImbuedLegacy legacy)
  {
    StatDescription stat=legacy.getStat();
    _tieredLegacies.put(stat,legacy);
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addDefaultLegacy(DefaultNonImbuedLegacy legacy)
  {
    int effectID=legacy.getEffectID();
    Integer identifier=Integer.valueOf(effectID);
    _defaultLegacies.put(identifier,legacy);
  }

  /**
   * Get the legacy for a given stat.
   * @param stat Stat to use.
   * @return A legacy or <code>null</code> if not found.
   */
  public TieredNonImbuedLegacy getLegacy(StatDescription stat)
  {
    return _tieredLegacies.get(stat);
  }

  /**
   * Get a legacy tier using its identifier.
   * @param id Legacy tier identifier (effect identifier).
   * @return A legacy tier or <code>null</code> if not found.
   */
  public NonImbuedLegacyTier getLegacyTier(int id)
  {
    for(TieredNonImbuedLegacy legacy : _tieredLegacies.values())
    {
      for(NonImbuedLegacyTier tier : legacy.getTiers())
      {
        if (tier.getEffectID()==id)
        {
          return tier;
        }
      }
    }
    return null;
  }

  /**
   * Get all tiered legacies.
   * @return a list of all tiered lagacies.
   */
  public List<TieredNonImbuedLegacy> getTieredLegacies()
  {
    List<TieredNonImbuedLegacy> ret=new ArrayList<TieredNonImbuedLegacy>();
    List<StatDescription> stats=new ArrayList<StatDescription>(_tieredLegacies.keySet());
    Collections.sort(stats,new StatDescriptionComparator());
    for(StatDescription stat : stats)
    {
      TieredNonImbuedLegacy legacy=_tieredLegacies.get(stat);
      ret.add(legacy);
    }
    return ret;
  }

  /**
   * Get a default legacy by its identifier.
   * @param identifier Identifier to use.
   * @return A legacy or <code>null</code> if not found.
   */
  public DefaultNonImbuedLegacy getDefaultLegacy(int identifier)
  {
    return _defaultLegacies.get(Integer.valueOf(identifier));
  }

  /**
   * Get all default non-imbued legacies.
   * @return a list of all default non-imbued lagacies.
   */
  public List<DefaultNonImbuedLegacy> getDefaultLegacies()
  {
    List<DefaultNonImbuedLegacy> ret=new ArrayList<DefaultNonImbuedLegacy>();
    List<Integer> ids=new ArrayList<Integer>(_defaultLegacies.keySet());
    Collections.sort(ids);
    for(Integer id : ids)
    {
      DefaultNonImbuedLegacy legacy=_defaultLegacies.get(id);
      ret.add(legacy);
    }
    return ret;
  }

  /**
   * Register a legacy usage.
   * @param legacy Legacy to use.
   * @param characterClass Involved character class.
   * @param slot Involved slot.
   */
  public void registerLegacyUsage(AbstractNonImbuedLegacy legacy, ClassDescription characterClass, EquipmentLocation slot)
  {
    if (isAllowedCombinaison(characterClass,slot))
    {
      ClassAndSlot spec=new ClassAndSlot(characterClass,slot);
      legacy.addAllowedClassAndSlot(spec);
    }
  }

  private boolean isAllowedCombinaison(ClassDescription characterClass, EquipmentLocation slot)
  {
    if (slot==EquipmentLocations.RANGED_ITEM)
    {
      String classKey=characterClass.getKey();
      if ((!(WellKnownCharacterClassKeys.HUNTER.equals(classKey))) &&
          (!(WellKnownCharacterClassKeys.WARDEN.equals(classKey))))
      {
        return false;
      }
    }
    if (slot==EquipmentLocations.CLASS_SLOT)
    {
      String classKey=characterClass.getKey();
      if ((WellKnownCharacterClassKeys.HUNTER.equals(classKey)) ||
          (WellKnownCharacterClassKeys.WARDEN.equals(classKey)))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Get all tiered legacies for a given character class and slot.
   * @param characterClass Targeted character class.
   * @param slot Targeted slot.
   * @return a possibly empty but not <code>null</code> list of legacies.
   */
  public List<TieredNonImbuedLegacy> getTieredLegacies(ClassDescription characterClass, EquipmentLocation slot)
  {
    return filterLegacies(characterClass,slot,_tieredLegacies.values());
  }

  /**
   * Get all tiered legacies for a given character class and slot.
   * @param characterClass Targeted character class.
   * @param slot Targeted slot.
   * @return a possibly empty but not <code>null</code> list of legacies.
   */
  public List<DefaultNonImbuedLegacy> getDefaultLegacies(ClassDescription characterClass, EquipmentLocation slot)
  {
    return filterLegacies(characterClass,slot,_defaultLegacies.values());
  }

  private <T extends AbstractLegacy> List<T> filterLegacies(ClassDescription characterClass, EquipmentLocation slot, Collection<T> legacies)
  {
    List<T> ret=new ArrayList<T>();
    ClassAndSlot classAndSlot=new ClassAndSlot(characterClass,slot);
    for(T legacy : legacies)
    {
      Filter<ClassAndSlot> constraint=legacy.getClassAndSlotFilter();
      boolean ok=((constraint==null) || (constraint.accept(classAndSlot)));
      if (ok)
      {
        ret.add(legacy);
      }
    }
    return ret;
  }

  /**
   * Get a displayable content of this object.
   * @return a displayable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    for(TieredNonImbuedLegacy legacy : _tieredLegacies.values())
    {
      sb.append(legacy.dump());
    }
    for(DefaultNonImbuedLegacy legacy : _defaultLegacies.values())
    {
      sb.append(legacy).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
